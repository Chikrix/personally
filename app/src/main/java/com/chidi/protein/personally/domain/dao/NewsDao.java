package com.chidi.protein.personally.domain.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.chidi.protein.personally.domain.models.NewsModel;
import java.util.List;

@Dao
public interface NewsDao {

  @Query("SELECT * FROM News")
  List<NewsModel> getSavedNews();

  @Insert
  void insertNewsModel(NewsModel newsModel);

  @Update
  void updateRecord(NewsModel newsModel);
}
