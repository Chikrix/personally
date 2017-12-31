package com.chidi.protein.personally.modules.preferences.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.chidi.protein.personally.R;
import com.chidi.protein.personally.modules.preferences.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DataBindingUtil.setContentView(this, R.layout.activity_settings);
    loadFragment();
  }

  private void loadFragment() {
    getFragmentManager().beginTransaction().replace(R.id.activitySettingsFlContainer,
        SettingsFragment.newInstance()).commit();
  }
}
