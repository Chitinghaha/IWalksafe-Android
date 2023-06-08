package org.tensorflow.lite.examples.objectdetection.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import org.tensorflow.lite.examples.objectdetection.NavGraphDirections
import org.tensorflow.lite.examples.objectdetection.R

public class CompassFragmentDirections private constructor() {
  public companion object {
    public fun actionCompassFragmentToWalkModel(): NavDirections =
        ActionOnlyNavDirections(R.id.action_compassFragment_to_WalkModel)

    public fun actionGlobalObjectSettingFragment(): NavDirections =
        NavGraphDirections.actionGlobalObjectSettingFragment()
  }
}
