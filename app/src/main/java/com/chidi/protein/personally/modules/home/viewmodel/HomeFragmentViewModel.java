package com.chidi.protein.personally.modules.home.viewmodel;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import com.chidi.protein.personally.PersonallyApplication;
import com.chidi.protein.personally.domain.dao.NewsDao;
import com.chidi.protein.personally.domain.models.NewsModel;
import com.chidi.protein.personally.domain.repositories.OfflineRepository;
import com.chidi.protein.personally.utils.Constants;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HomeFragmentViewModel extends AndroidViewModel {
  private OfflineRepository offlineRepository;

  public ObservableField<Boolean> isShowingProgressBar = new ObservableField<>(true);
  public ObservableField<String> seaqchQueryObservable =
      new ObservableField<>(Constants.QUERY_TECHNOLOGY);

  private CompositeDisposable disposables = new CompositeDisposable();

  public MutableLiveData<NewsModel> newsItems = new MutableLiveData<>();

  public HomeFragmentViewModel(PersonallyApplication personallyApplication) {
    super(personallyApplication);
    NewsDao dao = personallyApplication.databaseInstance.newsDao();
    offlineRepository = OfflineRepository.getOfflineRepositoryInstance(dao);
  }

  public void fetchNewsItems(final String query) {
    seaqchQueryObservable.set(query);
    Disposable disposable;
    if (query.equals(Constants.QUERY_ENTERTAINMENT ) | query.equals(Constants.QUERY_TECHNOLOGY)) {
       disposable = getSubscribe(query, 0);
    } else {
      disposable = getSubscribe(query, 1);
    }
    disposables.add(disposable);

  }

  @NonNull private Disposable getSubscribe(String query, int queryOrCategory) {
    return offlineRepository.fetchNewsArticles(query, queryOrCategory)
        .subscribe(new Consumer<NewsModel>() {
      @Override public void accept(NewsModel newsModel) throws Exception {
        newsItems.setValue(newsModel);
      }
    }, new Consumer<Throwable>() {
      @Override public void accept(Throwable throwable) throws Exception {
        newsItems.setValue(null);
      }
    });
  }

  public void fetchNewsArticles() {
    offlineRepository.fetchNewsArticles()
        .subscribe(new Consumer<NewsModel>() {
          @Override public void accept(NewsModel newsModel) throws Exception {
            newsItems.setValue(newsModel);
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            newsItems.setValue(null);
          }
        });
  }

  @Override protected void onCleared() {
    offlineRepository = null;
    disposables.dispose();
  }
}
