package com.example.ugd3_d_0659

import android.annotation.SuppressLint
import android.content.Intent
import android.hardware.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton

class CameraActivity : AppCompatActivity() {
    private var mCamera: Camera? = null
    private var  mCameraView: CameraView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        try {
            mCamera = Camera.open()
        }catch (e: Exception){
            Log.d("Error", "Failed to Get Camera" + e.message)
        }
        if (mCamera != null){
            mCameraView = CameraView(this,mCamera!!)
            val camera_view = findViewById<View>(R.id.FLCamera) as FrameLayout
            camera_view.addView(mCameraView)
        }
        @SuppressLint("MissingInflatedId", "LocalSuppress") val imageClose =
            findViewById<View>(R.id.toMenu) as ImageButton
        imageClose.setOnClickListener {
            val moveHome = Intent(this@CameraActivity, HomeActivity::class.java)
            startActivity(moveHome)
        }
    }
}