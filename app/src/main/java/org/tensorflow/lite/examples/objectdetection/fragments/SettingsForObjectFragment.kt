package org.tensorflow.lite.examples.objectdetection.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import org.tensorflow.lite.examples.objectdetection.R

class SettingsForObjectFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences2, rootKey)
    }
}