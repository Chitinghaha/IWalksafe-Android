package org.tensorflow.lite.examples.objectdetection.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u00b6\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0010#\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\b\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J&\u00109\u001a\u00020:2\u000e\u0010;\u001a\n\u0012\u0004\u0012\u00020=\u0018\u00010<2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00060\u001bH\u0002J\b\u0010>\u001a\u00020:H\u0003J\u0010\u0010?\u001a\u00020:2\u0006\u0010@\u001a\u00020AH\u0002J\u0010\u0010B\u001a\u00020:2\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J\u0010\u0010C\u001a\u00020:2\u0006\u0010\u0010\u001a\u00020\u0006H\u0002J\b\u0010D\u001a\u00020:H\u0002J\u0010\u0010E\u001a\u00020:2\u0006\u0010F\u001a\u00020GH\u0016J\u0010\u0010H\u001a\u00020:2\u0006\u0010I\u001a\u00020JH\u0016J\u0012\u0010K\u001a\u00020:2\b\u0010L\u001a\u0004\u0018\u00010MH\u0016J$\u0010N\u001a\u00020O2\u0006\u0010P\u001a\u00020Q2\b\u0010R\u001a\u0004\u0018\u00010S2\b\u0010L\u001a\u0004\u0018\u00010MH\u0016J\b\u0010T\u001a\u00020:H\u0016J\b\u0010U\u001a\u00020:H\u0016J\u0010\u0010V\u001a\u00020:2\u0006\u0010W\u001a\u00020\u0006H\u0016J\b\u0010X\u001a\u00020:H\u0016J0\u0010Y\u001a\u00020:2\u000e\u0010;\u001a\n\u0012\u0004\u0012\u00020=\u0018\u00010<2\u0006\u0010Z\u001a\u00020[2\u0006\u0010\\\u001a\u00020]2\u0006\u0010^\u001a\u00020]H\u0016J\b\u0010_\u001a\u00020:H\u0016J\b\u0010`\u001a\u00020:H\u0016J\b\u0010a\u001a\u00020:H\u0016J\u001a\u0010b\u001a\u00020:2\u0006\u0010c\u001a\u00020O2\b\u0010L\u001a\u0004\u0018\u00010MH\u0017J\b\u0010d\u001a\u00020:H\u0002R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\u0006X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u00020\u0006X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\t\"\u0004\b\u000f\u0010\u000bR\u001a\u0010\u0010\u001a\u00020\u0006X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\t\"\u0004\b\u0012\u0010\u000bR\u001a\u0010\u0013\u001a\u00020\u0006X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\t\"\u0004\b\u0015\u0010\u000bR\u000e\u0010\u0016\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0006X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\t\"\u0004\b\u0019\u0010\u000bR \u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00060\u001bX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u0004\u0018\u00010#X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010#X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\'X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010(\u001a\u0004\u0018\u00010)X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020+X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010,\u001a\u0004\u0018\u00010-X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010.\u001a\u00020!8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b/\u00100R\u0010\u00101\u001a\u0004\u0018\u000102X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00103\u001a\u000204X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u00105\u001a\u0004\u0018\u000106X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00107\u001a\u000208X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006e"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/fragments/WalkModeFragment;", "Landroidx/fragment/app/Fragment;", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper$DetectorListener;", "()V", "CHToEn", "", "", "Delegate", "getDelegate", "()Ljava/lang/String;", "setDelegate", "(Ljava/lang/String;)V", "EnToCh", "MaxResult", "getMaxResult", "setMaxResult", "Ml", "getMl", "setMl", "NumThreads", "getNumThreads", "setNumThreads", "TAG", "Threshold", "getThreshold", "setThreshold", "Warning_sound", "", "getWarning_sound", "()Ljava/util/Set;", "setWarning_sound", "(Ljava/util/Set;)V", "_fragmentWMBinding", "Lorg/tensorflow/lite/examples/objectdetection/databinding/FragmentWalkModeBinding;", "beep1", "Landroid/media/MediaPlayer;", "beep2", "beep3", "bitmapBuffer", "Landroid/graphics/Bitmap;", "camera", "Landroidx/camera/core/Camera;", "cameraExecutor", "Ljava/util/concurrent/ExecutorService;", "cameraProvider", "Landroidx/camera/lifecycle/ProcessCameraProvider;", "fragmentWalkModeBinding", "getFragmentWalkModeBinding", "()Lorg/tensorflow/lite/examples/objectdetection/databinding/FragmentWalkModeBinding;", "imageAnalyzer", "Landroidx/camera/core/ImageAnalysis;", "objectDetectorHelper", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper;", "preview", "Landroidx/camera/core/Preview;", "sp", "Landroid/content/SharedPreferences;", "DetectBoundingBox", "", "results", "", "Lorg/tensorflow/lite/task/vision/detector/Detection;", "bindCameraUseCases", "detectObjects", "image", "Landroidx/camera/core/ImageProxy;", "initDelegate", "initModel", "initSetting", "onAttach", "context", "Landroid/content/Context;", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroy", "onDestroyView", "onError", "error", "onPause", "onResults", "inferenceTime", "", "imageHeight", "", "imageWidth", "onResume", "onStart", "onStop", "onViewCreated", "view", "setUpCamera", "app_debug"})
public final class WalkModeFragment extends androidx.fragment.app.Fragment implements org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper.DetectorListener {
    private final java.lang.String TAG = "ObjectDetection";
    private org.tensorflow.lite.examples.objectdetection.databinding.FragmentWalkModeBinding _fragmentWMBinding;
    private org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper objectDetectorHelper;
    private android.graphics.Bitmap bitmapBuffer;
    private androidx.camera.core.Preview preview;
    private androidx.camera.core.ImageAnalysis imageAnalyzer;
    private androidx.camera.core.Camera camera;
    private androidx.camera.lifecycle.ProcessCameraProvider cameraProvider;
    private android.media.MediaPlayer beep1;
    private android.media.MediaPlayer beep2;
    private android.media.MediaPlayer beep3;
    
    /**
     * Blocking camera operations are performed using this executor
     */
    private java.util.concurrent.ExecutorService cameraExecutor;
    public java.util.Set<java.lang.String> Warning_sound;
    public java.lang.String MaxResult;
    public java.lang.String Threshold;
    public java.lang.String NumThreads;
    public java.lang.String Delegate;
    public java.lang.String Ml;
    private final java.util.Map<java.lang.String, java.lang.String> CHToEn = null;
    private final java.util.Map<java.lang.String, java.lang.String> EnToCh = null;
    private android.content.SharedPreferences sp;
    
    public WalkModeFragment() {
        super();
    }
    
    private final org.tensorflow.lite.examples.objectdetection.databinding.FragmentWalkModeBinding getFragmentWalkModeBinding() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> getWarning_sound() {
        return null;
    }
    
    public final void setWarning_sound(@org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMaxResult() {
        return null;
    }
    
    public final void setMaxResult(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getThreshold() {
        return null;
    }
    
    public final void setThreshold(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNumThreads() {
        return null;
    }
    
    public final void setNumThreads(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDelegate() {
        return null;
    }
    
    public final void setDelegate(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMl() {
        return null;
    }
    
    public final void setMl(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initSetting() {
    }
    
    private final void initDelegate(java.lang.String Delegate) {
    }
    
    private final void initModel(java.lang.String Ml) {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setUpCamera() {
    }
    
    @android.annotation.SuppressLint(value = {"UnsafeOptInUsageError"})
    private final void bindCameraUseCases() {
    }
    
    private final void detectObjects(androidx.camera.core.ImageProxy image) {
    }
    
    @java.lang.Override()
    public void onConfigurationChanged(@org.jetbrains.annotations.NotNull()
    android.content.res.Configuration newConfig) {
    }
    
    @java.lang.Override()
    public void onResults(@org.jetbrains.annotations.Nullable()
    java.util.List<org.tensorflow.lite.task.vision.detector.Detection> results, long inferenceTime, int imageHeight, int imageWidth) {
    }
    
    private final void DetectBoundingBox(java.util.List<org.tensorflow.lite.task.vision.detector.Detection> results, java.util.Set<java.lang.String> Warning_sound) {
    }
    
    @java.lang.Override()
    public void onError(@org.jetbrains.annotations.NotNull()
    java.lang.String error) {
    }
    
    @java.lang.Override()
    public void onAttach(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @java.lang.Override()
    public void onStop() {
    }
    
    @java.lang.Override()
    public void onStart() {
    }
}