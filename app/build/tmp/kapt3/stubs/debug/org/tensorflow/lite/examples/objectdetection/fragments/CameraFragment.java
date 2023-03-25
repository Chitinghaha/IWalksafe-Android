package org.tensorflow.lite.examples.objectdetection.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u00bc\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0015\n\u0002\u0010#\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\b\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010D\u001a\u00020EH\u0003J\u0010\u0010F\u001a\u00020E2\u0006\u0010G\u001a\u00020HH\u0002J\u0018\u0010I\u001a\u00020E2\u000e\u0010J\u001a\n\u0012\u0004\u0012\u00020L\u0018\u00010KH\u0002J\b\u0010M\u001a\u00020EH\u0002J \u0010N\u001a\u00020E2\u000e\u0010J\u001a\n\u0012\u0004\u0012\u00020L\u0018\u00010K2\u0006\u0010O\u001a\u00020\u0006H\u0002J\b\u0010P\u001a\u00020EH\u0002J\b\u0010Q\u001a\u00020EH\u0002J\u0010\u0010R\u001a\u00020E2\u0006\u0010\t\u001a\u00020\u0006H\u0002J\u0010\u0010S\u001a\u00020E2\u0006\u0010\u0011\u001a\u00020\u0006H\u0002J\b\u0010T\u001a\u00020EH\u0002J\u0010\u0010U\u001a\u00020E2\u0006\u0010V\u001a\u00020WH\u0016J\u0010\u0010X\u001a\u00020E2\u0006\u0010Y\u001a\u00020ZH\u0016J\u0012\u0010[\u001a\u00020E2\b\u0010\\\u001a\u0004\u0018\u00010]H\u0016J$\u0010^\u001a\u00020_2\u0006\u0010`\u001a\u00020a2\b\u0010b\u001a\u0004\u0018\u00010c2\b\u0010\\\u001a\u0004\u0018\u00010]H\u0016J\b\u0010d\u001a\u00020EH\u0016J\b\u0010e\u001a\u00020EH\u0016J\u0010\u0010f\u001a\u00020E2\u0006\u0010g\u001a\u00020\u0006H\u0016J\b\u0010h\u001a\u00020EH\u0016J0\u0010i\u001a\u00020E2\u000e\u0010J\u001a\n\u0012\u0004\u0012\u00020L\u0018\u00010K2\u0006\u0010j\u001a\u00020k2\u0006\u0010l\u001a\u00020m2\u0006\u0010n\u001a\u00020mH\u0016J\b\u0010o\u001a\u00020EH\u0016J\b\u0010p\u001a\u00020EH\u0016J\b\u0010q\u001a\u00020EH\u0016J\u001a\u0010r\u001a\u00020E2\u0006\u0010s\u001a\u00020_2\b\u0010\\\u001a\u0004\u0018\u00010]H\u0017J\b\u0010t\u001a\u00020EH\u0002R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0006X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u0006X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\rR\u001a\u0010\u0011\u001a\u00020\u0006X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000b\"\u0004\b\u0013\u0010\rR\u001a\u0010\u0014\u001a\u00020\u0006X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u000b\"\u0004\b\u0016\u0010\rR\u000e\u0010\u0017\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u00020\u0006X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u000b\"\u0004\b\u001a\u0010\rR \u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\u001cX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010$X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010$X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010$X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020(X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u0004\u0018\u00010*X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u0004\u0018\u00010.X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010/\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00100\u001a\u0004\u0018\u00010$X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u00101\u001a\u00020\"8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b2\u00103R\u0010\u00104\u001a\u0004\u0018\u000105X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00106\u001a\u0004\u0018\u00010$X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00107\u001a\u0004\u0018\u00010$X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00108\u001a\u0004\u0018\u00010$X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00109\u001a\u0004\u0018\u00010$X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010:\u001a\u0004\u0018\u00010$X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020<X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010=\u001a\u0004\u0018\u00010>X\u0082\u000e\u00a2\u0006\u0002\n\u0000R \u0010?\u001a\b\u0012\u0004\u0012\u00020\u00060\u001cX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b@\u0010\u001e\"\u0004\bA\u0010 R\u000e\u0010B\u001a\u00020CX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006u"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/fragments/CameraFragment;", "Landroidx/fragment/app/Fragment;", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper$DetectorListener;", "()V", "ChToEn", "", "", "getChToEn", "()Ljava/util/Map;", "Delegate", "getDelegate", "()Ljava/lang/String;", "setDelegate", "(Ljava/lang/String;)V", "MaxResult", "getMaxResult", "setMaxResult", "Ml", "getMl", "setMl", "NumThreads", "getNumThreads", "setNumThreads", "TAG", "Threshold", "getThreshold", "setThreshold", "Warning_sound", "", "getWarning_sound", "()Ljava/util/Set;", "setWarning_sound", "(Ljava/util/Set;)V", "_fragmentCameraBinding", "Lorg/tensorflow/lite/examples/objectdetection/databinding/FragmentCameraBinding;", "beep1", "Landroid/media/MediaPlayer;", "beep2", "beep3", "bitmapBuffer", "Landroid/graphics/Bitmap;", "camera", "Landroidx/camera/core/Camera;", "cameraExecutor", "Ljava/util/concurrent/ExecutorService;", "cameraProvider", "Landroidx/camera/lifecycle/ProcessCameraProvider;", "findname", "foundsound", "fragmentCameraBinding", "getFragmentCameraBinding", "()Lorg/tensorflow/lite/examples/objectdetection/databinding/FragmentCameraBinding;", "imageAnalyzer", "Landroidx/camera/core/ImageAnalysis;", "mp_bed", "mp_chair", "mp_cup", "mp_laptop", "mp_remote", "objectDetectorHelper", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper;", "preview", "Landroidx/camera/core/Preview;", "selections", "getSelections", "setSelections", "sp", "Landroid/content/SharedPreferences;", "bindCameraUseCases", "", "detectObjects", "image", "Landroidx/camera/core/ImageProxy;", "detectsound", "results", "", "Lorg/tensorflow/lite/task/vision/detector/Detection;", "displaySpeechRecognizer_second", "find_ob", "ob_name", "gobd", "goset", "initDelegate", "initModel", "initSetting", "onAttach", "context", "Landroid/content/Context;", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroy", "onDestroyView", "onError", "error", "onPause", "onResults", "inferenceTime", "", "imageHeight", "", "imageWidth", "onResume", "onStart", "onStop", "onViewCreated", "view", "setUpCamera", "app_debug"})
public final class CameraFragment extends androidx.fragment.app.Fragment implements org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper.DetectorListener {
    private final java.lang.String TAG = "ObjectDetection";
    private org.tensorflow.lite.examples.objectdetection.databinding.FragmentCameraBinding _fragmentCameraBinding;
    private org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper objectDetectorHelper;
    private android.graphics.Bitmap bitmapBuffer;
    private androidx.camera.core.Preview preview;
    private androidx.camera.core.ImageAnalysis imageAnalyzer;
    private androidx.camera.core.Camera camera;
    private androidx.camera.lifecycle.ProcessCameraProvider cameraProvider;
    private android.media.MediaPlayer mp_bed;
    private android.media.MediaPlayer mp_chair;
    private android.media.MediaPlayer mp_cup;
    private android.media.MediaPlayer mp_laptop;
    private android.media.MediaPlayer mp_remote;
    private android.media.MediaPlayer foundsound;
    private android.media.MediaPlayer beep1;
    private android.media.MediaPlayer beep2;
    private android.media.MediaPlayer beep3;
    
    /**
     * Blocking camera operations are performed using this executor
     */
    private java.util.concurrent.ExecutorService cameraExecutor;
    private java.lang.String findname;
    public java.util.Set<java.lang.String> Warning_sound;
    public java.util.Set<java.lang.String> selections;
    public java.lang.String MaxResult;
    public java.lang.String Threshold;
    public java.lang.String NumThreads;
    public java.lang.String Delegate;
    public java.lang.String Ml;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.String> ChToEn = null;
    private android.content.SharedPreferences sp;
    
    public CameraFragment() {
        super();
    }
    
    private final org.tensorflow.lite.examples.objectdetection.databinding.FragmentCameraBinding getFragmentCameraBinding() {
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
    public final java.util.Set<java.lang.String> getSelections() {
        return null;
    }
    
    public final void setSelections(@org.jetbrains.annotations.NotNull()
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
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.String> getChToEn() {
        return null;
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
    
    private final void gobd() {
    }
    
    private final void goset() {
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
    
    private final void find_ob(java.util.List<org.tensorflow.lite.task.vision.detector.Detection> results, java.lang.String ob_name) {
    }
    
    private final void detectsound(java.util.List<org.tensorflow.lite.task.vision.detector.Detection> results) {
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
    
    private final void displaySpeechRecognizer_second() {
    }
}