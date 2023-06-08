package org.tensorflow.lite.examples.objectdetection.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import org.tensorflow.lite.examples.objectdetection.NavGraphDirections
import org.tensorflow.lite.examples.objectdetection.R

public class IntroFragmentDirections private constructor() {
  public companion object {
    public fun actionIntroFragmentToHome(): NavDirections =
        ActionOnlyNavDirections(R.id.action_introFragment_to_Home)

    public fun actionGlobalObjectSettingFragment(): NavDirections =
        NavGraphDirections.actionGlobalObjectSettingFragment()
  }
}
