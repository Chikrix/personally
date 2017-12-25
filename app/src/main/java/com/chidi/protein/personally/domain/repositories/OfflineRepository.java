package com.chidi.protein.personally.domain.repositories;

import com.chidi.protein.personally.domain.dao.NewsDao;
import com.chidi.protein.personally.domain.models.NewsModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.completable.CompletableFromAction;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;

public class OfflineRepository {
  private static OfflineRepository offlineRepository;
  private OnlineRepository onlineRepository;
  private NewsDao newsDao;

  private OfflineRepository(NewsDao newsDao) {
    this.newsDao = newsDao;
    this.onlineRepository = OnlineRepository.getOnlineRepositoryInstance();
  }

  private Completable saveNews(final NewsModel newsModel) {
    return new CompletableFromAction(new Action() {
      @Override public void run() throws Exception {
        newsDao.insertNewsModel(newsModel);
      }})
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());

  }

  public void updateNews(NewsModel newsModel) {
    newsDao.updateRecord(newsModel);
  }

  public Flowable<NewsModel> fetchNewsArticles() {
    return newsDao.getSavedNews()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Flowable<NewsModel> fetchNewsArticles(String query, int queryOrCategory) {
    Flowable<NewsModel> newsModelFlowable;
    if (queryOrCategory == 0) { // category
      newsModelFlowable = onlineRepository.fetchNewsByCategory(query);
    } else { // query
      newsModelFlowable = onlineRepository.fetchNewsByKeyword(query);
    }

    return newsModelFlowable
        .observeOn(AndroidSchedulers.mainThread())
        .switchMap(new Function<NewsModel, Publisher<? extends NewsModel>>() {
          @Override public Publisher<NewsModel> apply(NewsModel newsModel)
              throws Exception {
            saveNews(newsModel).subscribe(new Action() {
              @Override public void run() throws Exception {
              }
            }, new Consumer<Throwable>() {
              @Override public void accept(Throwable throwable) throws Exception {
              }
            });
            return fetchNewsArticles();
          }
        });
  }

  public static OfflineRepository getOfflineRepositoryInstance(NewsDao newsDao) {
    if (offlineRepository == null) {
      offlineRepository = new OfflineRepository(newsDao);
    }
    return offlineRepository;
  }
}
