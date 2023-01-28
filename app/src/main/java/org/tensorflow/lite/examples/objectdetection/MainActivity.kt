/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tensorflow.lite.examples.objectdetection


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityMainBinding

/**
 * Main entry point into our app. This app follows the single-activity pattern, and all
 * functionality is implemented in the form of fragments.
 */
//Binding  https://ithelp.ithome.com.tw/articles/10244984
//Binding https://xnfood.com.tw/android-databinding-mvvc/
class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding


    //activity-lifecycle https://ithelp.ithome.com.tw/articles/10207640
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        //activity change to CompassMainActivity
        activityMainBinding.button1.setOnClickListener{
            finish()
            val intent = Intent(this, CompassMainActivity::class.java)
            startActivity(intent)
        }



//        //fragment init
//        initFragment(CompassFragment())
//
//        //fragment change
//        activityMainBinding.button1.setOnClickListener{
//            replaceFragment(CameraFragment())
//        }
//
//        activityMainBinding.button2.setOnClickListener{
//            replaceFragment(CompassFragment())
//        }
    }

    private fun initFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.add(R.id.fragment_container,fragment)
        fragmentTransition.commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container,fragment)
        fragmentTransition.commit()
    }



    //https://ithelp.ithome.com.tw/articles/10216949
    override fun onBackPressed() {
        Toast.makeText(this, "你確定要離開?", Toast.LENGTH_SHORT).show()
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            // Workaround for Android Q memory leak issue in IRequestFinishCallback$Stub.
            // (https://issuetracker.google.com/issues/139738913)
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }
}
