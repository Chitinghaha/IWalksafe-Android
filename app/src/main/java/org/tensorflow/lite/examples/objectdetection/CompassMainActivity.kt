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
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityCompassMainBinding


class CompassMainActivity : Activity(), SensorEventListener {
    private lateinit var activityCompassMainBinding: ActivityCompassMainBinding

    //recoding angle
    private var currentDegree =0f
    //device sensor manager
    private var mSensorManager: SensorManager? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCompassMainBinding = ActivityCompassMainBinding.inflate(layoutInflater)
        setContentView(activityCompassMainBinding.root)
        initData()


        //activity change MainActivity
        activityCompassMainBinding.button2.setOnClickListener{
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initData(){
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager?

    }

    override fun onDestroy() {
        super.onDestroy()
        mSensorManager?.unregisterListener(this,
            mSensorManager?.getDefaultSensor(Sensor.TYPE_ORIENTATION))
    }

    override fun onResume() {
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


        var right_degree= -currentDegree
        var print = (-currentDegree).toString()
        activityCompassMainBinding.textView.setText(print)

        if(right_degree > 355 || right_degree<5){
            dovibrate()
        }
    }

    private fun dovibrate() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }


}