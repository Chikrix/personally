package com.chidi.protein.personally.domain.api;

import com.chidi.protein.personally.utils.Constants;
import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAdapter {
  private static RetrofitAdapter retrofitAdapter;
  private Retrofit retrofit;

  private RetrofitAdapter() {
    buildRetrofit();
  }

  private void buildRetrofit() {
    retrofit =  new Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getOkhttpClient())
        .build();
  }

  public Object createService(Class clazz) {
    return retrofit.create(clazz);
  }

  private static OkHttpClient getOkhttpClient() {
    return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
      @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl requestHttpUrl =  request.url()
            .newBuilder()
            .addQueryParameter(Constants.KEY_API, Constants.API_KEY)
            .addQueryParameter(Constants.API_LANGUAGE,Constants.LANGUAGE_ENGLISH)
            .build();
        Request.Builder requestBuilder = request.newBuilder().url(requestHttpUrl);
        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
      }
    }).build();
  }

  public static RetrofitAdapter getInstance() {
    if (retrofitAdapter == null) {
      retrofitAdapter = new RetrofitAdapter();
    }

    return retrofitAdapter;
  }
}
