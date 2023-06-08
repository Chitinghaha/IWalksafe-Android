package org.tensorflow.lite.examples.objectdetection.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import org.tensorflow.lite.examples.objectdetection.NavGraphDirections
import org.tensorflow.lite.examples.objectdetection.R

public class CameraFragmentDirections private constructor() {
  public companion object {
    public fun actionCameraToPermissions(): NavDirections =
        ActionOnlyNavDirections(R.id.action_camera_to_permissions)

    public fun actionCameraFragmentToObjectSettingFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_camera_fragment_to_objectSettingFragment)

    public fun actionSearchToDetectFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_Search_to_detectFragment)

    public fun actionSearchToSettingsForObjectFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_Search_to_settingsForObjectFragment)

    public fun actionGlobalObjectSettingFragment(): NavDirections =
        NavGraphDirections.actionGlobalObjectSettingFragment()
  }
}
