package org.tensorflow.lite.examples.objectdetection.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import kotlin.Int
import kotlin.String
import org.tensorflow.lite.examples.objectdetection.NavGraphDirections
import org.tensorflow.lite.examples.objectdetection.R

public class ObjectSettingFragmentDirections private constructor() {
  private data class ActionObjectSettingFragmentToFindObjectFragment(
    public val str: String = "null"
  ) : NavDirections {
    public override val actionId: Int = R.id.action_objectSettingFragment_to_findObjectFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("str", this.str)
        return result
      }
  }

  public companion object {
    public fun actionObjectSettingFragmentToFindObjectFragment(str: String = "null"): NavDirections
        = ActionObjectSettingFragmentToFindObjectFragment(str)

    public fun actionGlobalObjectSettingFragment(): NavDirections =
        NavGraphDirections.actionGlobalObjectSettingFragment()
  }
}
