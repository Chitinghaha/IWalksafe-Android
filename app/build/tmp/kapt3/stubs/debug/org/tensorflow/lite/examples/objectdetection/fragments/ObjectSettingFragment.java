package org.tensorflow.lite.examples.objectdetection.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020)H\u0002J\b\u0010*\u001a\u00020\'H\u0002J\u0012\u0010+\u001a\u00020\'2\b\u0010,\u001a\u0004\u0018\u00010-H\u0016J\b\u0010.\u001a\u00020\'H\u0016J\b\u0010/\u001a\u00020\'H\u0016J\u001a\u00100\u001a\u00020\'2\u0006\u00101\u001a\u0002022\b\u0010,\u001a\u0004\u0018\u00010-H\u0016J\u0010\u00103\u001a\u00020\'2\u0006\u00104\u001a\u00020\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R \u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010 \u001a\u00020!X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%\u00a8\u00065"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/fragments/ObjectSettingFragment;", "Landroidx/fragment/app/Fragment;", "()V", "Et", "Landroid/widget/EditText;", "btn", "Landroid/widget/Button;", "btnSTT", "btnSpeak", "chipNavigationBar", "Lcom/google/android/material/bottomnavigation/BottomNavigationView;", "data", "", "", "getData", "()Ljava/util/List;", "setData", "(Ljava/util/List;)V", "database", "Lcom/google/firebase/database/DatabaseReference;", "editor", "Landroid/content/SharedPreferences$Editor;", "getEditor", "()Landroid/content/SharedPreferences$Editor;", "setEditor", "(Landroid/content/SharedPreferences$Editor;)V", "manager", "Landroidx/recyclerview/widget/RecyclerView$LayoutManager;", "myAdapter", "Lorg/tensorflow/lite/examples/objectdetection/MyAdapterForObject;", "pref", "Landroid/content/SharedPreferences;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "getRecyclerView", "()Landroidx/recyclerview/widget/RecyclerView;", "setRecyclerView", "(Landroidx/recyclerview/widget/RecyclerView;)V", "deltem", "", "index", "", "displaySpeechRecognizer_second", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onStop", "onViewCreated", "view", "Landroid/view/View;", "passItem", "str", "app_debug"})
public final class ObjectSettingFragment extends androidx.fragment.app.Fragment {
    private android.widget.Button btnSpeak;
    private android.widget.Button btn;
    private android.widget.EditText Et;
    private android.widget.Button btnSTT;
    private androidx.recyclerview.widget.RecyclerView.LayoutManager manager;
    private org.tensorflow.lite.examples.objectdetection.MyAdapterForObject myAdapter;
    public java.util.List<java.lang.String> data;
    private com.google.firebase.database.DatabaseReference database;
    private android.content.SharedPreferences pref;
    public android.content.SharedPreferences.Editor editor;
    public androidx.recyclerview.widget.RecyclerView recyclerView;
    private com.google.android.material.bottomnavigation.BottomNavigationView chipNavigationBar;
    
    public ObjectSettingFragment() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getData() {
        return null;
    }
    
    public final void setData(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.SharedPreferences.Editor getEditor() {
        return null;
    }
    
    public final void setEditor(@org.jetbrains.annotations.NotNull()
    android.content.SharedPreferences.Editor p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.recyclerview.widget.RecyclerView getRecyclerView() {
        return null;
    }
    
    public final void setRecyclerView(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView p0) {
    }
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void displaySpeechRecognizer_second() {
    }
    
    private final void passItem(java.lang.String str) {
    }
    
    @java.lang.Override()
    public void onStop() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    private final void deltem(int index) {
    }
}