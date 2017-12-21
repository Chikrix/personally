package com.chidi.protein.personally.domain;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import com.chidi.protein.personally.domain.dao.NewsDao;
import com.chidi.protein.personally.domain.models.NewsModel;

@Database(entities = { NewsModel.class }, version = 1)
@TypeConverters(ArticleListTypeConverter.class)
public abstract class NewsDb extends RoomDatabase {

  private static NewsDb database;
  private static final String DB_NAME = "newsDatabase.db";

  public abstract NewsDao newsDao();

  public static NewsDb createNewsDb(Context context) {

    if (database == null) {
      database = Room.databaseBuilder(context,
          NewsDb.class, DB_NAME)
          .addMigrations(MIGRATION_1_2)
          .build();
    }

    return database;
  }

  private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
    @Override
    public void migrate(SupportSQLiteDatabase database) {
      // Since I didn't alter the table, there's nothing else to do here.
    }
  };
}
