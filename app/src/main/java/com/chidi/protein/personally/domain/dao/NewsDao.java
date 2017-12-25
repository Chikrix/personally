package com.chidi.protein.personally.domain.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.chidi.protein.personally.domain.models.NewsModel;
import io.reactivex.Flowable;

@Dao
public interface NewsDao {

  @Query("SELECT * FROM News LIMIT 1")
  Flowable<NewsModel> getSavedNews();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertNewsModel(NewsModel newsModel);

  @Update
  void updateRecord(NewsModel newsModel);
}
