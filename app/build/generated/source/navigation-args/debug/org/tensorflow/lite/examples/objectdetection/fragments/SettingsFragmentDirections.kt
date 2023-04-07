package org.tensorflow.lite.examples.objectdetection.fragments

import androidx.navigation.NavDirections
import org.tensorflow.lite.examples.objectdetection.NavGraphDirections

public class SettingsFragmentDirections private constructor() {
  public companion object {
    public fun actionGlobalObjectSettingFragment(): NavDirections =
        NavGraphDirections.actionGlobalObjectSettingFragment()
  }
}
