package com.chidi.protein.personally.modules.preferences.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;
import com.chidi.protein.personally.R;

public class SettingsFragment extends PreferenceFragment
    implements SharedPreferences.OnSharedPreferenceChangeListener {
  private static SettingsFragment settingsFragment;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.pref_settings);
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    ListPreference listPreference = (ListPreference) findPreference("newsType");
    listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
      @Override public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.d("ghdj", newValue.toString());
        return true;
      }
    });
  }


  public static SettingsFragment newInstance() {
    if (settingsFragment == null) {
      settingsFragment = new SettingsFragment();
    }
    return settingsFragment;
  }

  @Override public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    Log.d("ghdj", key);
  }
}
