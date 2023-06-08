package org.tensorflow.lite.examples.objectdetection.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u00bc\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u000b\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010A\u001a\u00020B2\u000e\u0010C\u001a\n\u0012\u0004\u0012\u00020E\u0018\u00010DH\u0002J\u0018\u0010F\u001a\u00020B2\u000e\u0010C\u001a\n\u0012\u0004\u0012\u00020E\u0018\u00010DH\u0002J&\u0010G\u001a\u00020B2\u000e\u0010C\u001a\n\u0012\u0004\u0012\u00020E\u0018\u00010D2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J&\u0010H\u001a\u00020B2\u000e\u0010C\u001a\n\u0012\u0004\u0012\u00020E\u0018\u00010D2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J$\u0010I\u001a\u00020B2\f\u0010J\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J\b\u0010L\u001a\u00020BH\u0003J\u0010\u0010M\u001a\u00020B2\u0006\u0010N\u001a\u00020OH\u0002J\u0010\u0010P\u001a\u00020B2\u0006\u0010\u000f\u001a\u00020\u0007H\u0002J\u0010\u0010Q\u001a\u00020B2\u0006\u0010\u001f\u001a\u00020\u0007H\u0002J\b\u0010R\u001a\u00020BH\u0002J\u0010\u0010S\u001a\u00020B2\u0006\u0010T\u001a\u00020UH\u0016J\u0010\u0010V\u001a\u00020B2\u0006\u0010W\u001a\u00020XH\u0016J\u0012\u0010Y\u001a\u00020B2\b\u0010Z\u001a\u0004\u0018\u00010[H\u0016J$\u0010\\\u001a\u00020]2\u0006\u0010^\u001a\u00020_2\b\u0010`\u001a\u0004\u0018\u00010a2\b\u0010Z\u001a\u0004\u0018\u00010[H\u0016J\b\u0010b\u001a\u00020BH\u0016J\b\u0010c\u001a\u00020BH\u0016J\u0010\u0010d\u001a\u00020B2\u0006\u0010e\u001a\u00020\u0007H\u0016J\u0010\u0010f\u001a\u00020B2\u0006\u0010g\u001a\u00020\nH\u0016J\b\u0010h\u001a\u00020BH\u0016J0\u0010i\u001a\u00020B2\u000e\u0010C\u001a\n\u0012\u0004\u0012\u00020E\u0018\u00010D2\u0006\u0010j\u001a\u00020k2\u0006\u0010l\u001a\u00020\n2\u0006\u0010m\u001a\u00020\nH\u0016J\b\u0010n\u001a\u00020BH\u0016J\b\u0010o\u001a\u00020BH\u0016J\b\u0010p\u001a\u00020BH\u0016J\u001a\u0010q\u001a\u00020B2\u0006\u0010r\u001a\u00020]2\b\u0010Z\u001a\u0004\u0018\u00010[H\u0017J\b\u0010s\u001a\u00020BH\u0002J\u0010\u0010t\u001a\u00020B2\u0006\u0010u\u001a\u00020\u0007H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u001a\u0010\u000f\u001a\u00020\u0007X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R+\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\n8B@BX\u0082\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001c\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0007X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010\'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010(\u001a\u0004\u0018\u00010)X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010*\u001a\u0004\u0018\u00010)X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010+\u001a\u0004\u0018\u00010)X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020-X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010.\u001a\u0004\u0018\u00010/X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00100\u001a\u000201X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u00102\u001a\u0004\u0018\u000103X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u00104\u001a\u00020\'8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b5\u00106R\u0010\u00107\u001a\u0004\u0018\u000108X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020:X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010;\u001a\u0004\u0018\u00010<X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020>X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010?\u001a\u0004\u0018\u00010@X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006v"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/fragments/WalkModeFragment;", "Landroidx/fragment/app/Fragment;", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper$DetectorListener;", "Landroid/speech/tts/TextToSpeech$OnInitListener;", "()V", "All_sound", "", "", "DebounceMap", "", "", "getDebounceMap", "()Ljava/util/Map;", "DebounceMap_D", "getDebounceMap_D", "Delegate", "getDelegate", "()Ljava/lang/String;", "setDelegate", "(Ljava/lang/String;)V", "<set-?>", "Dis", "getDis", "()I", "setDis", "(I)V", "Dis$delegate", "Lkotlin/properties/ReadWriteProperty;", "Distance", "EnToCh", "MaxResult", "Ml", "NumThreads", "Static_sound", "TAG", "Threshold", "Warning_sound", "Ws", "_fragmentWMBinding", "Lorg/tensorflow/lite/examples/objectdetection/databinding/FragmentWalkModeBinding;", "beep1", "Landroid/media/MediaPlayer;", "beep2", "beep3", "bitmapBuffer", "Landroid/graphics/Bitmap;", "camera", "Landroidx/camera/core/Camera;", "cameraExecutor", "Ljava/util/concurrent/ExecutorService;", "cameraProvider", "Landroidx/camera/lifecycle/ProcessCameraProvider;", "fragmentWalkModeBinding", "getFragmentWalkModeBinding", "()Lorg/tensorflow/lite/examples/objectdetection/databinding/FragmentWalkModeBinding;", "imageAnalyzer", "Landroidx/camera/core/ImageAnalysis;", "objectDetectorHelper", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper;", "preview", "Landroidx/camera/core/Preview;", "sp", "Landroid/content/SharedPreferences;", "tts", "Landroid/speech/tts/TextToSpeech;", "Debounce", "", "results", "", "Lorg/tensorflow/lite/task/vision/detector/Detection;", "Debounce_Beep", "DetectBoundingBox", "DetectBoundingBox_Beep", "InintDebounceMap", "set", "set2", "bindCameraUseCases", "detectObjects", "image", "Landroidx/camera/core/ImageProxy;", "initDelegate", "initModel", "initSetting", "onAttach", "context", "Landroid/content/Context;", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroy", "onDestroyView", "onError", "error", "onInit", "status", "onPause", "onResults", "inferenceTime", "", "imageHeight", "imageWidth", "onResume", "onStart", "onStop", "onViewCreated", "view", "setUpCamera", "speakOut", "text", "app_debug"})
public final class WalkModeFragment extends androidx.fragment.app.Fragment implements org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper.DetectorListener, android.speech.tts.TextToSpeech.OnInitListener {
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
    private android.speech.tts.TextToSpeech tts;
    
    /**
     * Blocking camera operations are performed using this executor
     */
    private java.util.concurrent.ExecutorService cameraExecutor;
    private java.util.Set<java.lang.String> Warning_sound;
    private java.util.Set<java.lang.String> Static_sound;
    private java.util.Set<java.lang.String> All_sound;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.Integer> DebounceMap = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.Integer> DebounceMap_D = null;
    private java.lang.String MaxResult;
    private java.lang.String Distance;
    private final kotlin.properties.ReadWriteProperty Dis$delegate = null;
    private java.lang.String Threshold;
    private java.lang.String NumThreads;
    public java.lang.String Delegate;
    private java.lang.String Ml;
    private java.lang.String Ws;
    private final java.util.Map<java.lang.String, java.lang.String> EnToCh = null;
    private android.content.SharedPreferences sp;
    
    public WalkModeFragment() {
        super();
    }
    
    private final org.tensorflow.lite.examples.objectdetection.databinding.FragmentWalkModeBinding getFragmentWalkModeBinding() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.Integer> getDebounceMap() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.Integer> getDebounceMap_D() {
        return null;
    }
    
    private final int getDis() {
        return 0;
    }
    
    private final void setDis(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDelegate() {
        return null;
    }
    
    public final void setDelegate(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initSetting() {
    }
    
    private final void InintDebounceMap(java.util.Set<java.lang.String> set, java.util.Set<java.lang.String> set2) {
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
    
    private final void Debounce(java.util.List<org.tensorflow.lite.task.vision.detector.Detection> results) {
    }
    
    private final void DetectBoundingBox(java.util.List<org.tensorflow.lite.task.vision.detector.Detection> results, java.util.Set<java.lang.String> Warning_sound) {
    }
    
    private final void Debounce_Beep(java.util.List<org.tensorflow.lite.task.vision.detector.Detection> results) {
    }
    
    private final void DetectBoundingBox_Beep(java.util.List<org.tensorflow.lite.task.vision.detector.Detection> results, java.util.Set<java.lang.String> Warning_sound) {
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
    
    private final void speakOut(java.lang.String text) {
    }
    
    @java.lang.Override()
    public void onInit(int status) {
    }
}