package org.tensorflow.lite.examples.objectdetection.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech

import android.util.Log

import android.widget.AdapterView
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.core.ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper
import org.tensorflow.lite.examples.objectdetection.R
import org.tensorflow.lite.examples.objectdetection.databinding.FragmentCameraBinding
import org.tensorflow.lite.examples.objectdetection.databinding.FragmentWalkModeBinding
import org.tensorflow.lite.task.vision.detector.Detection
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.properties.Delegates


class WalkModeFragment : Fragment(R.layout.fragment_walk_mode),
    ObjectDetectorHelper.DetectorListener,TextToSpeech.OnInitListener {

    private val TAG = "ObjectDetection"

    // ?：做 null check 後，不為空的話再執行   !!：堅持不會是空值，執行就是了
    private var _fragmentWMBinding: FragmentWalkModeBinding? = null

    // getter,setter https://www.delftstack.com/zh-tw/howto/kotlin/kotlin-set/
    private val fragmentWalkModeBinding
        get() = _fragmentWMBinding!!

    // lateinit進行「延遲初始化  https://carterchen247.medium.com/kotlin%E4%BD%BF%E7%94%A8%E5%BF%83%E5%BE%97-%E5%8D%81%E4%B8%80-lateinit-vs-lazy-1ef96bc5b3b3
    private lateinit var objectDetectorHelper: ObjectDetectorHelper

    //存取bmp
    private lateinit var bitmapBuffer: Bitmap

    private var preview: Preview? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null


    private var beep1: MediaPlayer? = null
    private var beep2: MediaPlayer? = null
    private var beep3: MediaPlayer? = null

    private var tts: TextToSpeech? = null

    //private var results: List<Detection> = LinkedList<Detection>()


    /** Blocking camera operations are performed using this executor */

    //https://ithelp.ithome.com.tw/articles/10207124
    private lateinit var cameraExecutor: ExecutorService


    private lateinit var Warning_sound: MutableSet<String>
    private lateinit var Static_sound: MutableSet<String>
    private lateinit var All_sound: MutableSet<String>
    val DebounceMap = mutableMapOf<String, Int>()
    val DebounceMap_D = mutableMapOf<String, Int>()
    private lateinit var MaxResult: String
    private lateinit var Distance: String
    private var Dis by Delegates.notNull<Int>()
    private lateinit var Threshold: String
    private lateinit var NumThreads: String
    lateinit var Delegate: String
    private lateinit var Ml: String
    private lateinit var Ws: String


    private val EnToCh = mutableMapOf(
        "person" to "人",
        "cat" to "貓",
        "dog" to "狗",
        "bicycle" to "腳踏車",
        "motorcycle" to "摩托車",
        "car" to "汽車",
        "bus" to "公車",
        "traffic light" to "紅綠燈",
        "stop sign" to "暫停標誌",
        "bench" to "長椅",
    )

    //firebase
//    private lateinit var database: DatabaseReference

    //Share preference
    private lateinit var sp: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("compose", "fragment onCreate()")
        super.onCreate(savedInstanceState)

        sp = activity?.let { PreferenceManager.getDefaultSharedPreferences(it) }!!
        beep1 = MediaPlayer.create(context, R.raw.beep1)
        beep2 = MediaPlayer.create(context, R.raw.beep2)
        beep3 = MediaPlayer.create(context, R.raw.beep4)

        tts = TextToSpeech(context, this)

//        database = FirebaseDatabase.getInstance().reference
    }

    private fun initSetting() {
        Warning_sound = sp.getStringSet("www", null) as MutableSet<String>
        Static_sound = sp.getStringSet("wdo", mutableSetOf("")) as MutableSet<String>
        InintDebounceMap(Static_sound,Warning_sound)
        Distance =  sp.getString("wd", "100000").toString()
        Dis = Distance.toInt()
        Ws =  sp.getString("ws", "嗶嗶聲").toString()
        MaxResult = sp.getString("wm_Mr", "1").toString()
        Threshold = sp.getString("wm_Ts", "0.7").toString()
        NumThreads = sp.getString("wm_Nt", "1").toString()
        Delegate = sp.getString("wm_Dl", "CPU").toString()
        Ml = sp.getString("wm_Ml", "mobilenetv1.tflite").toString()
        objectDetectorHelper.maxResults = MaxResult.toInt()
        objectDetectorHelper.threshold = Threshold.toFloat()
        objectDetectorHelper.numThreads = NumThreads.toInt()
        initDelegate(Delegate)
        initModel(Ml)
        objectDetectorHelper.clearObjectDetector()

    }
    private fun InintDebounceMap(set: MutableSet<String>,set2: MutableSet<String>) {
        for (name in set) {
            DebounceMap[name] = 0 // 初始值
        }
        for (name in set2) {
            DebounceMap_D[name] = 0 // 初始值
        }
        Log.d("compose", set.toString())
        Log.d("compose", set2.toString())
    }

    private fun initDelegate(Delegate: String) {
        when (Delegate) {
            "CPU" -> objectDetectorHelper.currentDelegate = 0
            "GPU" -> objectDetectorHelper.currentDelegate = 1
            "NNAPI" -> objectDetectorHelper.currentDelegate = 2
        }
    }

    private fun initModel(Ml: String) {
        when (Ml) {
            "mobilenetv1.tflite" -> objectDetectorHelper.currentModel = 0
            "efficientdet-lite0.tflite" -> objectDetectorHelper.currentModel = 1
            "efficientdet-lite1.tflite" -> objectDetectorHelper.currentModel = 2
            "efficientdet-lite2.tflite" -> objectDetectorHelper.currentModel = 3
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

        _fragmentWMBinding = FragmentWalkModeBinding.inflate(inflater, container, false)

        //STT

        return fragmentWalkModeBinding.root
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
        fragmentWalkModeBinding.viewFinder.post {
            // Set up the camera and its use cases
            setUpCamera()
        }

        fragmentWalkModeBinding.wmCompass.setOnClickListener {
            view.findNavController().navigate(R.id.action_WalkModel_to_compassFragment)
        }
        fragmentWalkModeBinding.wmNavigation.setOnClickListener {
            view.findNavController().navigate(R.id.action_WalkModel_to_navigationSettingFragment)
        }
        fragmentWalkModeBinding.wmSetting.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_WalkModel_to_settingsForWalkModeFragment)
        }

        // Attach listeners to UI control widgets
        initSetting()

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
                .setTargetRotation(fragmentWalkModeBinding.viewFinder.display.rotation)
                .build()

        // ImageAnalysis. Using RGBA 8888 to match how our models work
        imageAnalyzer =
            ImageAnalysis.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(fragmentWalkModeBinding.viewFinder.display.rotation)
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
            preview?.setSurfaceProvider(fragmentWalkModeBinding.viewFinder.surfaceProvider)
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
        imageAnalyzer?.targetRotation = fragmentWalkModeBinding.viewFinder.display.rotation
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
            fragmentWalkModeBinding.overlay.setResults(
                results ?: LinkedList<Detection>(),
                imageHeight,
                imageWidth,
            )
        }


        // Force a redraw
        fragmentWalkModeBinding.overlay.invalidate()
        //bounding box

        if(Ws == "嗶嗶聲") {
            DetectBoundingBox_Beep(results, Warning_sound)
            Debounce_Beep(results)

        }
        else if(Ws == "物件名稱"){
            DetectBoundingBox(results, Warning_sound)
            Debounce(results)
        }
        else
        {
            DetectBoundingBox_Beep(results, Warning_sound)
            Debounce_Beep(results)
        }

    }

    private fun Debounce(results: MutableList<Detection>?) {
        if (results != null) {
            for (result in results) {
                if (DebounceMap.containsKey(EnToCh[result.categories[0].label])) {
                    if (DebounceMap[result.categories[0].label] == null)
                        DebounceMap[result.categories[0].label]= 0
                    Log.d("compose", result.categories[0].label)
                    DebounceMap[result.categories[0].label] =
                        DebounceMap[result.categories[0].label]!! + 1
                    if (DebounceMap[result.categories[0].label]!! > 6) {
                        DebounceMap[result.categories[0].label] = 0
//                        beep2?.start()
                        EnToCh[result.categories[0].label]?.let { speakOut(it) }
                    }
                }
            }
        }
    }


    private fun DetectBoundingBox(
        results: MutableList<Detection>?,
        Warning_sound: MutableSet<String>
    ) {
        val scaleFactor = 1f
        if (results != null) {
            for (result in results) {
                EnToCh[result.categories[0].label]?.let { Log.d("compose", it) }
                if (Warning_sound.contains(EnToCh[result.categories[0].label])) {
                    Log.d("compose", EnToCh[result.categories[0].label].toString())
                    val boundingBox = result.boundingBox
                    val top = boundingBox.top * scaleFactor
                    val bottom = boundingBox.bottom * scaleFactor
                    val left = boundingBox.left * scaleFactor
                    val right = boundingBox.right * scaleFactor

                    val area = (left - right) * (top - bottom)
                    if (area >= Dis) {

                        if (DebounceMap_D.containsKey(EnToCh[result.categories[0].label])) {
                            if (DebounceMap_D[result.categories[0].label] == null)
                                DebounceMap_D[result.categories[0].label]= 0
                            Log.d("compose", result.categories[0].label)
                            DebounceMap_D[result.categories[0].label] =
                            DebounceMap_D[result.categories[0].label]!! + 1
                            if (DebounceMap_D[result.categories[0].label]!! > 4) {
                                DebounceMap_D[result.categories[0].label] = 0
                                EnToCh[result.categories[0].label]?.let { speakOut(it) }
                            }
                        }
                    }
                }
            }
        }
    }



    private fun Debounce_Beep(results: MutableList<Detection>?) {
        if (results != null) {
            for (result in results) {
                if (DebounceMap.containsKey(EnToCh[result.categories[0].label])) {
                    if (DebounceMap[result.categories[0].label] == null)
                        DebounceMap[result.categories[0].label]= 0
                    Log.d("compose", result.categories[0].label)
                    DebounceMap[result.categories[0].label] =
                        DebounceMap[result.categories[0].label]!! + 1
                    if (DebounceMap[result.categories[0].label]!! > 6) {
                        DebounceMap[result.categories[0].label] = 0
                        beep2?.start()
//                        EnToCh[result.categories[0].label]?.let { speakOut(it) }
                    }
                }
            }
        }
    }
    private fun DetectBoundingBox_Beep(
        results: MutableList<Detection>?,
        Warning_sound: MutableSet<String>
    ) {
        val scaleFactor = 1f
        if (results != null) {
            for (result in results) {
                result.categories[0].label?.let { Log.d("compose", it) }
                if (Warning_sound.contains(EnToCh[result.categories[0].label])) {
                    Log.d("compose", EnToCh[result.categories[0].label].toString())
                    val boundingBox = result.boundingBox
                    val top = boundingBox.top * scaleFactor
                    val bottom = boundingBox.bottom * scaleFactor
                    val left = boundingBox.left * scaleFactor
                    val right = boundingBox.right * scaleFactor

                    val area = (left - right) * (top - bottom)
                    if (area >= Dis*2) {
                        beep1?.start()
                    } else if (area >= Dis*1.5) {
                        beep2?.start()
                    } else if (area >= Dis) {
                        beep3?.start()
                    }
                }

            }
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
    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.TAIWAN)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language not supported!")
            } else {
                Log.e("TTS", "error")
            }
        }
    }
}
