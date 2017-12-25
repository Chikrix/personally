package com.chidi.protein.personally.domain.dao;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.chidi.protein.personally.domain.NewsDb;
import com.chidi.protein.personally.domain.models.Article;
import com.chidi.protein.personally.domain.models.NewsModel;
import io.reactivex.Flowable;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.arch.core.executor.testing.*;

//@RunWith(AndroidJUnit4.class)
public class NewsDaoTest {


  private NewsDb newsDb;

  @Before
  public void initDb() throws Exception {
    newsDb = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
        NewsDb.class)
        .allowMainThreadQueries()
        .build();
  }

  @Test
  public void insertAndGetUser() {
    // When inserting a new user in the data source
    List<Article> articles = new ArrayList<>();
    NewsModel newsModel = new NewsModel();
    newsModel.setStatus("ok");
    newsModel.setTotalResults(10);
    articles.add(new Article());
    articles.add(new Article());
    newsModel.setArticles(articles);
    newsDb.newsDao().insertNewsModel(newsModel);

    // When subscribing to the emissions of the user
    List<NewsModel> nm = newsDb.newsDao().getSavedNews();
    System.out.println(nm == null);
        /*.test()
        // assertValue asserts that there was only one emission of the user
        .assertValue(user -> {
          // The emitted user is the expected one
          return user != null && user.getId().equals(USER.getId()) &&
              user.getUserName().equals(USER.getUserName());
        });*/
  }

  @Test public void getSavedNews() throws Exception {
  }

  @Test public void insertNewsModel() throws Exception {
  }

  @Test public void updateRecord() throws Exception {
  }
}