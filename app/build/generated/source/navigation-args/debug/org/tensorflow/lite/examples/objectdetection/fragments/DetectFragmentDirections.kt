package org.tensorflow.lite.examples.objectdetection.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import org.tensorflow.lite.examples.objectdetection.NavGraphDirections
import org.tensorflow.lite.examples.objectdetection.R

public class DetectFragmentDirections private constructor() {
  public companion object {
    public fun actionDetectFragmentToObjectSettingFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_detectFragment_to_objectSettingFragment)

    public fun actionDetectFragmentToSearch(): NavDirections =
        ActionOnlyNavDirections(R.id.action_detectFragment_to_Search)

    public fun actionDetectFragmentToSettingsForObjectFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_detectFragment_to_settingsForObjectFragment)

    public fun actionGlobalObjectSettingFragment(): NavDirections =
        NavGraphDirections.actionGlobalObjectSettingFragment()
  }
}
