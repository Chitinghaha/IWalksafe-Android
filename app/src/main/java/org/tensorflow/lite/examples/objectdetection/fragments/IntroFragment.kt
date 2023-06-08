package org.tensorflow.lite.examples.objectdetection.fragments

import android.hardware.Sensor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.tensorflow.lite.examples.objectdetection.R


class IntroFragment : Fragment() {

    private lateinit var chipNavigationBar: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chipNavigationBar = activity?.findViewById<View>(R.id.navigation) as BottomNavigationView
        chipNavigationBar.animate().translationY(chipNavigationBar.height.toFloat()).duration = 1000
    }

    override fun onDestroy() {
        Log.d("compose", "Compass onDestroy()")
        chipNavigationBar.animate().translationY(0F).duration = 1000
        super.onDestroy()
    }

}
