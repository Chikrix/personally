package com.chidi.protein.personally.modules.home.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chidi.protein.personally.R;
import com.chidi.protein.personally.databinding.FragmentNewsDetailsBinding;
import com.chidi.protein.personally.domain.models.Article;
import com.chidi.protein.personally.utils.Constants;

public class NewsDetailsFragment extends Fragment {
  private static NewsDetailsFragment newsDetailsFragment;
  private FragmentNewsDetailsBinding newsDetailsBinding;
  private Article article;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    newsDetailsBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false);

    Bundle args = getArguments();
    if (args != null && args.containsKey(Constants.ARTICLE_BUNDLE_KEY)) {
      article = (Article) args.getSerializable(Constants.ARTICLE_BUNDLE_KEY);
      newsDetailsBinding.setArticle(article);
    } else {
      onDetach();
    }

    return newsDetailsBinding.getRoot();
  }

  public static NewsDetailsFragment newInstance(@NonNull Bundle bundle) {
    if (newsDetailsFragment == null) {
      newsDetailsFragment = new NewsDetailsFragment();
    }
    newsDetailsFragment.setArguments(bundle);
    return newsDetailsFragment;
  }
}
