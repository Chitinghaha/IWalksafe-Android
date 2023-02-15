package org.tensorflow.lite.examples.objectdetection

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.Toast
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityCompassMainBinding


class CompassMainActivity : Activity(), SensorEventListener {
    private lateinit var activityCompassMainBinding: ActivityCompassMainBinding

    //recoding angle
    private var currentDegree =0f
    //device sensor manager
    private var mSensorManager: SensorManager? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("compose", "Compass onCreate()")
        super.onCreate(savedInstanceState)
        activityCompassMainBinding = ActivityCompassMainBinding.inflate(layoutInflater)
        setContentView(activityCompassMainBinding.root)
        initData()
    }

    private fun initData(){
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager?

    }

    override fun onDestroy() {
        Log.d("compose", "Compass onDestroy()")
        super.onDestroy()
        mSensorManager?.unregisterListener(this,
            mSensorManager?.getDefaultSensor(Sensor.TYPE_ORIENTATION))
    }

    override fun onResume() {
        Log.d("compose", "Compass onResume()")
        super.onResume()
        @Suppress("DEPRECATION")
        mSensorManager?.registerListener(this,
            mSensorManager?.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val degree =Math.round(event?.values?.get(0)!!)

        val rotationAnimation = RotateAnimation(
            currentDegree,
            (-degree).toFloat(),
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )

        rotationAnimation.duration = 210
        rotationAnimation.fillAfter = true

        activityCompassMainBinding.imgCompass.startAnimation(rotationAnimation)
        currentDegree=(-degree).toFloat()


        val right_degree= -currentDegree
        val print = (-currentDegree).toString()
        activityCompassMainBinding.textView.setText(print)

        if(right_degree > 355 || right_degree<5){
            dovibrate()
        }
    }

    private fun dovibrate() {
        val pattern = longArrayOf(0, 150)
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(50)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d("compose", "Compass onBackPressed()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("compose", "Compass onRestart()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("compose", "Compass onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("compose", "Compass onStop()")
    }

    override fun onStart() {
        super.onStart()
        Log.d("compose", "Compass onStart()")
    }


}