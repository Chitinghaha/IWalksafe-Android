package org.tensorflow.lite.examples.objectdetection

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

public class NavGraphDirections private constructor() {
  public companion object {
    public fun actionGlobalObjectSettingFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_global_objectSettingFragment)
  }
}
