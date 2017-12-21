package com.chidi.protein.personally;

import android.app.Application;
import com.chidi.protein.personally.domain.NewsDb;

public class PersonallyApplication extends Application {
  public NewsDb databaseInstance;

  @Override public void onCreate() {
    super.onCreate();
    databaseInstance = NewsDb.createNewsDb(this);
  }
}
