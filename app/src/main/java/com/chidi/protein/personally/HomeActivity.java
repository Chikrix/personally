package com.chidi.protein.personally;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.chidi.protein.personally.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
  private ActivityHomeBinding activityHomeBinding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
  }
}
