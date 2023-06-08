package org.tensorflow.lite.examples.objectdetection.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import org.tensorflow.lite.examples.objectdetection.NavGraphDirections
import org.tensorflow.lite.examples.objectdetection.R

public class NavigationSettingFragmentDirections private constructor() {
  public companion object {
    public fun actionNavigationSettingFragmentToWalkModel(): NavDirections =
        ActionOnlyNavDirections(R.id.action_navigationSettingFragment_to_WalkModel)

    public fun actionGlobalObjectSettingFragment(): NavDirections =
        NavGraphDirections.actionGlobalObjectSettingFragment()
  }
}
