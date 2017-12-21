package com.chidi.protein.personally.modules.home.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.chidi.protein.personally.R;
import com.chidi.protein.personally.databinding.ActivityHomeBinding;
import com.chidi.protein.personally.modules.home.fragments.HomeFragment;
import com.chidi.protein.personally.utils.AwarenessManager;
import com.chidi.protein.personally.utils.Constants;

public class HomeActivity extends AppCompatActivity {
  private ActivityHomeBinding activityHomeBinding;
  private AwarenessManager awarenessManager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
    awarenessManager = new AwarenessManager(getApplicationContext());
  }

  @Override protected void onStart() {
    super.onStart();
    awarenessManager.connectForAwareness();
    String searchQuery = awarenessManager.getSearchAwareContext();
    loadHomeFragment(searchQuery);
  }

  private void loadHomeFragment(String queryString) {
    Bundle bundle = new Bundle();
    bundle.putString(Constants.BUNDLE_KEY, queryString);
    HomeFragment homeFragment = HomeFragment.newInstance(bundle);
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.homeActivityFlContainer, homeFragment).commit();
  }

}
