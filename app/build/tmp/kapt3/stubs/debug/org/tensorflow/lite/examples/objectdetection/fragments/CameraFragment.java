package org.tensorflow.lite.examples.objectdetection.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u00a2\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010!\u001a\u00020\"H\u0003J\u0010\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020%H\u0002J\b\u0010&\u001a\u00020\"H\u0002J\b\u0010\'\u001a\u00020\"H\u0002J\u0010\u0010(\u001a\u00020\"2\u0006\u0010)\u001a\u00020*H\u0016J\u0010\u0010+\u001a\u00020\"2\u0006\u0010,\u001a\u00020-H\u0016J\u0012\u0010.\u001a\u00020\"2\b\u0010/\u001a\u0004\u0018\u000100H\u0016J$\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\b\u00105\u001a\u0004\u0018\u0001062\b\u0010/\u001a\u0004\u0018\u000100H\u0016J\b\u00107\u001a\u00020\"H\u0016J\b\u00108\u001a\u00020\"H\u0016J\u0010\u00109\u001a\u00020\"2\u0006\u0010:\u001a\u00020\u0005H\u0016J\b\u0010;\u001a\u00020\"H\u0016J0\u0010<\u001a\u00020\"2\u000e\u0010=\u001a\n\u0012\u0004\u0012\u00020?\u0018\u00010>2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020CH\u0016J\b\u0010E\u001a\u00020\"H\u0016J\b\u0010F\u001a\u00020\"H\u0016J\b\u0010G\u001a\u00020\"H\u0016J\u001a\u0010H\u001a\u00020\"2\u0006\u0010I\u001a\u0002022\b\u0010/\u001a\u0004\u0018\u000100H\u0017J\b\u0010J\u001a\u00020\"H\u0002J\u0014\u0010K\u001a\u00020\"2\f\u0010L\u001a\b\u0012\u0004\u0012\u00020?0>J\b\u0010M\u001a\u00020\"H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00078BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006N"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/fragments/CameraFragment;", "Landroidx/fragment/app/Fragment;", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper$DetectorListener;", "()V", "TAG", "", "_fragmentCameraBinding", "Lorg/tensorflow/lite/examples/objectdetection/databinding/FragmentCameraBinding;", "bitmapBuffer", "Landroid/graphics/Bitmap;", "camera", "Landroidx/camera/core/Camera;", "cameraExecutor", "Ljava/util/concurrent/ExecutorService;", "cameraProvider", "Landroidx/camera/lifecycle/ProcessCameraProvider;", "findname", "fragmentCameraBinding", "getFragmentCameraBinding", "()Lorg/tensorflow/lite/examples/objectdetection/databinding/FragmentCameraBinding;", "imageAnalyzer", "Landroidx/camera/core/ImageAnalysis;", "mp", "Landroid/media/MediaPlayer;", "mp1", "mp2", "mp3", "mp4", "mp5", "objectDetectorHelper", "Lorg/tensorflow/lite/examples/objectdetection/ObjectDetectorHelper;", "preview", "Landroidx/camera/core/Preview;", "bindCameraUseCases", "", "detectObjects", "image", "Landroidx/camera/core/ImageProxy;", "dovibrate", "initBottomSheetControls", "onAttach", "context", "Landroid/content/Context;", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroy", "onDestroyView", "onError", "error", "onPause", "onResults", "results", "", "Lorg/tensorflow/lite/task/vision/detector/Detection;", "inferenceTime", "", "imageHeight", "", "imageWidth", "onResume", "onStart", "onStop", "onViewCreated", "view", "setUpCamera", "sound_output", "detectionResults", "updateControlsUi", "app_debug"})
public final class CameraFragment extends androidx.fragment.app.Fragment implements org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper.DetectorListener {
    private final java.lang.String TAG = "ObjectDetection";
    private org.tensorflow.lite.examples.objectdetection.databinding.FragmentCameraBinding _fragmentCameraBinding;
    private org.tensorflow.lite.examples.objectdetection.ObjectDetectorHelper objectDetectorHelper;
    private android.graphics.Bitmap bitmapBuffer;
    private androidx.camera.core.Preview preview;
    private androidx.camera.core.ImageAnalysis imageAnalyzer;
    private androidx.camera.core.Camera camera;
    private androidx.camera.lifecycle.ProcessCameraProvider cameraProvider;
    private android.media.MediaPlayer mp;
    private android.media.MediaPlayer mp1;
    private android.media.MediaPlayer mp2;
    private android.media.MediaPlayer mp3;
    private android.media.MediaPlayer mp4;
    private android.media.MediaPlayer mp5;
    
    /**
     * Blocking camera operations are performed using this executor
     */
    private java.util.concurrent.ExecutorService cameraExecutor;
    private java.lang.String findname;
    
    public CameraFragment() {
        super();
    }
    
    private final org.tensorflow.lite.examples.objectdetection.databinding.FragmentCameraBinding getFragmentCameraBinding() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
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
    
    private final void initBottomSheetControls() {
    }
    
    private final void updateControlsUi() {
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
    
    public final void sound_output(@org.jetbrains.annotations.NotNull()
    java.util.List<org.tensorflow.lite.task.vision.detector.Detection> detectionResults) {
    }
    
    private final void dovibrate() {
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