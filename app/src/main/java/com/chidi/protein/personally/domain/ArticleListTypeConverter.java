package com.chidi.protein.personally.domain;

import android.arch.persistence.room.TypeConverter;
import com.chidi.protein.personally.domain.models.Article;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class ArticleListTypeConverter {

  @TypeConverter
  public static List<Article> stringToArticles(String json) {
    Gson gson = new Gson();
    Type type = new TypeToken<List<Article>>(){}.getType();
    return gson.fromJson(json, type);
  }

  @TypeConverter
  public static String articlesListToString(List<Article> articles) {
    Gson gson = new Gson();
    Type type = new TypeToken<List<Article>>(){}.getType();
    return  gson.toJson(articles, type);
  }
}
