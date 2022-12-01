package com.example.ugd3_d_0659.webAPI

import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ugd3_d_0659.MainActivity
import com.example.ugd3_d_0659.NotificationReceiver
import com.example.ugd3_d_0659.R
import com.example.ugd3_d_0659.databinding.ActivityAddEditBinding
import com.example.ugd3_d_0659.webAPI.api.UserApi
import com.example.ugd3_d_0659.webAPI.models.User
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.HashMap

class AddEditUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditBinding

    private var etNama: TextInputEditText? = null
    private var etUsername: TextInputEditText? = null
    private var etEmail: TextInputEditText? = null
    private var etPassword: TextInputEditText? = null
    private var etKonfirmasiPassword: TextInputEditText? = null
    private var etTglLahir: TextInputEditText? = null
    private var etTelp: TextInputEditText? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null
    var sharedPreferences: SharedPreferences? = null
    private val myPreference = "myPref"
    val mBundle = Bundle()
    private val CHANNEL_ID = "channel_notification"
    private val notification = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cal = Calendar.getInstance()
        val myYear = cal.get(Calendar.YEAR)
        val myDay = cal.get(Calendar.DAY_OF_MONTH)
        val myMonth = cal.get(Calendar.MONTH)

        //supportActionBar?.hide()

        queue = Volley.newRequestQueue(this)
        etNama = binding.etNama
        etUsername = binding.etUsername
        etEmail = binding.etEmail
        etPassword = binding.etPassword
        etKonfirmasiPassword = binding.etKonfirmasiPassword
        etTglLahir = binding.etTglLahir
        etTelp = binding.etTelp
        layoutLoading = findViewById(R.id.layout_loading)

        
        val btnCancel:Button = binding.btnCancel
        btnCancel.setOnClickListener { finish() }

        val btnSave:Button = binding.btnSave
        val tvTitle:TextView = binding.tvTitle
        val id = intent.getLongExtra("id", -1)
        if(id== -1L){
            tvTitle.setText("REGISTRASI AKUN")
            btnSave.setOnClickListener { createUser() }
        }else{
            tvTitle.setText("UBAH DATA AKUN")
            getUserById(id)
            btnSave.setOnClickListener { updateUser(id) }
        }

        etTglLahir!!.setOnFocusChangeListener { view, b ->
            val datePicker= DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                    val day2digit = String.format("%02d", dayOfMonth)
                    etTglLahir!!.setText("${day2digit}/${(month.toInt()+1)}/${year}")

            }, myYear, myMonth, myDay)

            if(b){
                datePicker.show()
            }else{
                datePicker.hide()
            }
        }
    }

    private fun getUserById(id: Long){
        setLoading(true)

        val stringRequest: StringRequest = object :
            StringRequest(
                Method.GET, UserApi.GET_BY_ID_URL + id,
                { response ->
                    val jsonObject = JSONObject(response)
                    val jsonArray = jsonObject.getJSONObject("data")
                    val user = Gson().fromJson(jsonArray.toString(), User::class.java)

                    etNama!!.setText(user.nama)
                    etUsername!!.setText(user.username)
                    etEmail!!.setText(user.email)
                    etPassword!!.setText(user.password)
                    etKonfirmasiPassword!!.setText(user.konfirmasiPassword)
                    etTglLahir!!.setText(user.tglLahir)
                    etTelp!!.setText(user.telp)

                    FancyToast.makeText(this@AddEditUserActivity,"Data berhasil diambil",FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();
//                    Toast.makeText(this@AddEditUserActivity,"Data berhasil diambil", Toast.LENGTH_SHORT).show()
                    setLoading(false)
                },
                Response.ErrorListener{ error ->
                    setLoading(false)
                    try{
                        val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        FancyToast.makeText(this@AddEditUserActivity,errors.getString("message"),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
//                        Toast.makeText(
//                            this,
//                            errors.getString("message"),
//                            Toast.LENGTH_SHORT
//                        ).show()
                    } catch (e: Exception){
                        FancyToast.makeText(this@AddEditUserActivity,e.message,FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
//                        Toast.makeText(this@AddEditUserActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)

    }

    private fun createUser(){
        setLoading(true)

        val user = User(
            etNama!!.text.toString(),
            etUsername!!.text.toString(),
            etEmail!!.text.toString(),
            etPassword!!.text.toString(),
            etKonfirmasiPassword!!.text.toString(),
            etTglLahir!!.text.toString(),
            etTelp!!.text.toString(),
        )

        val stringRequest: StringRequest =
            object: StringRequest(Method.POST, UserApi.ADD_URL, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONObject("user")
                val users = gson.fromJson(jsonArray.toString(), User::class.java)

                if(users != null)
                    FancyToast.makeText(this@AddEditUserActivity,"Registrasi Sukses",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
//                    Toast.makeText(this@AddEditUserActivity, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
//                finish()
                mBundle.putString("username",user.username)
                mBundle.putString("password",user.password)

                val moveMain = Intent(this@AddEditUserActivity, MainActivity::class.java)
                moveMain.putExtra("login", mBundle)
                startActivity(moveMain)
                sendNotification()

                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    if(error.networkResponse.statusCode == 422){
                        val jsonObject = JSONObject(responseBody)
                        val jsonArray = jsonObject.getJSONObject("errors")
                        println(jsonArray.toString(4))
                        for(i in jsonArray.keys()){
                            when(i){
                                "nama"      -> etNama!!.error = jsonArray.getJSONArray(i).getString(0)
                                "username"  -> etUsername!!.error = jsonArray.getJSONArray(i).getString(0)
                                "email"     -> etEmail!!.error = jsonArray.getJSONArray(i).getString(0)
                                "password"  -> etPassword!!.error = jsonArray.getJSONArray(i).getString(0)
                                "konfirmasiPassword" -> etKonfirmasiPassword!!.error = jsonArray.getJSONArray(i).getString(0)
                                "tglLahir"  -> etTglLahir!!.error = jsonArray.getJSONArray(i).getString(0)
                                "telp"      -> etTelp!!.error = jsonArray.getJSONArray(i).getString(0)
                            }
                        }
                        FancyToast.makeText(this@AddEditUserActivity,jsonObject.getString("message"),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }else{
                        val errors = JSONObject(responseBody)
                        FancyToast.makeText(this@AddEditUserActivity,errors.getString("message") + "rwqrqw",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }
                } catch (e: Exception){
                    FancyToast.makeText(this@AddEditUserActivity,e.message,FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
//                    Toast.makeText(this@AddEditUserActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }){
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    return headers
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    val gson = Gson()
                    val requestBody = gson.toJson(user)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }

        queue!!.add(stringRequest)

    }

    private fun updateUser(id: Long){
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()

        setLoading(true)
        val user = User(
            etNama!!.text.toString(),
            etUsername!!.text.toString(),
            etEmail!!.text.toString(),
            etPassword!!.text.toString(),
            etKonfirmasiPassword!!.text.toString(),
            etTglLahir!!.text.toString(),
            etTelp!!.text.toString(),
        )
        val stringRequest: StringRequest =
            object: StringRequest(Method.PUT, UserApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONObject("data")
                var user = gson.fromJson(jsonArray.toString(), User::class.java)

                if(user != null){
                    FancyToast.makeText(this@AddEditUserActivity,"Edit Profile Sukses",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
//                    Toast.makeText(this@AddEditUserActivity, "Data berhasil diubah", Toast.LENGTH_SHORT).show()
                    editor.putString("user", user.username)
                    editor.putString("password", user.password)
                    editor.putString("nama", user.nama)
                    editor.commit()
                }


                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()

                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    if(error.networkResponse.statusCode == 422){
                        val jsonObject = JSONObject(responseBody)
                        val jsonArray = jsonObject.getJSONObject("errors")
                        for(i in jsonArray.keys()){
                            when(i){
                                "nama"      -> etNama!!.error = jsonArray.getJSONArray(i).getString(0)
                                "username"  -> etUsername!!.error = jsonArray.getJSONArray(i).getString(0)
                                "email"     -> etEmail!!.error = jsonArray.getJSONArray(i).getString(0)
                                "password"  -> etPassword!!.error = jsonArray.getJSONArray(i).getString(0)
                                "konfirmasiPassword" -> etKonfirmasiPassword!!.error = jsonArray.getJSONArray(i).getString(0)
                                "tglLahir"  -> etTglLahir!!.error = jsonArray.getJSONArray(i).getString(0)
                                "telp"      -> etTelp!!.error = jsonArray.getJSONArray(i).getString(0)
                            }
                        }
                        FancyToast.makeText(this@AddEditUserActivity,jsonObject.getString("message"),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }else{
                        val errors = JSONObject(responseBody)
                        FancyToast.makeText(this@AddEditUserActivity,errors.getString("message") + "rwqrqw",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }

                } catch (e: Exception){
                    FancyToast.makeText(this@AddEditUserActivity,e.message,FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
//                    Toast.makeText(this@AddEditUserActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }){
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    return headers
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    val gson = Gson()
                    val requestBody = gson.toJson(user)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }
        queue!!.add(stringRequest)

    }

    private fun validation(id:Long){

        val stringRequest : StringRequest = object:
            StringRequest(Method.GET, UserApi.GET_ALL_URL, Response.Listener { response ->
                if(id== -1L){
                    createUser()
                }else{
                    updateUser(id)
                }
            }, Response.ErrorListener { error ->
                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    if(error.networkResponse.statusCode == 400){
                        val jsonObject = JSONObject(responseBody)
                        val jsonArray = jsonObject.getJSONObject("message")
                        for(i in jsonArray.keys()){
                            if(i=="nama"){
                                etNama!!.error = jsonArray.getJSONArray(i).getString(0)
                            }
                            if(i=="username"){
                                etUsername!!.error = jsonArray.getJSONArray(i).getString(0)
                            }
                            if(i=="email"){
                                etEmail!!.error = jsonArray.getJSONArray(i).getString(0)
                            }
                            if(i=="password"){
                                etPassword!!.error = jsonArray.getJSONArray(i).getString(0)
                            }
                            if(i=="konfirmasiPassword"){
                                etKonfirmasiPassword!!.error = jsonArray.getJSONArray(i).getString(0)
                            }
                            if(i=="tglLahir"){
                                etTglLahir!!.error = jsonArray.getJSONArray(i).getString(0)
                            }
                            if(i=="telp"){
                                etTelp!!.error = jsonArray.getJSONArray(i).getString(0)
                            }
                        }
                    }else{
                        val errors = JSONObject(responseBody)
                        FancyToast.makeText(this@AddEditUserActivity,errors.getString("message"),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
//                        Toast.makeText(this@AddEditUserActivity, errors.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception){
                    FancyToast.makeText(this@AddEditUserActivity,e.message,FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
//                    Toast.makeText(this@AddEditUserActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
            }
        queue!!.add(stringRequest)

    }

    private fun sendNotification() {
        val intent: Intent = Intent(this,MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_MUTABLE)
        val broadcastIntent: Intent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", etUsername?.text.toString())
        val actionIntent= PendingIntent.getBroadcast(this,0,broadcastIntent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val largeIcon : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.congrat)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_profil)
            .setContentTitle("Berhasil Registrasi!!!")
            .setContentText(etUsername?.text.toString())
            .setLargeIcon(largeIcon)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(largeIcon).bigLargeIcon(null))
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.BLUE)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher,"Toast", actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)){
            notify(notification, builder.build())
        }
    }

    private fun setLoading(isLoading: Boolean){
        if(isLoading){
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading!!.visibility = View.INVISIBLE
        }else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.visibility = View.INVISIBLE
        }
    }

}