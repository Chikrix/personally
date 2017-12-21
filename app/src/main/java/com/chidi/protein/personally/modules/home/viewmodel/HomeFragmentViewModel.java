package com.chidi.protein.personally.modules.home.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import com.chidi.protein.personally.domain.models.NewsModel;
import com.chidi.protein.personally.domain.repositories.OfflineRepository;
import com.chidi.protein.personally.domain.repositories.OnlineRepository;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HomeFragmentViewModel extends ViewModel {

  private OnlineRepository onlineRepository = OnlineRepository.getOnlineRepositoryInstance();
  private OfflineRepository offlineRepository = OfflineRepository.getOfflineRepositoryInstance();
  public ObservableField<Boolean> isShowingProgressBar = new ObservableField<>(true);
  public ObservableField<String> seaqchQueryObservable = new ObservableField<>("technology");

  private CompositeDisposable disposables = new CompositeDisposable();

  public MutableLiveData<NewsModel> newsItems = new MutableLiveData<>();

  public void fetchNewsItems(String query) {
    Disposable disposable = onlineRepository.fetchNewsItems(query).subscribe(new Consumer<NewsModel>() {
      @Override public void accept(NewsModel newsModel) throws Exception {
        newsItems.setValue(newsModel);
      }
    }, new Consumer<Throwable>() {
      @Override public void accept(Throwable throwable) throws Exception {

      }
    });
    disposables.add(disposable);
  }

  @Override protected void onCleared() {
    onlineRepository = null;
    offlineRepository = null;
    disposables.dispose();
  }
}
