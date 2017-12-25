package com.chidi.protein.personally;

import android.app.Application;
import com.chidi.protein.personally.domain.NewsDb;
import com.facebook.stetho.Stetho;

public class PersonallyApplication extends Application {
  public NewsDb databaseInstance = NewsDb.createNewsDb(this);

  @Override public void onCreate() {
    super.onCreate();
    Stetho.initialize(
        Stetho.newInitializerBuilder(this)
            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
            .build());
  }
}
