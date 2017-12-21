package com.chidi.protein.personally.domain.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@Entity(tableName = "news")
public class NewsModel {

  @SerializedName("status") @PrimaryKey @ColumnInfo(name = "status") @NonNull private String status;
  @SerializedName("totalResults") @ColumnInfo(name = "total_results")  private Integer totalResults;
  @SerializedName("articles") @ColumnInfo(name = "articles") private List<Article> articles = null;

  public List<Article> getArticles() {
    return articles;
  }

  public Integer getTotalResults() {
    return totalResults;
  }

  public void setTotalResults(Integer totalResults) {
    this.totalResults = totalResults;
  }

  public void setArticles(List<Article> articles) {
    this.articles = articles;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}