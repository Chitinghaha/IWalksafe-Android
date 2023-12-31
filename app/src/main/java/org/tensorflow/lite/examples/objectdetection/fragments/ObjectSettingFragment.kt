package org.tensorflow.lite.examples.objectdetection.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import org.tensorflow.lite.examples.objectdetection.MyAdapter
import org.tensorflow.lite.examples.objectdetection.MyAdapterForObject
import org.tensorflow.lite.examples.objectdetection.R


class ObjectSettingFragment : Fragment(R.layout.fragment_object_setting) {
    private lateinit var btnSpeak : Button
    private lateinit var btn : Button
    private lateinit var Et : EditText
    private lateinit var btnSTT : Button
    private lateinit var manager : RecyclerView.LayoutManager
    private lateinit var myAdapter: MyAdapterForObject
    lateinit var data : MutableList<String>
    private lateinit var database: DatabaseReference

    private lateinit var  pref : SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    lateinit var recyclerView: RecyclerView


    private lateinit var chipNavigationBar: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        pref = activity?.let { PreferenceManager.getDefaultSharedPreferences(it) }!!
        editor = pref.edit()!!
        val name : MutableSet<String> = pref.getStringSet("object", mutableSetOf("error","error")) as MutableSet<String>
        Log.d("compose", name.toString())
        data = name.toMutableList()

//        data = mutableListOf("杯子", "電腦", "遙控器", "椅子", "床")
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        Et = view.findViewById(R.id.obText)
//        btn = view.findViewById(R.id.ob_btn)
        btnSpeak = view.findViewById(R.id.ob_buttonSound)
//        btn.setOnClickListener { addItem() }-
        btnSpeak.setOnClickListener { displaySpeechRecognizer_second()  }


        manager= LinearLayoutManager(context)
        myAdapter= context?.let { MyAdapterForObject(it, data,{index -> deltem(index)}) { str -> passItem(str) } }!!
        recyclerView = view.findViewById<RecyclerView>(R.id.ob_r_view).apply{
            layoutManager = manager
            adapter = myAdapter
        }
        chipNavigationBar = (activity as AppCompatActivity).findViewById<View>(R.id.navigation) as BottomNavigationView
        chipNavigationBar.animate().translationY(chipNavigationBar.height.toFloat()).duration = 1000



        super.onViewCreated(view, savedInstanceState)
    }

    private fun displaySpeechRecognizer_second() {

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please say something")
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-TW")
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
        activity?.startActivityForResult(intent, 0)


    }

//    private fun addItem() {
//        if(Et.text.isNotEmpty()){
//            data.add(Et.text.toString())
//            myAdapter.setItems(data)
//            Et.setText("")
//            val arr=data.toMutableSet()
//            editor.remove("object")
//            editor.putStringSet("object", arr)?.apply()
//        }
//        else
//            Toast.makeText(requireContext(), "請輸入文字" , Toast.LENGTH_SHORT).show()
//    }

    private fun passItem(str :String) {
//        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
        val action = ObjectSettingFragmentDirections.actionObjectSettingFragmentToFindObjectFragment(str)
        view?.findNavController()?.navigate(action)

    }

    override fun onStop() {
        chipNavigationBar = (activity as AppCompatActivity).findViewById<View>(R.id.navigation) as BottomNavigationView
        chipNavigationBar.animate().translationY(0F).duration = 1000
        super.onStop()
    }

    override fun onDestroy() {
        chipNavigationBar = (activity as AppCompatActivity).findViewById<View>(R.id.navigation) as BottomNavigationView
        chipNavigationBar.animate().translationY(0F).duration = 1000
        super.onDestroy()
    }

    private fun deltem(index: Int) {
        if( ::myAdapter.isInitialized){
            data.removeAt(index)
            myAdapter.setItems(data)
            val arr=data.toMutableSet()
            editor.remove("object")
            editor.putStringSet("object", arr)?.apply()
        }
    }

}