package com.chidi.protein.personally.modules.home.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.chidi.protein.personally.R;
import com.chidi.protein.personally.databinding.FragmentHomeBinding;
import com.chidi.protein.personally.domain.models.Article;
import com.chidi.protein.personally.domain.models.NewsModel;
import com.chidi.protein.personally.modules.home.adapters.NewsAdapter;
import com.chidi.protein.personally.modules.home.viewmodel.HomeFragmentViewModel;
import com.chidi.protein.personally.utils.AwarenessManager;
import com.chidi.protein.personally.utils.Constants;
import io.reactivex.functions.Consumer;
import java.util.List;

public class HomeFragment extends Fragment {
  private static HomeFragment homeFragment;
  private FragmentHomeBinding fragmentHomeBinding;
  private HomeFragmentViewModel homeFragmentViewModel;
  private NewsAdapter newsAdapter;
  private String searchQuery;
  private AwarenessManager awarenessManager;
  private boolean isHeadphoneConnected = false;
  private boolean isWalkingOrDriving = false;


  public HomeFragment() {

  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    fragmentHomeBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
    homeFragmentViewModel = ViewModelProviders.of(this).get(HomeFragmentViewModel.class);
    fragmentHomeBinding.setViewModel(homeFragmentViewModel);

    awarenessManager = new AwarenessManager(getContext());
    setupListView();
    observeViewModels();
    return fragmentHomeBinding.getRoot();
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    awarenessManager.connectForAwareness();
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    /*if(savedInstanceState != null && savedInstanceState.containsKey(Constants.BUNDLE_KEY)) {
      searchQuery = savedInstanceState.getString(Constants.BUNDLE_KEY);
    }*/

    awarenessManager.isWalkingOrDriving.subscribe(new Consumer<Boolean>() {
      @Override public void accept(Boolean walkingOrDriving) throws Exception {
        updatesAwarenessState(walkingOrDriving, 1);
        updatesQuery();
        fetchNewsItems();
      }
    });

    awarenessManager.isHeadphonePluggedIn.subscribe(new Consumer<Boolean>() {
      @Override public void accept(Boolean headPhoneIsPlugged) throws Exception {
        updatesAwarenessState(headPhoneIsPlugged, 0);
        updatesQuery();
        fetchNewsItems();
      }
    });
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    /*if (searchQuery != null) {
      outState.putString(Constants.BUNDLE_KEY, searchQuery);
    }*/
  }

  private void updatesAwarenessState(boolean update, int state) {
    if (state == 1) isWalkingOrDriving = update;
    else isHeadphoneConnected = update;
  }

  private void updatesQuery() {
    if (isWalkingOrDriving) {
      searchQuery = Constants.QUERY_TRAFFIC;
    } else if (isHeadphoneConnected) {
      searchQuery = Constants.QUERY_ENTERTAINMENT;
    } else {
      searchQuery = Constants.QUERY_TECHNOLOGY;
    }
  }

  private void fetchNewsItems() {
    homeFragmentViewModel.isShowingProgressBar.set(true);
    homeFragmentViewModel.fetchNewsItems(searchQuery);
  }

  private void setupListView() {
    newsAdapter = new NewsAdapter();
    fragmentHomeBinding.fragmentHomeRvMessages.setLayoutManager(new LinearLayoutManager(getContext()));
    fragmentHomeBinding.fragmentHomeRvMessages.setAdapter(newsAdapter);
    fragmentHomeBinding.fragmentHomeRvMessages.setHasFixedSize(true);
  }

  private void observeViewModels() {
    homeFragmentViewModel.newsItems.observe(this, new Observer<NewsModel>() {
      @Override public void onChanged(@Nullable NewsModel newsModel) {
        homeFragmentViewModel.isShowingProgressBar.set(false);
        List<Article> articleList = newsModel.getArticles();
        if (articleList != null && articleList.size() > 0) {
          newsAdapter.updateMatchList(articleList);
        } else {
          Toast.makeText(getContext(), R.string.nothining, Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  public static HomeFragment newInstance() {
    if (homeFragment == null) {
      homeFragment = new HomeFragment();
    }
    return homeFragment;
  }

}
