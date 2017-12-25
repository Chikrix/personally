package com.chidi.protein.personally.modules.home.viewmodel;

import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import com.chidi.protein.personally.PersonallyApplication;

public class NewsViewmodelFactory implements ViewModelProvider.Factory {

  @Override
  public HomeFragmentViewModel create(@NonNull Class modelClass) {
    PersonallyApplication appContext = new PersonallyApplication();
    return new HomeFragmentViewModel(appContext);
  }
}

