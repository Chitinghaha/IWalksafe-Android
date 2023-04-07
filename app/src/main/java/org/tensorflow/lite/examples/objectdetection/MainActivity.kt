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


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityMainBinding
import org.tensorflow.lite.examples.objectdetection.fragments.*
import java.util.*

/**
 * Main entry point into our app. This app follows the single-activity pattern, and all
 * functionality is implemented in the form of fragments.
 */
//Binding  https://ithelp.ithome.com.tw/articles/10244984
//Binding https://xnfood.com.tw/android-databinding-mvvc/
class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private  lateinit var appBarConfiguration:AppBarConfiguration
    private  lateinit var navHostFragment: NavHostFragment
    private  lateinit var navController:NavController
    private  lateinit var toolbar: Toolbar
    private  lateinit var bottomNavView: BottomNavigationView



    private  var activity_channel = 0


    //activity-lifecycle https://ithelp.ithome.com.tw/articles/10207640
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        activityMainBinding.toolbar.setupWithNavController(navController,appBarConfiguration)
        activityMainBinding.navigation.setupWithNavController(navController)


        Log.d("compose", "Main onCreate()")



//
//        //fragment change
//        activityMainBinding.button1.setOnClickListener{
//            replaceFragment(CameraFragment())
//        }
//
//        activityMainBinding.button2.setOnClickListener{
//            replaceFragment(CompassFragment())
//        }


//        activityMainBinding.navigation.setOnNavigationItemSelectedListener {
//            val fragmentManager = supportFragmentManager
//            val fragmentTransition = fragmentManager.beginTransaction()
//
//            when(it.itemId){
//                R.id.Home -> {
//
//                    fragmentTransition.replace(R.id.fragment_container,  HomeFragment())
//                    fragmentTransition.commit()
//                }
//
//                R.id.Search-> {
//                    fragmentTransition.replace(R.id.fragment_container,  CameraFragment())
//                    fragmentTransition.commit()
//
//                }
//                R.id.WalkModel -> {
////                    activity_channel = 0
////                    displaySpeechRecognizer()
//
//                    fragmentTransition.replace(R.id.fragment_container, WalkModeFragment())
//                    fragmentTransition.commit()
//                }
//
//                R.id.Setting-> {
//                    fragmentTransition.replace(R.id.fragment_container, SettingsFragment())
//                    fragmentTransition.commit()
//
//                }
//
//                else ->{
//
//                }
//            }
//            true
//        }
    }



    private fun displaySpeechRecognizer() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please say something")
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-TW")
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val spokenText: String? =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                    results?.get(0)
                }
            if (spokenText != null) {
                channel_funtion(spokenText)
            }

            Log.d("compose", "Main onActivityResult()")
        } else if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            val spokenText: String? =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                    results?.get(0)
                }
            if (spokenText != null) {
                activity_channel = 1
                channel_funtion(spokenText)
            }
        }
    }

    private  fun channel_funtion(spokenText:String){
        Toast.makeText(this,spokenText,Toast.LENGTH_SHORT).show()
        if (activity_channel == 0){
            //Manager
            val fragmentManager = supportFragmentManager
            val fragmentTransition = fragmentManager.beginTransaction()

            fragmentTransition.remove(CameraFragment())

            // Creating the new Fragment with the name passed in.
            val bundle = Bundle()
            val fragment = CameraFragment()
            bundle.putString("String", spokenText)
            fragment.arguments = bundle
            fragmentTransition.add(R.id.fragment_container, fragment,"A")
            fragmentTransition.commit()
        }


        else if(activity_channel == 1){
            val gmmIntentUri =
                Uri.parse("google.navigation:q=$spokenText&mode=w")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            // check have google map app
            mapIntent.resolveActivity(packageManager)?.let {
                startActivity(mapIntent)
            }

        }

        else{
            Toast.makeText(this,"error",Toast.LENGTH_SHORT).show()
        }


    }

    override fun onResume() {
        super.onResume()
        Log.d("compose", "Main onResume()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("compose", "Main onRestart()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("compose", "Main onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("compose", "Main onStop()")
    }

    override fun onStart() {
        super.onStart()
        Log.d("compose", "Main onStart()")
    }



//    fun newInstance(param: String?): CameraFragment? {
//        val fragment = CameraFragment()
//        val args = Bundle()
//        args.putString("name", param)
//        fragment.setArguments(args)
//        return fragment
//    }

    private fun initFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.add(R.id.fragment_container,fragment)
        fragmentTransition.commit()
    }
//
//    private fun replaceFragment(fragment: Fragment) {
//        val fragmentManager = supportFragmentManager
//        val fragmentTransition = fragmentManager.beginTransaction()
//        fragmentTransition.replace(R.id.fragment_container,fragment)
//        fragmentTransition.commit()
//    }






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
        Log.d("compose", "Main onBackPressed()")
    }



    public override fun onDestroy() {
        super.onDestroy()
        Log.d("compose", "Main onDestroy()")
    }
//    private fun dovibrate() {
//        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//        if (Build.VERSION.SDK_INT >= 26) {
//            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
//        } else {
//            vibrator.vibrate(50)
//        }
//    }

}
