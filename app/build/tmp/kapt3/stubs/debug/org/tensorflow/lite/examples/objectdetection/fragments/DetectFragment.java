package org.tensorflow.lite.examples.objectdetection.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u00c6\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u000b\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010/\u001a\u0002002\u000e\u00101\u001a\n\u0012\u0004\u0012\u000203\u0018\u000102H\u0002J\u0016\u00104\u001a\u0002002\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013H\u0002J\b\u00106\u001a\u000200H\u0003J\u0016\u00107\u001a\u0002082\u0006\u00109\u001a\u00020\b2\u0006\u0010:\u001a\u00020\bJ\"\u0010;\u001a\u0004\u0018\u00010<2\u0006\u0010=\u001a\u00020<2\u0006\u0010>\u001a\u00020\b2\u0006\u0010?\u001a\u00020\bH\u0002J\u0010\u0010@\u001a\u0002002\u0006\u0010A\u001a\u00020BH\u0002J\u000e\u0010C\u001a\u00020\b2\u0006\u0010D\u001a\u00020\u0017J\u000e\u0010E\u001a\u00020\u00072\u0006\u0010F\u001a\u00020\bJ\u000e\u0010G\u001a\u00020\u00072\u0006\u0010F\u001a\u00020\bJ\u001a\u0010H\u001a\u0004\u0018\u00010<2\u000e\u00101\u001a\n\u0012\u0004\u0012\u000203\u0018\u000102H\u0002J\u000e\u0010I\u001a\u00020\u00072\u0006\u0010F\u001a\u00020\bJ\u0010\u0010J\u001a\u0002002\u0006\u0010\u000b\u001a\u00020\u0007H\u0002J\u0010\u0010K\u001a\u0002002\u0006\u0010\u000e\u001a\u00020\u0007H\u0002J\b\u0010L\u001a\u000200H\u0002J\u0010\u0010M\u001a\u0002002\u0006\u0010N\u001a\u00020OH\u0016J\u0010\u0010P\u001a\u0002002\u0006\u0010Q\u001a\u00020RH\u0016J\u0012\u0010S\u001a\u0002002\b\u0010T\u001a\u0004\u0018\u00010UH\u0016J$\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020Y2\b\u0010Z\u001a\u0004\u0018\u00010[2\b\u0010T\u001a\u0004\u0018\u00010UH\u0016J\b\u0010\\\u001a\u000200H\u0016J\b\u0010]\u001a\u000200H\u0016J\u0010\u0010^\u001a\u0002002\u0006\u0010_\u001a\u00020\u0007H\u0016J\u0010\u0010`\u001a\u0002002\u0006\u0010a\u001a\u00020\bH\u0016J\b\u0010b\u001a\u000200H\u0016J0\u0010c\u001a\u0002002\u000e\u00101\u001a\n\u0012\u0004\u0012\u000203\u0018\u0001022\u0006\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020\b2\u0006\u0010g\u001a\u00020\bH\u0016J\b\u0010h\u001a\u000200H\u0016J\b\u0010i\u001a\u000200H\u0016J\b\u0010j\u001a\u000200H\u0016J\u001a\u0010k\u001a\u0002002\u0006\u0010l\u001a\u00020W2\b\u0010T\u001a\u0004\u0018\u00010UH\u0017J\b\u0010m\u001a\u000200H\u0002J\u0010\u0010n\u001a\u0002002\u0006\u0010o\u001a\u00020\u0007H\u0002R\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\u00158BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\"\u0010#R\u0010\u0010$\u001a\u0004\u0018\u00010%X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\'X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010(\u001a\u0004\u0018\u00010)X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u0004\u0018\u00010.X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006p"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/fragments/DetectFragment;", "Landroidx/fragment/app/Fragment;", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper$DetectorListener;", "Landroid/speech/tts/TextToSpeech$OnInitListener;", "()V", "DebounceMap", "", "", "", "getDebounceMap", "()Ljava/util/Map;", "Delegate", "EnToCh", "MaxResult", "Ml", "NumThreads", "TAG", "Threshold", "Warning_sound", "", "_fragmentDetectBinding", "Lorg/tensorflow/lite/examples/objectdetection/databinding/FragmentDetectBinding;", "bitmapBuffer", "Landroid/graphics/Bitmap;", "camera", "Landroidx/camera/core/Camera;", "cameraExecutor", "Ljava/util/concurrent/ExecutorService;", "cameraProvider", "Landroidx/camera/lifecycle/ProcessCameraProvider;", "filteredDebounceSet", "getFilteredDebounceSet", "()Ljava/util/Set;", "fragmentDetectBinding", "getFragmentDetectBinding", "()Lorg/tensorflow/lite/examples/objectdetection/databinding/FragmentDetectBinding;", "imageAnalyzer", "Landroidx/camera/core/ImageAnalysis;", "objectDetectorHelper", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper;", "preview", "Landroidx/camera/core/Preview;", "selections", "sp", "Landroid/content/SharedPreferences;", "tts", "Landroid/speech/tts/TextToSpeech;", "Debounce", "", "results", "", "Lorg/tensorflow/lite/task/vision/detector/Detection;", "InintDebounceMap", "set", "bindCameraUseCases", "calculateDistance", "", "color1", "color2", "clipObjectRect", "Landroid/graphics/Rect;", "rect", "maxWidth", "maxHeight", "detectObjects", "image", "Landroidx/camera/core/ImageProxy;", "getAverageColor", "bitmap", "getClosestColorName", "color", "getColorName", "getObjectRect", "getRGBValues", "initDelegate", "initModel", "initSetting", "onAttach", "context", "Landroid/content/Context;", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroy", "onDestroyView", "onError", "error", "onInit", "status", "onPause", "onResults", "inferenceTime", "", "imageHeight", "imageWidth", "onResume", "onStart", "onStop", "onViewCreated", "view", "setUpCamera", "speakOut", "text", "app_debug"})
public final class DetectFragment extends androidx.fragment.app.Fragment implements org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper.DetectorListener, android.speech.tts.TextToSpeech.OnInitListener {
    private final java.lang.String TAG = "ObjectDetection";
    private org.tensorflow.lite.examples.objectdetection.databinding.FragmentDetectBinding _fragmentDetectBinding;
    private org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper objectDetectorHelper;
    private android.graphics.Bitmap bitmapBuffer;
    private androidx.camera.core.Preview preview;
    private androidx.camera.core.ImageAnalysis imageAnalyzer;
    private androidx.camera.core.Camera camera;
    private androidx.camera.lifecycle.ProcessCameraProvider cameraProvider;
    
    /**
     * Blocking camera operations are performed using this executor
     */
    private java.util.concurrent.ExecutorService cameraExecutor;
    private java.util.Set<java.lang.String> Warning_sound;
    private java.util.Set<java.lang.String> selections;
    private java.lang.String MaxResult;
    private java.lang.String Threshold;
    private java.lang.String NumThreads;
    private java.lang.String Delegate;
    private java.lang.String Ml;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.Integer> DebounceMap = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.String> filteredDebounceSet = null;
    private android.speech.tts.TextToSpeech tts;
    private android.content.SharedPreferences sp;
    private final java.util.Map<java.lang.String, java.lang.String> EnToCh = null;
    
    public DetectFragment() {
        super();
    }
    
    private final org.tensorflow.lite.examples.objectdetection.databinding.FragmentDetectBinding getFragmentDetectBinding() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.Integer> getDebounceMap() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> getFilteredDebounceSet() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initSetting() {
    }
    
    private final void InintDebounceMap(java.util.Set<java.lang.String> set) {
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
    
    private final android.graphics.Rect clipObjectRect(android.graphics.Rect rect, int maxWidth, int maxHeight) {
        return null;
    }
    
    private final android.graphics.Rect getObjectRect(java.util.List<org.tensorflow.lite.task.vision.detector.Detection> results) {
        return null;
    }
    
    public final int getAverageColor(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap bitmap) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getClosestColorName(int color) {
        return null;
    }
    
    public final double calculateDistance(int color1, int color2) {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getColorName(int color) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRGBValues(int color) {
        return null;
    }
    
    private final void Debounce(java.util.List<org.tensorflow.lite.task.vision.detector.Detection> results) {
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