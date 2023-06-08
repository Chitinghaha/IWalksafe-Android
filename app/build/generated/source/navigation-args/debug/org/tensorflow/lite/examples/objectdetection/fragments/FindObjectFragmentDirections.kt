package org.tensorflow.lite.examples.objectdetection.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import org.tensorflow.lite.examples.objectdetection.NavGraphDirections
import org.tensorflow.lite.examples.objectdetection.R

public class FindObjectFragmentDirections private constructor() {
  public companion object {
    public fun actionFindObjectFragmentToSearch(): NavDirections =
        ActionOnlyNavDirections(R.id.action_findObjectFragment_to_Search)

    public fun actionFindObjectFragmentToDetectFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_findObjectFragment_to_detectFragment)

    public fun actionFindObjectFragmentToSettingsForObjectFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_findObjectFragment_to_settingsForObjectFragment)

    public fun actionGlobalObjectSettingFragment(): NavDirections =
        NavGraphDirections.actionGlobalObjectSettingFragment()
  }
}
