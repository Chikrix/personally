package com.chidi.protein.personally.modules.home.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import com.chidi.protein.personally.domain.models.NewsModel;
import com.chidi.protein.personally.domain.repositories.OfflineRepository;
import com.chidi.protein.personally.domain.repositories.OnlineRepository;
import com.chidi.protein.personally.utils.Constants;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HomeFragmentViewModel extends ViewModel {

  private OnlineRepository onlineRepository = OnlineRepository.getOnlineRepositoryInstance();
  private OfflineRepository offlineRepository = OfflineRepository.getOfflineRepositoryInstance();
  public ObservableField<Boolean> isShowingProgressBar = new ObservableField<>(true);
  public ObservableField<String> seaqchQueryObservable = new ObservableField<>(Constants.QUERY_TECHNOLOGY);

  private CompositeDisposable disposables = new CompositeDisposable();

  public MutableLiveData<NewsModel> newsItems = new MutableLiveData<>();

  public void fetchNewsItems(String query) {
    seaqchQueryObservable.set(query);
    Disposable disposable;
    if (query.equals(Constants.QUERY_ENTERTAINMENT ) | query.equals(Constants.QUERY_TECHNOLOGY)) {
      disposable = onlineRepository.fetchNewsByCategory(query).subscribe(new Consumer<NewsModel>() {
        @Override public void accept(NewsModel newsModel) throws Exception {
          newsItems.setValue(newsModel);
        }
      }, new Consumer<Throwable>() {
        @Override public void accept(Throwable throwable) throws Exception {

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

  @Override protected void onCleared() {
    onlineRepository = null;
    offlineRepository = null;
    disposables.dispose();
  }
}
