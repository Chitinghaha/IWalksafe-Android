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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import org.tensorflow.lite.examples.objectdetection.fragments.CompassFragment
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityMainBinding
import org.tensorflow.lite.examples.objectdetection.fragments.CameraFragment
import java.util.*

/**
 * Main entry point into our app. This app follows the single-activity pattern, and all
 * functionality is implemented in the form of fragments.
 */
//Binding  https://ithelp.ithome.com.tw/articles/10244984
//Binding https://xnfood.com.tw/android-databinding-mvvc/
class MainActivity : AppCompatActivity() , TextToSpeech.OnInitListener{
    private val SPEECH_REQUEST_CODE = 0

    private lateinit var activityMainBinding: ActivityMainBinding

    private  var activity_channel = 0

    private var tts: TextToSpeech? = null

    private  var prefragment = 0

    //activity-lifecycle https://ithelp.ithome.com.tw/articles/10207640
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        tts = TextToSpeech(this, this)

//        //activity change to CompassMainActivity
//        activityMainBinding.button1.setOnLongClickListener{
//            //dovibrate()
//            val intent = Intent(this, CompassMainActivity::class.java)
//            startActivity(intent)
//            true
//        }




        Log.d("compose", "Main onCreate()")


        //fragment init
//        initFragment(CameraFragment())
//
//        //fragment change
//        activityMainBinding.button1.setOnClickListener{
//            replaceFragment(CameraFragment())
//        }
//
//        activityMainBinding.button2.setOnClickListener{
//            replaceFragment(CompassFragment())
//        }
        activityMainBinding.navigation.setOnNavigationItemSelectedListener {
            val fragmentManager = supportFragmentManager
            val fragmentTransition = fragmentManager.beginTransaction()

            when(it.itemId){
                R.id.Sound -> {
                    val fragmentManager = supportFragmentManager
                    val fragmentTransition = fragmentManager.beginTransaction()

//                    val fragmentA = fragmentManager.findFragmentByTag("A")
//                    val fragmentB = fragmentManager.findFragmentByTag("B")
//
//                    if (fragmentA != null) { fragmentTransition.remove(CameraFragment())
//                        Log.d("compose", "Remove CameraFragment")//exist
//                    }
//
//                    if (fragmentB != null) { fragmentTransition.remove(CompassFragment())
//                        Log.d("compose", "Remove CompassFragment")//exist
//                    }

//                    while(fragmentManager.backStackEntryCount > 0) {
//                        Log.d("compose", "NUMBER"+fragmentManager.backStackEntryCount.toString())
//                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    }
                   // fragmentTransition.replace(R.id.fragment_container,CameraFragment())
                    fragmentTransition.replace(R.id.fragment_container, CameraFragment())
                    fragmentTransition.commit()
                }
                R.id.Detection -> {
                    activity_channel = 0
                    displaySpeechRecognizer()
                }
//                R.id.Found -> {
//                    val fragmentManager = supportFragmentManager
//                    val fragmentTransition = fragmentManager.beginTransaction()
//                    fragmentTransition.remove(CameraFragment())
//                    fragmentTransition.add(R.id.fragment_container, CameraFragment())
//                    fragmentTransition.commit()
//                }

                R.id.compress-> {
                    fragmentTransition.replace(R.id.fragment_container, CompassFragment())
//                    fragmentTransition.remove(CameraFragment())
//                    fragmentTransition.add(R.id.fragment_container, CompassFragment(),"B")
                    fragmentTransition.commit()
                }
                R.id.Navigation-> {
                    activity_channel = 1
                    displaySpeechRecognizer()
                }

                else ->{

                }
            }
            true
        }
    }




    private fun speakOut(etSpeak:String) {
        if(tts != null) {
            tts!!.stop()
            tts!!.speak(etSpeak, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

//    private fun displaySpeechRecognizer() {
//        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please say something")
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-TW")
//        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
//
//
//        startActivityForResult(intent, SPEECH_REQUEST_CODE)
//    }

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
        }
    }

    private  fun channel_funtion(spokenText:String){
        speakOut(spokenText)
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

//    private fun initFragment(fragment: Fragment) {
//        val fragmentManager = supportFragmentManager
//        val fragmentTransition = fragmentManager.beginTransaction()
//        fragmentTransition.add(R.id.fragment_container,fragment)
//        fragmentTransition.commit()
//    }
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

    override fun onInit(p0: Int) {
        if (p0 == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.TAIWAN)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language not supported!")
            }
        }

    }

    public override fun onDestroy() {
        // Shutdown TTS when
        // activity is destroyed
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
        Log.d("compose", "Main onDestroy()")
    }

    private fun dovibrate() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(50)
        }
    }

}
