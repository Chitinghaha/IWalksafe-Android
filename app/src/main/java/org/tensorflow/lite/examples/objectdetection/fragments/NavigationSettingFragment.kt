package org.tensorflow.lite.examples.objectdetection.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import org.tensorflow.lite.examples.objectdetection.MyAdapter
import org.tensorflow.lite.examples.objectdetection.R




class NavigationSettingFragment : Fragment() {



    private lateinit var btnSpeak : Button
    private lateinit var btn : Button
    private lateinit var Et : EditText
    private lateinit var btnSTT : Button
    private lateinit var manager : RecyclerView.LayoutManager
    private lateinit var myAdapter: MyAdapter
    lateinit var data : MutableList<String>
    private lateinit var database: DatabaseReference

    private lateinit var  pref : SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        pref = context?.getSharedPreferences("name", Context.MODE_PRIVATE)!!
        editor = pref.edit()!!
        val name : MutableSet<String> = pref.getStringSet("name", mutableSetOf("cccc","cccc")) as MutableSet<String>
        data = name.toMutableList()
//        data = mutableListOf("家", "故宮", "士林夜市", "台北動物園", "清大人社院","中央公園","台灣大學","清大小吃部")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        Et = view.findViewById(R.id.eT)
        btn = view.findViewById(R.id.btn)
        btnSpeak = view.findViewById(R.id.buttonSound)
        btn.setOnClickListener { addItem() }

        btnSpeak.setOnClickListener { displaySpeechRecognizer_second()  }


        manager= LinearLayoutManager(context)
        myAdapter= context?.let { it1 -> MyAdapter(it1, data){ index -> deltem(index)} }!!
        recyclerView = view.findViewById<RecyclerView>(R.id.r_view).apply{
            layoutManager = manager
            adapter = myAdapter
        }


        super.onViewCreated(view, savedInstanceState)
    }

    private fun displaySpeechRecognizer_second() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please say something")
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-TW")
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
        activity?.startActivityForResult(intent, 1)
    }

    private fun addItem() {
        if(Et.text.isNotEmpty()){
            data.add(Et.text.toString())
            myAdapter.setItems(data)
            Et.setText("")
            val arr=data.toMutableSet()
            editor.remove("name")
            editor.putStringSet("name", arr)?.apply()
        }
        else
            Toast.makeText(requireContext(), "請輸入文字" , Toast.LENGTH_SHORT).show()
    }

    private fun deltem(index: Int) {
        if( ::myAdapter.isInitialized){
            data.removeAt(index)
            myAdapter.setItems(data)
            val arr=data.toMutableSet()
            editor.remove("name")
            editor.putStringSet("name", arr)?.apply()
        }
    }

}