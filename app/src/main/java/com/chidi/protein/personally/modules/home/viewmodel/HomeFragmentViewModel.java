package com.chidi.protein.personally.modules.home.viewmodel;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.util.Log;
import com.chidi.protein.personally.PersonallyApplication;
import com.chidi.protein.personally.domain.dao.NewsDao;
import com.chidi.protein.personally.domain.models.NewsModel;
import com.chidi.protein.personally.domain.repositories.OfflineRepository;
import com.chidi.protein.personally.domain.repositories.OnlineRepository;
import com.chidi.protein.personally.utils.Constants;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeFragmentViewModel extends AndroidViewModel {

  private OnlineRepository onlineRepository = OnlineRepository.getOnlineRepositoryInstance();
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
       disposable = onlineRepository.fetchNewsByCategory(query)
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(new Consumer<NewsModel>() {
             @Override public void accept(NewsModel newsModel) throws Exception {
               offlineRepository.saveNews(newsModel)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new Action() {
                     @Override public void run() throws Exception {
                       Log.d("hfdj", "Saved data");
                     }
                   }, new Consumer<Throwable>() {
                     @Override public void accept(Throwable throwable) throws Exception {
                       Log.d("hfdj", throwable.getMessage());
                     }
                   });
               fetchNewsArticles();
             }
           }, new Consumer<Throwable>() {
             @Override public void accept(Throwable throwable) throws Exception {
               newsItems.setValue(null);
             }
           });
    } else {
      disposable = onlineRepository.fetchNewsByKeyword(query).subscribe(new Consumer<NewsModel>() {
        @Override public void accept(NewsModel newsModel) throws Exception {
          newsItems.setValue(newsModel);
        }
      }, new Consumer<Throwable>() {
        @Override public void accept(Throwable throwable) throws Exception {

        }
      });
    }

    disposables.add(disposable);
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
            Log.d("hfdj", throwable.getMessage());
          }
        });
  }

  @Override protected void onCleared() {
    onlineRepository = null;
    offlineRepository = null;
    disposables.dispose();
  }
}
