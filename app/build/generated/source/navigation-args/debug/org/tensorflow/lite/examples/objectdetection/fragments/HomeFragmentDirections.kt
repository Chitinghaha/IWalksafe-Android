package org.tensorflow.lite.examples.objectdetection.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import org.tensorflow.lite.examples.objectdetection.NavGraphDirections
import org.tensorflow.lite.examples.objectdetection.R

public class HomeFragmentDirections private constructor() {
  public companion object {
    public fun actionHomeToIntroFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_Home_to_introFragment)

    public fun actionGlobalObjectSettingFragment(): NavDirections =
        NavGraphDirections.actionGlobalObjectSettingFragment()
  }
}
