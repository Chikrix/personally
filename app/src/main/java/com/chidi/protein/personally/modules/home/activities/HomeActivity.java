package com.chidi.protein.personally.modules.home.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.chidi.protein.personally.R;
import com.chidi.protein.personally.databinding.ActivityHomeBinding;
import com.chidi.protein.personally.domain.models.Article;
import com.chidi.protein.personally.modules.home.fragments.HomeFragment;
import com.chidi.protein.personally.modules.home.fragments.NewsDetailsFragment;
import com.chidi.protein.personally.utils.Constants;
import com.chidi.protein.personally.utils.SimpleFragmentManager;

public class HomeActivity extends AppCompatActivity {
  private ActivityHomeBinding activityHomeBinding;
  private SimpleFragmentManager simpleFragmentManager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
    simpleFragmentManager = new SimpleFragmentManager(getSupportFragmentManager());
    simpleFragmentManager.replaceFragment(HomeFragment.newInstance(), R.id.homeActivityFlContainer, true);
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
  }

  public void navigateToHomeNewsFragment(Article article) {
    Bundle args = new Bundle();
    args.putSerializable(Constants.ARTICLE_BUNDLE_KEY, article);
    simpleFragmentManager.replaceFragment(NewsDetailsFragment.newInstance(args),
        R.id.homeActivityFlContainer, true);
  }

}
