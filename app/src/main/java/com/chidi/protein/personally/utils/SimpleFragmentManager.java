package com.chidi.protein.personally.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class SimpleFragmentManager {
  private FragmentManager fragmentManager;

  public SimpleFragmentManager(FragmentManager fragmentManager) {
    this.fragmentManager = fragmentManager;
  }

  public void replaceFragment(@NonNull Fragment fragment,
      int layoutContainerId,
      boolean shouldAddToBackStack) {
    String simpleName = fragment.getClass().getSimpleName();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(layoutContainerId, fragment);
    if (shouldAddToBackStack) {
      fragmentTransaction.addToBackStack(simpleName);
    }

    fragmentTransaction.commit();
  }

}
