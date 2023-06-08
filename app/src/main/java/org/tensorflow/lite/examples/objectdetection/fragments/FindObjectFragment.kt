package org.tensorflow.lite.examples.objectdetection.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper
import org.tensorflow.lite.examples.objectdetection.R
import org.tensorflow.lite.examples.objectdetection.databinding.FragmentCameraBinding
import org.tensorflow.lite.examples.objectdetection.databinding.FragmentFindObjectBinding
import org.tensorflow.lite.task.vision.detector.Detection
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors



class FindObjectFragment :Fragment(), ObjectDetectorHelper.DetectorListener{
    private val TAG = "ObjectDetection"

    // ?：做 null check 後，不為空的話再執行   !!：堅持不會是空值，執行就是了
    private var _fragmentFindObjectBinding: FragmentFindObjectBinding? = null

    // getter,setter https://www.delftstack.com/zh-tw/howto/kotlin/kotlin-set/
    private val fragmentFindObjectBinding
        get() = _fragmentFindObjectBinding!!

    // lateinit進行「延遲初始化  https://carterchen247.medium.com/kotlin%E4%BD%BF%E7%94%A8%E5%BF%83%E5%BE%97-%E5%8D%81%E4%B8%80-lateinit-vs-lazy-1ef96bc5b3b3
    private lateinit var objectDetectorHelper: ObjectDetectorHelper

    //存取bmp
    private lateinit var bitmapBuffer: Bitmap

    private var preview: Preview? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null

    private var foundsound: MediaPlayer? = null


    //private var results: List<Detection> = LinkedList<Detection>()


    /** Blocking camera operations are performed using this executor */

    //https://ithelp.ithome.com.tw/articles/10207124
    private lateinit var cameraExecutor: ExecutorService

    //find_name
    private var findname: String? = null

    private lateinit var selections: MutableSet<String>
    private lateinit var MaxResult: String
    private lateinit var Threshold: String
    private lateinit var NumThreads: String
    private lateinit var Delegate: String
    private lateinit var Ml: String

//    private val args by navArgs<FindObjectFragmentArgs>()


    val DebounceMap = mutableMapOf<String, Int>()
    val filteredDebounceSet = mutableSetOf<String>()

    private var tts: TextToSpeech? = null

    private val args by navArgs<FindObjectFragmentArgs>()


    //firebase
//    private lateinit var database: DatabaseReference

    //Share preference
    private lateinit var sp: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("compose", "fragment onCreate()")
        super.onCreate(savedInstanceState)

        sp = activity?.let { PreferenceManager.getDefaultSharedPreferences(it) }!!
        foundsound = MediaPlayer.create(context, R.raw.foundsound)



//        database = FirebaseDatabase.getInstance().reference
    }

    private fun initSetting() {


        MaxResult = sp.getString("ob_Mr", "1").toString()
        Threshold = sp.getString("ob_Ts", "0.7").toString()
        NumThreads = sp.getString("ob_Nt", "1").toString()
        Delegate = sp.getString("ob_Dl", "CPU").toString()
        Ml = sp.getString("ob_Ml", "mobilenetv1.tflite").toString()
        objectDetectorHelper.maxResults = MaxResult.toInt()
        objectDetectorHelper.threshold = Threshold.toFloat()
        objectDetectorHelper.numThreads = NumThreads.toInt()
        initDelegate(Delegate)
        initModel(Ml)
        objectDetectorHelper.clearObjectDetector()

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

        _fragmentFindObjectBinding = FragmentFindObjectBinding.inflate(inflater, container, false)


        Toast.makeText(requireContext(), "onCreateView", Toast.LENGTH_SHORT).show()
        return fragmentFindObjectBinding.root
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
        fragmentFindObjectBinding.viewFinder.post {
            // Set up the camera and its use cases
            setUpCamera()
        }

        fragmentFindObjectBinding.cmSetting.setOnClickListener {
            view.findNavController().navigate(R.id.action_findObjectFragment_to_settingsForObjectFragment)
        }
        fragmentFindObjectBinding.cmFind.setOnClickListener {
            view.findNavController().navigate(R.id.action_findObjectFragment_to_Search)
        }
        fragmentFindObjectBinding.cmDetect.setOnClickListener {
            view.findNavController().navigate(R.id.action_findObjectFragment_to_detectFragment)
        }
        findname = args.str

        Toast.makeText(requireContext(), args.str, Toast.LENGTH_SHORT).show()



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
                .setTargetRotation(fragmentFindObjectBinding.viewFinder.display.rotation)
                .build()

        // ImageAnalysis. Using RGBA 8888 to match how our models work
        imageAnalyzer =
            ImageAnalysis.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(fragmentFindObjectBinding.viewFinder.display.rotation)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
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
            preview?.setSurfaceProvider(fragmentFindObjectBinding.viewFinder.surfaceProvider)
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
        imageAnalyzer?.targetRotation = fragmentFindObjectBinding.viewFinder.display.rotation
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
            fragmentFindObjectBinding.overlay.setResults(
                results ?: LinkedList<Detection>(),
                imageHeight,
                imageWidth,
            )
        }

        //found object
        findname?.let { find_ob(results, it) }



        // Force a redraw
        fragmentFindObjectBinding.overlay.invalidate()
    }


    private fun find_ob(results: MutableList<Detection>?, ob_name: String) {
        if (results != null) {
            for (result in results) {
                if (ChToEn[result.categories[0].label] == ob_name) {
                    foundsound?.start()
                    break
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
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
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

    private val ChToEn = mutableMapOf(
        "person" to "人",
        "bicycle" to "自行車",
        "car" to "汽車",
        "motorcycle" to "摩托車",
        "airplane" to "飛機",
        "bus" to "公車",
        "train" to "火車",
        "truck" to "卡車",
        "boat" to "船",
        "traffic light" to "紅綠燈",
        "fire hydrant" to "消防栓",
        "stop sign" to "停止標誌",
        "parking meter" to "停車計時器",
        "bench" to "長椅",
        "bird" to "鳥",
        "cat" to "貓",
        "dog" to "狗",
        "horse" to "馬",
        "sheep" to "綿羊",
        "cow" to "牛",
        "elephant" to "大象",
        "bear" to "熊",
        "zebra" to "斑馬",
        "giraffe" to "長頸鹿",
        "backpack" to "背包",
        "umbrella" to "雨傘",
        "handbag" to "手提包",
        "tie" to "領帶",
        "suitcase" to "手提箱",
        "frisbee" to "飛盤",
        "skis" to "滑雪板",
        "snowboard" to "滑雪板",
        "sports ball" to "運動球",
        "kite" to "風箏",
        "baseball bat" to "棒球棒",
        "baseball glove" to "棒球手套",
        "skateboard" to "滑板",
        "surfboard" to "衝浪板",
        "tennis racket" to "網球拍",
        "bottle" to "瓶子",
        "wine glass" to "酒杯",
        "cup" to "杯子",
        "fork" to "叉子",
        "knife" to "刀",
        "spoon" to "匙",
        "bowl" to "碗",
        "banana" to "香蕉",
        "apple" to "蘋果",
        "sandwich" to "三明治",
        "orange" to "柳橙",
        "broccoli" to "西蘭花",
        "carrot" to "胡蘿蔔",
        "hot dog" to "熱狗",
        "pizza" to "比薩餅",
        "donut" to "甜甜圈",
        "cake" to "蛋糕",
        "chair" to "椅子",
        "couch" to "沙發",
        "potted plant" to "盆栽植物",
        "bed" to "床",
        "dining table" to "餐桌",
        "toilet" to "馬桶",
        "tv" to "電視",
        "laptop" to "筆記型電腦",
        "mouse" to "鼠標",
        "remote" to "遙控器",
        "keyboard" to "鍵盤",
        "cell phone" to "手機",
        "microwave" to "微波爐",
        "oven" to "烤箱",
        "toaster" to "烤麵包機",
        "sink" to "水槽",
        "refrigerator" to "冰箱",
        "book" to "書",
        "clock" to "時鐘",
        "vase" to "花瓶",
        "scissors" to "剪刀",
        "teddy bear" to "泰迪熊",
        "hair drier" to "吹風機",
        "toothbrush" to "牙刷"
    )


}