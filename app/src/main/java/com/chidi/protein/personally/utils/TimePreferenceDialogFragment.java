package com.chidi.protein.personally.utils;

import android.preference.DialogPreference;

/**
 * Created by Chidi Justice on 31/12/2017.
 */

public class TimePreferenceDialogFragment {
}
/*
public class TimePickerDialogFragmentCompat extends PreferenceDialogFragmentCompat
    implements DialogPreference.TargetFragment {
  TimePicker timePicker = null;

  @Override
  protected View onCreateDialogView(Context context) {
    timePicker = new TimePicker(context);
    return (timePicker);
  }

  @Override
  protected void onBindDialogView(View v) {
    super.onBindDialogView(v);
    timePicker.setIs24HourView(false);
    TimePreferencePicker pref = (TimePreferencePicker) getPreference();
    timePicker.setCurrentHour(pref.hour);
    timePicker.setCurrentMinute(pref.minute);
  }

  @Override
  public void onDialogClosed(boolean positiveResult) {
    if (positiveResult) {
      TimePreferencePicker pref = (TimePreferencePicker) getPreference();
      pref.hour = timePicker.getCurrentHour();
      pref.minute = timePicker.getCurrentMinute();

      String value = TimePreferencePicker.timeToString(pref.hour, pref.minute);
      if (pref.callChangeListener(value)) pref.persistStringValue(value);
    }
  }

  @Override
  public Preference findPreference(CharSequence charSequence) {
    return getPreference();
  }
}*/
