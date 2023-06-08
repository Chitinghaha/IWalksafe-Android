package org.tensorflow.lite.examples.objectdetection.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import org.tensorflow.lite.examples.objectdetection.R



class NavigationFragment : Fragment() {


    private lateinit var tohome: Button
    private lateinit var tomap: Button


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tohome = view.findViewById(R.id.tohome)
        tomap = view.findViewById(R.id.tomap)

        tohome.setOnClickListener { gomap() }
        tomap.setOnClickListener { displaySpeechRecognizer_second() }
    }

    private  fun gomap(){
        val gmmIntentUri =
            Uri.parse("google.navigation:q=24°47'12 120°59'49&mode=w")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        // check have google map app
        getActivity()?.let {
            mapIntent.resolveActivity(it.packageManager)?.let {
                startActivity(mapIntent)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    private fun displaySpeechRecognizer_second() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please say something")
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-TW")
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
        activity?.startActivityForResult(intent, 1)
    }

}