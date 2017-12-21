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
import com.chidi.protein.personally.utils.Constants;
import java.util.List;

public class HomeFragment extends Fragment {
  private static HomeFragment homeFragment;
  private FragmentHomeBinding fragmentHomeBinding;
  private HomeFragmentViewModel homeFragmentViewModel;
  private NewsAdapter newsAdapter;
  private String searchQuery;

  public HomeFragment() {

  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    fragmentHomeBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
    homeFragmentViewModel = ViewModelProviders.of(this).get(HomeFragmentViewModel.class);
    fragmentHomeBinding.setViewModel(homeFragmentViewModel);

    Bundle args = getArguments();
    if (args != null && args.containsKey(Constants.BUNDLE_KEY)) {
      searchQuery = args.getString(Constants.BUNDLE_KEY);
    }

    setupListView();
    observeViewModels();
    return fragmentHomeBinding.getRoot();
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if(savedInstanceState != null && savedInstanceState.containsKey(Constants.BUNDLE_KEY)) {
      searchQuery = savedInstanceState.getString(Constants.BUNDLE_KEY);
    }
    fetchNewsItems();
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (searchQuery != null) {
      outState.putString(Constants.BUNDLE_KEY, searchQuery);
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

  public static HomeFragment newInstance(@NonNull Bundle bundle) {
    if (homeFragment == null) {
      homeFragment = new HomeFragment();
    }
    homeFragment.setArguments(bundle);

    return homeFragment;
  }
}
