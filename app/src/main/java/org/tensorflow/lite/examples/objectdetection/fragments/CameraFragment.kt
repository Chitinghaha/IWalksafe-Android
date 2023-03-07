/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tensorflow.lite.examples.objectdetection.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.core.ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper
import org.tensorflow.lite.examples.objectdetection.R
import org.tensorflow.lite.examples.objectdetection.databinding.FragmentCameraBinding
import org.tensorflow.lite.task.vision.detector.Detection
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


// fragment https://medium.com/@waynechen323/android-%E5%9F%BA%E7%A4%8E%E7%9A%84-fragment-%E4%BD%BF%E7%94%A8%E6%96%B9%E5%BC%8F-730858c12a43
// fragment https://ithelp.ithome.com.tw/articles/10262921
// Kotlin 繼承
class CameraFragment() : Fragment(R.layout.fragment_camera), ObjectDetectorHelper.DetectorListener {

    private val TAG = "ObjectDetection"

    // ?：做 null check 後，不為空的話再執行   !!：堅持不會是空值，執行就是了
    private var _fragmentCameraBinding: FragmentCameraBinding? = null

    // getter,setter https://www.delftstack.com/zh-tw/howto/kotlin/kotlin-set/
    private val fragmentCameraBinding
        get() = _fragmentCameraBinding!!

    // lateinit進行「延遲初始化  https://carterchen247.medium.com/kotlin%E4%BD%BF%E7%94%A8%E5%BF%83%E5%BE%97-%E5%8D%81%E4%B8%80-lateinit-vs-lazy-1ef96bc5b3b3
    private lateinit var objectDetectorHelper: ObjectDetectorHelper

    //存取bmp
    private lateinit var bitmapBuffer: Bitmap

    private var preview: Preview? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null

    private var mp_bed: MediaPlayer? = null
    private var mp_chair: MediaPlayer? = null
    private var mp_cup: MediaPlayer? = null
    private var mp_laptop: MediaPlayer? = null
    private var mp_remote: MediaPlayer? = null
    private var foundsound: MediaPlayer? = null
    private var beep1: MediaPlayer? = null
    private var beep2: MediaPlayer? = null
    private var beep3: MediaPlayer? = null

    //private var results: List<Detection> = LinkedList<Detection>()
    private var AllObject = mutableSetOf("bed","chair","cup","laptop","remote","person","dog","cat")

    private var Objectsound = mutableSetOf<String>()
    private var Warningsound = mutableSetOf<String>()

    /** Blocking camera operations are performed using this executor */

    //https://ithelp.ithome.com.tw/articles/10207124
    private lateinit var cameraExecutor: ExecutorService

    //find_name
    private  var findname: String? =null

    private  var flage: Boolean?= false

    //firebase
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("compose", "fragment onCreate()")
        super.onCreate(savedInstanceState)
        mp_bed = MediaPlayer.create(context, R.raw.bed)
        mp_chair = MediaPlayer.create(context, R.raw.chair)
        mp_cup = MediaPlayer.create(context, R.raw.cup)
        mp_laptop= MediaPlayer.create(context, R.raw.computer)
        mp_remote = MediaPlayer.create(context, R.raw.remote)
        beep1 = MediaPlayer.create(context, R.raw.beep1)
        beep2 = MediaPlayer.create(context, R.raw.beep2)
        beep3 = MediaPlayer.create(context, R.raw.beep4)
        foundsound = MediaPlayer.create(context, R.raw.foundsound)
        database = FirebaseDatabase.getInstance().reference
    }

    private fun initButton(){
        for( ob in AllObject){
            database.child("Button").child(ob).get().addOnSuccessListener {
                if(it.value == true ){
                    when (ob) {
                        "bed" ->{ Objectsound.add("bed")
                            fragmentCameraBinding.bottomSheetLayout.ckbBed.isChecked = true}
                        "chair"-> { Objectsound.add("chair")
                            fragmentCameraBinding.bottomSheetLayout.ckbChair.isChecked = true}
                        "cup" -> { Objectsound.add("cup")
                            fragmentCameraBinding.bottomSheetLayout.ckbCup.isChecked = true}
                        "laptop" ->{ Objectsound.add("laptop")
                            fragmentCameraBinding.bottomSheetLayout.ckbLaptop.isChecked = true}
                        "remote" ->{ Objectsound.add("remote")
                            fragmentCameraBinding.bottomSheetLayout.ckbRemote.isChecked = true}
                        "person" ->{ Warningsound.add("person")
                            fragmentCameraBinding.bottomSheetLayout.ckbPerson.isChecked = true}
                        "dog" ->{ Warningsound.add("dog")
                            fragmentCameraBinding.bottomSheetLayout.ckbDog.isChecked = true}
                        "cat" ->{ Warningsound.add("cat")
                            fragmentCameraBinding.bottomSheetLayout.ckbCat.isChecked = true}
                        else -> println("Other")
                    }
                }

            }.addOnFailureListener {
                Log.d("GGG", "Error getting data", it)
            }
        }
    }


    override fun onResume() {
        Log.d("compose", "fragment onResume()")
        super.onResume()

        // Make sure that all permissions are still present, since the
        // user could have removed them while the app was in paused state
        //確認fragment的權限
        if (!PermissionsFragment.hasPermissions(requireContext())) {

            Log.d("compose","errorrrrrrrrr")
            //Navigation 用於管理 fragment
            //https://ithelp.ithome.com.tw/articles/10225937
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                .navigate(CameraFragmentDirections.actionCameraToPermissions())
        }
    }


    // Fragment即將被結束
    override fun onDestroyView() {
        Log.d("compose", "fragment onDestroyView()")
        //_fragmentCameraBinding = null

        // Shut down our background executor
        cameraExecutor.shutdown()
        super.onDestroyView()
    }


    // 使用LayoutInflater內的inflate方法，將你所設定的layout包裝成一個view(視圖)並使用
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("compose", "fragment onCreateView()")

        _fragmentCameraBinding = FragmentCameraBinding.inflate(inflater, container, false)
        initButton()


        //STT
        val data = arguments
        activity?.runOnUiThread {
            Toast.makeText(requireContext(),data?.getString("String").toString(), Toast.LENGTH_SHORT).show()
        }
        findname = data?.getString("String").toString()
        return fragmentCameraBinding.root
    }




    // onViewCreated() 適合初始化 view 的狀態、觀察 liveData 或在此設置 recycler 的 adapter, viewPager2
    // @SuppressLint("MissingPermission") 可以禁止權限檢查
    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("compose", "fragment onViewCreated()")
        super.onViewCreated(view, savedInstanceState)


        /*
        fragmentCameraBinding.bottomSheetLayout.btnMain.setOnClickListener{
            objectDetectorHelper.threshold -= 0.1f
            updateControlsUi()
        }
        */

        // Toast.makeText(getContext() , "Hello", Toast.LENGTH_LONG).show()


        objectDetectorHelper = ObjectDetectorHelper(
            context = requireContext(),
            objectDetectorListener = this)

        // Initialize our background executor
        cameraExecutor = Executors.newSingleThreadExecutor()

        // Wait for the views to be properly laid out
        fragmentCameraBinding.viewFinder.post {
            // Set up the camera and its use cases
            setUpCamera()
        }

        // Attach listeners to UI control widgets
        initBottomSheetControls()
    }




    private fun initBottomSheetControls() {

        // When clicked, lower detection score threshold floor
        fragmentCameraBinding.bottomSheetLayout.thresholdMinus.setOnClickListener {
            if (objectDetectorHelper.threshold >= 0.1) {
                objectDetectorHelper.threshold -= 0.1f
                updateControlsUi()
            }
        }

        // When clicked, raise detection score threshold floor
        fragmentCameraBinding.bottomSheetLayout.thresholdPlus.setOnClickListener {
            if (objectDetectorHelper.threshold <= 0.8) {
                objectDetectorHelper.threshold += 0.1f
                updateControlsUi()
            }
        }

        // When clicked, reduce the number of objects that can be detected at a time
        fragmentCameraBinding.bottomSheetLayout.maxResultsMinus.setOnClickListener {
            if (objectDetectorHelper.maxResults > 1) {
                objectDetectorHelper.maxResults--
                updateControlsUi()
            }
        }

        // When clicked, increase the number of objects that can be detected at a time
        fragmentCameraBinding.bottomSheetLayout.maxResultsPlus.setOnClickListener {
            if (objectDetectorHelper.maxResults < 5) {
                objectDetectorHelper.maxResults++
                updateControlsUi()
            }
        }

        // When clicked, decrease the number of threads used for detection
        fragmentCameraBinding.bottomSheetLayout.threadsMinus.setOnClickListener {
            if (objectDetectorHelper.numThreads > 1) {
                objectDetectorHelper.numThreads--
                updateControlsUi()
            }
        }

        // When clicked, increase the number of threads used for detection
        fragmentCameraBinding.bottomSheetLayout.threadsPlus.setOnClickListener {
            if (objectDetectorHelper.numThreads < 4) {
                objectDetectorHelper.numThreads++
                updateControlsUi()
            }
        }

//        fragmentCameraBinding.btnMain.setOnClickListener{
//           // dovibrate()
//
//            if(fragmentCameraBinding.btnMain.text == "聲音關閉") {
//                fragmentCameraBinding.btnMain.text = "聲音開啟"
//            }
//
//            else {
//                fragmentCameraBinding.btnMain.text = "聲音關閉"
//            }
//
//            Toast.makeText(getContext() , fragmentCameraBinding.btnMain.text, Toast.LENGTH_LONG).show()
//        }


//        fragmentCameraBinding.btnStt.setOnClickListener{
//            //dovibrate()
//            findname = null
//        }

//        fragmentCameraBinding.btnStt.setOnLongClickListener {
//           // dovibrate()
//            displaySpeechRecognizer()
//            true
//        }

//        fragmentCameraBinding.mapbtn.setOnClickListener {
//           // dovibrate()
//            displaySpeechRecognizer_second()
//        }

//        fragmentCameraBinding.compassbtn.setOnClickListener {
//
//        }



        //check box
        fragmentCameraBinding.bottomSheetLayout.ckbBed.setOnCheckedChangeListener {_, isChecked ->

            if(isChecked){//判斷框1是否被選定
                Objectsound.add("bed")   //若選定，則將字串加該項目
                Toast.makeText(context, "床", Toast.LENGTH_LONG).show()
                writeNewPost("bed", true )
            }
            else{
                Objectsound.remove("bed")   //若選定，則將字串加該項目
                writeNewPost("bed", false )
            }
        }

        fragmentCameraBinding.bottomSheetLayout.ckbCup.setOnCheckedChangeListener {_, isChecked -> //判斷框2是否被選定
            if(isChecked){//判斷框1是否被選定
                Objectsound.add("cup")   //若選定，則將字串加該項目
                Toast.makeText(context,"杯子", Toast.LENGTH_LONG).show()
                writeNewPost("cup", true )
            }
            else{
                Objectsound.remove("cup")   //若選定，則將字串加該項目
                writeNewPost("cup", false )
            }
        }

        fragmentCameraBinding.bottomSheetLayout.ckbChair.setOnCheckedChangeListener {_, isChecked -> //判斷框3是否被選定
            if(isChecked){//判斷框1是否被選定
                Objectsound.add("chair")   //若選定，則將字串加該項目
                Toast.makeText(context,"chair", Toast.LENGTH_LONG).show()
                writeNewPost("chair", true )
            }
            else{
                Objectsound.remove("chair")
                writeNewPost("chair", false )
            }

        }

        fragmentCameraBinding.bottomSheetLayout.ckbLaptop.setOnCheckedChangeListener {_, isChecked -> //判斷框3是否被選定
            if(isChecked){//判斷框1是否被選定
                Objectsound.add("laptop")   //若選定，則將字串加該項目
                Toast.makeText(context,"筆電", Toast.LENGTH_LONG).show()
                writeNewPost("laptop", true )
            }
            else{
                Objectsound.remove("laptop")   //若選定，則將字串加該項目
                writeNewPost("laptop", false )
            }

        }

        fragmentCameraBinding.bottomSheetLayout.ckbRemote.setOnCheckedChangeListener {_, isChecked -> //判斷框3是否被選定
            if(isChecked){//判斷框1是否被選定
                Objectsound.add("remote")   //若選定，則將字串加該項目
                Toast.makeText(context,"遙控器", Toast.LENGTH_LONG).show()
                writeNewPost("remote", true )
            }
            else{
                Objectsound.remove("remote")   //若選定，則將字串加該項目
                writeNewPost("remote", false )
            }

        }


        //warning
        fragmentCameraBinding.bottomSheetLayout.ckbPerson.setOnCheckedChangeListener {_, isChecked -> //判斷框4是否被選定
            if(isChecked){//判斷框1是否被選定
                Warningsound.add("person")   //若選定，則將字串加該項目
                Toast.makeText(context,"人", Toast.LENGTH_LONG).show()
                writeNewPost("person", true )
            }
            else{
                Warningsound.remove("person")   //若選定，則將字串加該項目
                writeNewPost("person", false )
            }

        }

        fragmentCameraBinding.bottomSheetLayout.ckbDog.setOnCheckedChangeListener {_, isChecked -> //判斷框5是否被選定
            if(isChecked){//判斷框1是否被選定
                Warningsound.add("dog")   //若選定，則將字串加該項目
                Toast.makeText(context,"狗", Toast.LENGTH_LONG).show()
                writeNewPost("dog", true )
            }
            else{
                Warningsound.remove("dog")   //若選定，則將字串加該項目
                writeNewPost("dog", false )
            }

        }

        fragmentCameraBinding.bottomSheetLayout.ckbCat.setOnCheckedChangeListener {_, isChecked -> //判斷框6是否被選定
            if(isChecked){//判斷框1是否被選定
                Warningsound.add("cat")   //若選定，則將字串加該項目
                Toast.makeText(getContext() ,"貓", Toast.LENGTH_LONG).show()
                writeNewPost("cat", true )
            }
            else{
                Warningsound.remove("cat")   //若選定，則將字串加該項目
                writeNewPost("cat", false )
            }

        }

        fragmentCameraBinding.bottomSheetLayout.ckb.setOnCheckedChangeListener {_, isChecked -> //判斷框6是否被選定
            flage = isChecked
        }



        // When clicked, change the underlying hardware used for inference. Current options are CPU
        // GPU, and NNAPI
        fragmentCameraBinding.bottomSheetLayout.spinnerDelegate.setSelection(0, false)
        fragmentCameraBinding.bottomSheetLayout.spinnerDelegate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    objectDetectorHelper.currentDelegate = p2
                    updateControlsUi()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    /* no op */
                }
            }

        // When clicked, change the underlying model used for object detection
        fragmentCameraBinding.bottomSheetLayout.spinnerModel.setSelection(0, false)
        fragmentCameraBinding.bottomSheetLayout.spinnerModel.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    objectDetectorHelper.currentModel = p2
                    updateControlsUi()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    /* no op */
                }
            }
    }

    private fun writeNewPost(btnId: String, state: Boolean) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        database.child("Button").child(btnId).setValue(state)
    }


    private fun insertUser(btnId: String, state: Boolean ) {
//        val user = Btn(btnId, state)
        database.child("Button").child(btnId).setValue(state)
    }


    private fun find(btnId: String) {
        database.child("Button").child(btnId).get().addOnSuccessListener {
            Log.d("GGG", "Got value ${it.value}")


        }.addOnFailureListener {
            Log.d("GGG", "Error getting data", it)
        }
    }

    // Update the values displayed in the bottom sheet. Reset detector.
    private fun updateControlsUi() {
        fragmentCameraBinding.bottomSheetLayout.maxResultsValue.text =
            objectDetectorHelper.maxResults.toString()
        fragmentCameraBinding.bottomSheetLayout.thresholdValue.text =
            String.format("%.2f", objectDetectorHelper.threshold)
        fragmentCameraBinding.bottomSheetLayout.threadsValue.text =
            objectDetectorHelper.numThreads.toString()

        // Needs to be cleared instead of reinitialized because the GPU
        // delegate needs to be initialized on the thread using it when applicable
        objectDetectorHelper.clearObjectDetector()
        fragmentCameraBinding.overlay.clear()
    }

    // Initialize CameraX, and prepare to bind the camera use cases
    private fun setUpCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(
            {
                // CameraProvider
                cameraProvider = cameraProviderFuture.get()

                // Build and bind the camera use cases
                bindCameraUseCases()
            },
            ContextCompat.getMainExecutor(requireContext())
        )
    }

    // Declare and bind preview, capture and analysis use cases
    // @SuppressLint("UnsafeOptInUsageError") https://developer.android.com/reference/androidx/camera/core/ExperimentalGetImage
    @SuppressLint("UnsafeOptInUsageError")
    private fun bindCameraUseCases() {

        // CameraProvider
        val cameraProvider =
            cameraProvider ?: throw IllegalStateException("Camera initialization failed.")

        // CameraSelector - makes assumption that we're only using the back camera
        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        // Preview. Only using the 4:3 ratio because this is the closest to our models
        preview =
            Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(fragmentCameraBinding.viewFinder.display.rotation)
                .build()

        // ImageAnalysis. Using RGBA 8888 to match how our models work
        imageAnalyzer =
            ImageAnalysis.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(fragmentCameraBinding.viewFinder.display.rotation)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build()
                // The analyzer can then be assigned to the instance
                .also {
                    it.setAnalyzer(cameraExecutor) { image ->
                        if (!::bitmapBuffer.isInitialized) {
                            // The image rotation and RGB image buffer are initialized only once
                            // the analyzer has started running
                            bitmapBuffer = Bitmap.createBitmap(
                                image.width,
                                image.height,
                                Bitmap.Config.ARGB_8888
                            )
                        }

                        detectObjects(image)
                        image.close()
                    }
                }


        // Must unbind the use-cases before rebinding them
        cameraProvider.unbindAll()

        try {
            // A variable number of use-cases can be passed here -
            // camera provides access to CameraControl & CameraInfo
            camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalyzer)


            // Attach the viewfinder's surface provider to preview use case
            preview?.setSurfaceProvider(fragmentCameraBinding.viewFinder.surfaceProvider)
        } catch (exc: Exception) {
            Log.e(TAG, "Use case binding failed", exc)
        }
    }

    private fun detectObjects(image: ImageProxy) {
        // Copy out RGB bits to the shared bitmap buffer
        image.use { bitmapBuffer.copyPixelsFromBuffer(image.planes[0].buffer) }


        val imageRotation = image.imageInfo.rotationDegrees
        // Pass Bitmap and rotation to the object detector helper for processing and detection
        objectDetectorHelper.detect(bitmapBuffer, imageRotation)
        //prevent error："maxImages (4) has already been acquired, call #close before acquiring more."
        image.close()
    }

    // 其中一個設定發生變更時，MyActivity 就不會重新啟動，而是 MyActivity 會收到對 onConfigurationChanged() 的呼叫
    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.d("compose", "fragment onConfigurationChanged")
        super.onConfigurationChanged(newConfig)
        imageAnalyzer?.targetRotation = fragmentCameraBinding.viewFinder.display.rotation
    }

    // Update UI after objects have been detected. Extracts original image height/width
    // to scale and place bounding boxes properly through OverlayView
    override fun onResults(
        results: MutableList<Detection>?,
        inferenceTime: Long,
        imageHeight: Int,
        imageWidth: Int,
    ) {
       // Log.d("compose", "fragment onResults()")
        activity?.runOnUiThread {
           // fragmentCameraBinding.bottomSheetLayout.inferenceTimeVal.text =
             //   String.format("%d ms", inferenceTime)

            // Pass necessary information to OverlayView for drawing on the canvas
            // draw box!!!
            fragmentCameraBinding.overlay.setResults(
                results ?: LinkedList<Detection>(),
                imageHeight,
                imageWidth,
                Warningsound
            )

        }


        //found object
        when(findname){
            "床" -> findname ="bed"
            "椅子" -> findname = "chair"
            "杯子" -> findname = "cup"
            "電腦" -> findname = "laptop"
            "遙控器" -> findname = "remote"
            else -> null
        }


        if (results != null) {
            for (result in results) {
                if (result.categories[0].label == findname) {
                    foundsound?.start()
                    if(flage == true){
                        dovibrate()
                    }
                    break
                }
            }
        }

        //sound output
        if (results != null ) {
            for (result in results){
                for(ob in Objectsound){
                    //Toast.makeText(requireContext(),ob+"  "+result.categories[0].label, Toast.LENGTH_SHORT).show()
                    if(ob == result.categories[0].label){
                        sound_output(ob)
                       // Toast.makeText(requireContext(),ob+"  "+result.categories[0].label, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }



        // Force a redraw
        fragmentCameraBinding.overlay.invalidate()

    }


    private fun displaySpeechRecognizer() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please say something")
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-TW")
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
        activity?.startActivityForResult(intent, 0)
    }

    private fun displaySpeechRecognizer_second() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please say something")
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-TW")
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
        activity?.startActivityForResult(intent, 1)
    }



    fun sound_output( object_sound: String)
    {
        if (object_sound == "bed" ) {
            mp_bed?.start()
        } else if (object_sound == "chair" ) {
            mp_chair?.start()
        } else if (object_sound == "cup" ) {
            mp_cup?.start()
        } else if (object_sound == "laptop") {
            mp_laptop?.start()
        } else if (object_sound == "remote" ) {
            mp_remote?.start()
        }
    }

    private fun dovibrate() {
        val vibrator = getActivity()?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(50)
        }
    }

    override fun onError(error: String) {
        Log.d("compose", "fragment onError()")
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAttach(context: Context) {
        Log.d("compose", "fragment onAttach()")
        super.onAttach(context)
    }

    override fun onPause() {
        Log.d("compose", "fragment onPause()")
        super.onPause()
    }

    override fun onDestroy() {
        Log.d("compose", "fragment onDestroy()")

        super.onDestroy()
    }

    override fun onStop() {
        Log.d("compose", "fragment onStop()")
        super.onStop()
    }

    override fun onStart() {
        Log.d("compose", "fragment onStart()")
        super.onStart()
    }
}
