package org.tensorflow.lite.examples.objectdetection.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import org.tensorflow.lite.examples.objectdetection.NavGraphDirections
import org.tensorflow.lite.examples.objectdetection.R

public class SlapshFragmentDirections private constructor() {
  public companion object {
    public fun actionSlapshFragmentToHomeFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_slapshFragment_to_homeFragment)

    public fun actionGlobalObjectSettingFragment(): NavDirections =
        NavGraphDirections.actionGlobalObjectSettingFragment()
  }
}
