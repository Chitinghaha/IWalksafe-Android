package org.tensorflow.lite.examples.objectdetection.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import org.tensorflow.lite.examples.objectdetection.NavGraphDirections
import org.tensorflow.lite.examples.objectdetection.R

public class WalkModeFragmentDirections private constructor() {
  public companion object {
    public fun actionWalkModelToCompassFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_WalkModel_to_compassFragment)

    public fun actionWalkModelToNavigationSettingFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_WalkModel_to_navigationSettingFragment)

    public fun actionWalkModelToSettingsForWalkModeFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_WalkModel_to_settingsForWalkModeFragment)

    public fun actionGlobalObjectSettingFragment(): NavDirections =
        NavGraphDirections.actionGlobalObjectSettingFragment()
  }
}
