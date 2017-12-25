package com.chidi.protein.personally.domain.repositories;

import com.chidi.protein.personally.domain.api.NewsService;
import com.chidi.protein.personally.domain.api.RetrofitAdapter;
import com.chidi.protein.personally.domain.models.NewsModel;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class OnlineRepository {
  private final RetrofitAdapter retrofitAdapter;
  private static OnlineRepository onlineRepository;
  private NewsService newsService;

  private OnlineRepository() {
    retrofitAdapter = RetrofitAdapter.getInstance();
    newsService = (NewsService) retrofitAdapter.createService(NewsService.class);
  }

  Flowable<NewsModel> fetchNewsByKeyword(String query) {
    return newsService.fetchNewsheadlinesByKeyword(query)
        .subscribeOn(Schedulers.io())
        .cache();
  }
  
  Flowable<NewsModel> fetchNewsByCategory(String category) {
    return newsService.fetchNewsheadlinesByCategory(category)
        .subscribeOn(Schedulers.io())
        .cache();
  }

  public static OnlineRepository getOnlineRepositoryInstance() {
    if (onlineRepository == null) {
      onlineRepository = new OnlineRepository();
    }
    return onlineRepository;
  }
}
