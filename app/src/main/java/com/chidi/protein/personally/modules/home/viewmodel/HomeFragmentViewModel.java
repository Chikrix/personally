package com.chidi.protein.personally.modules.home.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import com.chidi.protein.personally.domain.models.NewsModel;
import com.chidi.protein.personally.domain.repositories.OnlineRepository;

public class HomeFragmentViewModel extends ViewModel {

  private OnlineRepository onlineRepository = OnlineRepository.getOnlineRepositoryInstance();
  public ObservableField<Boolean> isShowingProgressBar = new ObservableField<>(true);

  public MutableLiveData<NewsModel> newsItems = new MutableLiveData<>();

  public void fetchNewsItems(String query) {
    NewsModel newsArticles = onlineRepository.fetchNewsItems(query);
    newsItems.setValue(newsArticles);
  }

  @Override protected void onCleared() {
    onlineRepository = null;
  }
}
