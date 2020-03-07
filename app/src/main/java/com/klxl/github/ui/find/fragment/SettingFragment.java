package com.klxl.github.ui.find.fragment;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.klxl.github.R;


public class SettingFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference_setting, rootKey);
    }
}
