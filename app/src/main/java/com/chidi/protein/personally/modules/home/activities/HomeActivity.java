package com.chidi.protein.personally.modules.home.activities;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.chidi.protein.personally.R;
import com.chidi.protein.personally.databinding.ActivityHomeBinding;
import com.chidi.protein.personally.modules.home.fragments.HomeFragment;

public class HomeActivity extends AppCompatActivity {
  private ActivityHomeBinding activityHomeBinding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
    loadHomeFragment();
  }

  private void loadHomeFragment() {
    HomeFragment homeFragment = HomeFragment.newInstance();
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.homeActivityFlContainer, homeFragment).commit();
  }
}
