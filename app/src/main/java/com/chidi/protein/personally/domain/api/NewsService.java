package com.chidi.protein.personally.domain.api;

import com.chidi.protein.personally.domain.models.NewsModel;
import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {

  @GET("top-headlines")
  Flowable<NewsModel> fetchNewsheadlines(@Query("q") String query);

}
