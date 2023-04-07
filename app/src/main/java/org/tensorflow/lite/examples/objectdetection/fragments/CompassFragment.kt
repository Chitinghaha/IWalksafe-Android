package org.tensorflow.lite.examples.objectdetection.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.tensorflow.lite.examples.objectdetection.MainActivity
import org.tensorflow.lite.examples.objectdetection.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CompassFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompassFragment : Fragment(R.layout.fragment_compass), SensorEventListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var text : TextView

    //recoding angle
    private var currentDegree =0f
    //device sensor manager
    private var mSensorManager: SensorManager? =null

    private lateinit var compass:ImageView
    private lateinit var activity: AppCompatActivity
    private lateinit var chipNavigationBar: BottomNavigationView





    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


        initData()

    }

    private fun initData(){
        mSensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compass= view.findViewById(R.id.imgCompass)
        activity = view.context as AppCompatActivity
        chipNavigationBar = activity.findViewById<View>(R.id.navigation) as BottomNavigationView
        chipNavigationBar.animate().translationY(chipNavigationBar.height.toFloat()).duration = 1000
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        // val btn1: Button = findViewById(R.id.button1)
        return inflater.inflate(R.layout.fragment_compass, container, false)
    }

    override fun onResume() {
        Log.d("compose", "Compass onResume()")
        super.onResume()
        @Suppress("DEPRECATION")
        mSensorManager?.registerListener(this,
            mSensorManager?.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CompassFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CompassFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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

        compass.startAnimation(rotationAnimation)
        currentDegree=(-degree).toFloat()


        val right_degree= -currentDegree
        val print = (-currentDegree).toString()
        //activityCompassMainBinding.textView.setText(print)

        if(right_degree > 355 || right_degree<5){
            dovibrate()
        }
    }

    private fun dovibrate() {
        val pattern = longArrayOf(0, 150)
        val vibrator = requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(50)
        }
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onDestroy() {
        Log.d("compose", "Compass onDestroy()")
        chipNavigationBar.animate().translationY(0F).duration = 1000
        super.onDestroy()
        mSensorManager?.unregisterListener(this,
            mSensorManager?.getDefaultSensor(Sensor.TYPE_ORIENTATION))
    }


    override fun onPause() {
        Log.d("compose", "Compass fragment onPause()")
        super.onPause()
    }


    override fun onStop() {
        Log.d("compose", "Compass fragment onStop()")
        super.onStop()
    }

    override fun onStart() {
        Log.d("compose", "Compass fragment onStart()")
        super.onStart()
    }

}