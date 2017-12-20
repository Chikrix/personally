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
import com.chidi.protein.personally.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
  private static HomeFragment homeFragment;
  private FragmentHomeBinding fragmentHomeBinding;

  public HomeFragment() {

  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    fragmentHomeBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
    return fragmentHomeBinding.getRoot();
  }

  public static HomeFragment newInstance() {
    if (homeFragment == null) {
      homeFragment = new HomeFragment();
    }

    return homeFragment;
  }
}
