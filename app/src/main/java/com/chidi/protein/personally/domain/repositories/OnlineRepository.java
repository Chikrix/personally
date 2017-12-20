package com.chidi.protein.personally.domain.repositories;

import android.support.annotation.NonNull;
import com.chidi.protein.personally.domain.api.NewsService;
import com.chidi.protein.personally.domain.api.RetrofitAdapter;
import com.chidi.protein.personally.domain.models.NewsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineRepository {
  private final RetrofitAdapter retrofitAdapter;
  private static OnlineRepository onlineRepository;

  private OnlineRepository() {
    retrofitAdapter = RetrofitAdapter.getInstance();
  }

  public NewsModel fetchNewsItems(String query) {
    NewsService newsService = (NewsService) retrofitAdapter.createService(NewsService.class);
    final NewsModel[] newsModelLiveData = { new NewsModel() };
    newsService.fetchNewsheadlines(query).enqueue(new Callback<NewsModel>() {

      @Override
      public void onResponse(@NonNull Call<NewsModel> call, @NonNull Response<NewsModel> response) {
        newsModelLiveData[0] = response.body();
      }

      @Override public void onFailure(Call<NewsModel> call, Throwable t) {
        newsModelLiveData[0] = null;
      }
    });

    return newsModelLiveData[0];
  }

  public static OnlineRepository getOnlineRepositoryInstance() {
    if (onlineRepository == null) {
      onlineRepository = new OnlineRepository();
    }
    return onlineRepository;
  }
}
