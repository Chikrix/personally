package com.chidi.protein.personally.modules.home.activities;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.chidi.protein.personally.R;
import com.chidi.protein.personally.databinding.ActivityHomeBinding;
import com.chidi.protein.personally.domain.models.Article;
import com.chidi.protein.personally.modules.home.fragments.HomeFragment;
import com.chidi.protein.personally.modules.home.fragments.NewsDetailsFragment;
import com.chidi.protein.personally.utils.Constants;
import com.chidi.protein.personally.utils.SimpleFragmentManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class HomeActivity extends AppCompatActivity {
  private ActivityHomeBinding activityHomeBinding;
  private SimpleFragmentManager simpleFragmentManager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
    simpleFragmentManager = new SimpleFragmentManager(getSupportFragmentManager());
  }

  @Override protected void onResume() {
    super.onResume();
    if (isGooglePlayServiceAvailable()) {
      activityHomeBinding.homeActivityPbLoading.setVisibility(View.GONE);
      simpleFragmentManager.replaceFragment(HomeFragment.newInstance(), R.id.homeActivityFlContainer,
          true);
    } else {
      Toast.makeText(this, R.string.has_play_store, Toast.LENGTH_LONG).show();
    }
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

  private boolean isGooglePlayServiceAvailable() {
    GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
    int status = apiAvailability.isGooglePlayServicesAvailable(this);
    Dialog dialog = apiAvailability.getErrorDialog(this, status, 2404);
    if (status != ConnectionResult.SUCCESS) {
      if (apiAvailability.isUserResolvableError(status)) {
        dialog.show();
      }
    }

    return status == ConnectionResult.SUCCESS;
  }
}
