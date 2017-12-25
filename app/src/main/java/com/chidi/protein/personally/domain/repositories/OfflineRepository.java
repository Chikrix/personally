package com.chidi.protein.personally.domain.repositories;

import com.chidi.protein.personally.domain.dao.NewsDao;
import com.chidi.protein.personally.domain.models.NewsModel;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.completable.CompletableFromAction;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class OfflineRepository {
  private static OfflineRepository offlineRepository;
  private OnlineRepository onlineRepository;
  private NewsDao newsDao;

  private OfflineRepository(NewsDao newsDao) {
    this.newsDao = newsDao;
    this.onlineRepository = OnlineRepository.getOnlineRepositoryInstance();
  }

  public Completable saveNews(final NewsModel newsModel) {
    return new CompletableFromAction(new Action() {
      @Override public void run() throws Exception {
        newsDao.insertNewsModel(newsModel);
      }
    });

  }

  public void updateNews(NewsModel newsModel) {
    newsDao.updateRecord(newsModel);
  }

  public Flowable<NewsModel> fetchNewsArticles() {
    return newsDao.getSavedNews()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static OfflineRepository getOfflineRepositoryInstance(NewsDao newsDao) {
    if (offlineRepository == null) {
      offlineRepository = new OfflineRepository(newsDao);
    }
    return offlineRepository;
  }
}
