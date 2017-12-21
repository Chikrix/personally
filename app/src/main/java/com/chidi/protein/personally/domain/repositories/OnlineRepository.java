package com.chidi.protein.personally.domain.repositories;

import com.chidi.protein.personally.domain.api.NewsService;
import com.chidi.protein.personally.domain.api.RetrofitAdapter;
import com.chidi.protein.personally.domain.models.NewsModel;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OnlineRepository {
  private final RetrofitAdapter retrofitAdapter;
  private static OnlineRepository onlineRepository;

  private OnlineRepository() {
    retrofitAdapter = RetrofitAdapter.getInstance();
  }

  public Flowable<NewsModel> fetchNewsItems(String query) {
    NewsService newsService = (NewsService) retrofitAdapter.createService(NewsService.class);
    return newsService.fetchNewsheadlines(query)
        .subscribeOn(Schedulers.io())
        .cache()
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static OnlineRepository getOnlineRepositoryInstance() {
    if (onlineRepository == null) {
      onlineRepository = new OnlineRepository();
    }
    return onlineRepository;
  }
}
