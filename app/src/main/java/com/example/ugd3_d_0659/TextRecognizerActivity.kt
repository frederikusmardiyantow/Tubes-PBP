package com.example.ugd3_d_0659

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextRecognizer
import com.google.android.material.button.MaterialButton

class TextRecognizerActivity : AppCompatActivity(), View.OnClickListener {
    companion object{
        private const val CAMERA_REQUEST_CODE = 100
        private const val STORAGE_REQUEST_CODE = 101
        private const val TAG = "MAIN_TAG"
    }
    private lateinit var cameraPermissions:Array<String>
    private lateinit var storagePermission:Array<String>
    private var imageUri: Uri? =null
    private val imageTv = findViewById<ImageView>(R.id.imageTv)
    private val tv = findViewById<TextView>(R.id.resultView)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_recognizer)

        val cameraBtn = findViewById<MaterialButton>(R.id.cameraBtn)
        val scanBtn = findViewById<MaterialButton>(R.id.scanBtn)


        cameraBtn.setOnClickListener(this)
        scanBtn.setOnClickListener(this)
        cameraPermissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        storagePermission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.cameraBtn -> {
                if (checkCameraPermissions()){
                    pickImageCamera()
                }else{
                    requestCameraPermission()
                }
            }
            R.id.scanBtn -> {
                if (imageUri == null){
                    showToast("Pick Image First")
                }else{
                    detectResultFromImage()
                }
            }
        }
    }

    private fun detectResultFromImage() {
        var recognizer = TextRecognizer.Builder(this@TextRecognizerActivity).build()
        var bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
        val frame = Frame.Builder().setBitmap(bitmap).build()
        val textBlockSparseArray = recognizer.detect(frame)
        val stringBuilder = StringBuilder()

        for (i in 0 until  textBlockSparseArray.size()){
            val textBlock = textBlockSparseArray.valueAt(i)
            stringBuilder.append(textBlock.value)
            stringBuilder.append("\n")
        }
        tv.text = stringBuilder.toString()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE)
    }

    private fun pickImageCamera() {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE, "Foto Sample")
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Deskripsi Foto Sample")

        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)


        cameraActivityResultLauncher.launch(intent)
    }
    private val cameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data = result.data
            Log.d(TAG, "cameraActivityResultLauncher: imageUri: $imageUri")
            imageTv.setImageURI(imageUri)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()){
                    val cameraAcepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val storageAcepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                    if (cameraAcepted && storageAcepted){
                        pickImageCamera()
                    }else{
                        showToast("Camera and Storage permission are required...")
                    }
                }
            }
        }
    }

    private fun checkCameraPermissions(): Boolean {
        val resultcamera = (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)
        val resultstorage = (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
        return resultcamera && resultstorage
    }
}