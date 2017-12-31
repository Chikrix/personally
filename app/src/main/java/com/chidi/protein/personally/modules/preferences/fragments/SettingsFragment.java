package com.chidi.protein.personally.modules.preferences.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import com.chidi.protein.personally.R;

public class SettingsFragment extends PreferenceFragment {
  private SharedPreferences.OnSharedPreferenceChangeListener listener;
  private static SettingsFragment settingsFragment;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.pref_settings);
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
  }

  public static SettingsFragment newInstance() {
    if (settingsFragment == null) {
      settingsFragment = new SettingsFragment();
    }
    return settingsFragment;
  }

}
