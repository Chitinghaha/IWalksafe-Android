package org.tensorflow.lite.examples.objectdetection

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityCompassMainBinding


class CompassMainActivity : Activity() {
    private lateinit var activityCompassMainBinding: ActivityCompassMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCompassMainBinding = ActivityCompassMainBinding.inflate(layoutInflater)
        setContentView(activityCompassMainBinding.root)


        //activity change MainActivity
        activityCompassMainBinding.button2.setOnClickListener{
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }


}