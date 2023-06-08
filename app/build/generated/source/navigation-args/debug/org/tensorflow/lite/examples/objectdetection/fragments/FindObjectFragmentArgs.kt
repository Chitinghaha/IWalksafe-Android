package org.tensorflow.lite.examples.objectdetection.fragments

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class FindObjectFragmentArgs(
  public val str: String = "null"
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("str", this.str)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("str", this.str)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): FindObjectFragmentArgs {
      bundle.setClassLoader(FindObjectFragmentArgs::class.java.classLoader)
      val __str : String?
      if (bundle.containsKey("str")) {
        __str = bundle.getString("str")
        if (__str == null) {
          throw IllegalArgumentException("Argument \"str\" is marked as non-null but was passed a null value.")
        }
      } else {
        __str = "null"
      }
      return FindObjectFragmentArgs(__str)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): FindObjectFragmentArgs {
      val __str : String?
      if (savedStateHandle.contains("str")) {
        __str = savedStateHandle["str"]
        if (__str == null) {
          throw IllegalArgumentException("Argument \"str\" is marked as non-null but was passed a null value")
        }
      } else {
        __str = "null"
      }
      return FindObjectFragmentArgs(__str)
    }
  }
}
