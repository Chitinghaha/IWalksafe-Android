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
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Looper
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.core.ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
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


    /** Blocking camera operations are performed using this executor */

    //https://ithelp.ithome.com.tw/articles/10207124
    private lateinit var cameraExecutor: ExecutorService

    //find_name
    private var findname: String? = null

    lateinit var Warning_sound:MutableSet<String>
    lateinit var selections:MutableSet<String>
    lateinit var MaxResult:String
    lateinit var Threshold:String
    lateinit var NumThreads:String
    lateinit var Delegate:String
    lateinit var Ml:String

    val ChToEn = mutableMapOf("椅子" to "chair", "床" to "bed", "電腦" to "laptop" ,"杯子" to "cup","遙控器" to "remote")


    //firebase
//    private lateinit var database: DatabaseReference

    //Share preference
    private lateinit var sp : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("compose", "fragment onCreate()")
        super.onCreate(savedInstanceState)

        sp = activity?.let { PreferenceManager.getDefaultSharedPreferences(it) }!!
        mp_bed = MediaPlayer.create(context, R.raw.bed)
        mp_chair = MediaPlayer.create(context, R.raw.chair)
        mp_cup = MediaPlayer.create(context, R.raw.cup)
        mp_laptop = MediaPlayer.create(context, R.raw.computer)
        mp_remote = MediaPlayer.create(context, R.raw.remote)
        beep1 = MediaPlayer.create(context, R.raw.beep1)
        beep2 = MediaPlayer.create(context, R.raw.beep2)
        beep3 = MediaPlayer.create(context, R.raw.beep4)
        foundsound = MediaPlayer.create(context, R.raw.foundsound)

//        database = FirebaseDatabase.getInstance().reference
    }
    private fun initSetting(){
        Warning_sound = sp.getStringSet("Wo", null) as MutableSet<String>
        selections = sp.getStringSet("Do", null) as MutableSet<String>
        MaxResult = sp.getString("Mr", "1").toString()
        Threshold = sp.getString("Ts", "0.7").toString()
        NumThreads = sp.getString("Nt", "1").toString()
        Delegate = sp.getString("Dl", "CPU").toString()
        Ml = sp.getString("Ml", "mobilenetv1.tflite").toString()
        objectDetectorHelper.maxResults = MaxResult.toInt()
        objectDetectorHelper.threshold = Threshold.toFloat()
        objectDetectorHelper.numThreads = NumThreads.toInt()
        initDelegate(Delegate)
        initModel(Ml)

        objectDetectorHelper.clearObjectDetector()

    }
    private  fun initDelegate(Delegate: String) {
        when(Delegate){
            "CPU" -> objectDetectorHelper.currentDelegate = 0
            "GPU" -> objectDetectorHelper.currentDelegate = 1
            "NNAPI" ->  objectDetectorHelper.currentDelegate=2
        }
    }
    private  fun initModel(Ml: String) {
        when(Ml){
            "mobilenetv1.tflite" -> objectDetectorHelper.currentModel = 0
            "efficientdet-lite0.tflite" -> objectDetectorHelper.currentModel = 1
            "efficientdet-lite1.tflite" ->  objectDetectorHelper.currentModel= 2
            "efficientdet-lite2.tflite" ->  objectDetectorHelper.currentModel= 3
        }
    }


    override fun onResume() {
        Log.d("compose", "fragment onResume()")
        super.onResume()

        // Make sure that all permissions are still present, since the
        // user could have removed them while the app was in paused state
        //確認fragment的權限
        if (!PermissionsFragment.hasPermissions(requireContext())) {

            Log.d("compose", "errorrrrrrrrr")
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

        //STT
        val data = arguments
        findname = data?.getString("String").toString()
        if(findname != "null")
         Toast.makeText(requireContext(), findname, Toast.LENGTH_SHORT).show()
        return fragmentCameraBinding.root
    }


    // onViewCreated() 適合初始化 view 的狀態、觀察 liveData 或在此設置 recycler 的 adapter, viewPager2
    // @SuppressLint("MissingPermission") 可以禁止權限檢查
    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("compose", "fragment onViewCreated()")
        super.onViewCreated(view, savedInstanceState)

        // Toast.makeText(getContext() , "Hello", Toast.LENGTH_LONG).show()

        objectDetectorHelper = ObjectDetectorHelper(
            context = requireContext(),
            objectDetectorListener = this
        )

        // Initialize our background executor
        cameraExecutor = Executors.newSingleThreadExecutor()

        // Wait for the views to be properly laid out
        fragmentCameraBinding.viewFinder.post {
            // Set up the camera and its use cases
            setUpCamera()
        }

        fragmentCameraBinding.cmSetting.setOnClickListener { goset() }
        fragmentCameraBinding.cmFind.setOnClickListener { gobd() }
        fragmentCameraBinding.cmDetect.setOnClickListener {  }

        // Attach listeners to UI control widgets
        initSetting()
    }
    private fun gobd(){
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, ObjectSettingFragment())
            ?.commit()
    }

    private  fun goset(){
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, SettingsFragment())
            ?.commit()

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
            )
        }


        //found object
        findname?.let { find_ob(results,it) }

        //sound output
        detectsound(results)



        // Force a redraw
        fragmentCameraBinding.overlay.invalidate()

    }

    private fun find_ob(results: MutableList<Detection>?,ob_name:String){
        if (results != null) {
            for(result in results) {
                if(result.categories[0].label == ChToEn[ob_name]) {
                  foundsound?.start()
                    break
                }
            }
        }
    }




    private fun detectsound(results: MutableList<Detection>?) {
        if (results != null) {
            for(result in results) {
                for (ob in selections) {

                    if(result.categories[0].label == ob) {
                        when (ob) {
                            "bed" -> mp_bed?.start()
                            "chair" -> mp_chair?.start()
                            "cup" -> mp_cup?.start()
                            "laptop" -> mp_laptop?.start()
                            "remote" -> mp_remote?.start()
                            else -> println("Other")
                        }
                    }
                }
            }
        }
    }


//    private fun dovibrate() {
//        val vibrator = getActivity()?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//        if (Build.VERSION.SDK_INT >= 26) {
//            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
//        } else {
//            vibrator.vibrate(50)
//        }
//    }

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

    private fun displaySpeechRecognizer_second() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please say something")
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-TW")
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
        activity?.startActivityForResult(intent, 0)
    }
}
