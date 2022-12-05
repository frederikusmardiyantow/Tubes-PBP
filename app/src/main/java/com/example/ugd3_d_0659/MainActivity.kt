package com.example.ugd3_d_0659

import android.app.*
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.ugd3_d_0659.databinding.ActivityMainBinding
import com.example.ugd3_d_0659.webAPI.models.User
import com.example.ugd3_d_0659.webAPI.AddEditUserActivity
import com.example.ugd3_d_0659.webAPI.api.UserApi
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {
    private var etUsername: TextInputEditText? = null
    private var etPassword: TextInputEditText? = null
    private var etBtnRegister: MaterialButton? = null
    private var etBtnLogin: MaterialButton? = null

    var mBundle: Bundle? = null
    var tempUsername: String = null.toString()
    var tempPassword: String = null.toString()
    private val myPreference = "myPref"
    private val myLoginPreference = "myLogin"
    private lateinit var binding: ActivityMainBinding
    var sharedPreferences: SharedPreferences? = null
    var sharedPreferencesLogin: SharedPreferences? = null
    private var queue: RequestQueue? = null
    private var loginCek = false

    private var notificationManager: NotificationManager? = null
    private val channelID = "Tubes.news"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        queue = Volley.newRequestQueue(this)
        etUsername = binding.etLayoutUsername
        etPassword = binding.etLayoutPassword
        etBtnRegister = binding.btnRegistration
        etBtnLogin = binding.btnLogin

        Glide.with(this)
            .load("https://pmb.dalwa.ac.id/assets/img/register.png")
            .fitCenter()
            .into(binding.imageView)

        notificationManager =
            getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        createNotificationChannel(
            channelID,
            "Message", "Ini Pesan Mama dan Papa.."
        )

        getSupportActionBar()?.hide()

        //inputUsername = findViewById(R.id.inputLayoutUsername)
        //inputPassword = findViewById(R.id.inputLayoutPassword)
        //mainLayout = findViewById(R.id.mainLayout)

        if (sharedPreferences != null) {
            val usernameLogin = sharedPreferences!!.getString("user", "")
            val passwordLogin = sharedPreferences!!.getString("password", "")

            etUsername!!.setText(usernameLogin)
            etPassword!!.setText(passwordLogin)
        }

        if (intent.getBundleExtra("login") != null) {
            getBundle()
            etUsername!!.setText(tempUsername)
            etPassword!!.setText(tempPassword)
        }

        etBtnRegister!!.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditUserActivity::class.java)
            startActivity(intent)
        }

        etBtnLogin!!.setOnClickListener {
            var checkLogin = false
            var username: String = etUsername!!.text.toString()
            var password: String = etPassword!!.text.toString()


            if (username.isEmpty()) {
                etUsername!!.error = "Username harus terisi!"
                checkLogin = false
            } else {
                etUsername!!.error = null
            }

            if (password.isEmpty()) {
                etPassword!!.error = "Password harus terisi"
                checkLogin = false
            } else {
                etPassword!!.error = null
            }


            cekLogin(username, password)


        }
    }

    fun getBundle() {
        mBundle = intent.getBundleExtra("login")!!
        tempUsername = mBundle!!.getString("username")!!
        tempPassword = mBundle!!.getString("password")!!
    }

    private fun createNotificationChannel(id: String, name: String, description: String) {

        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(id, name, importance)

        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern =
            longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)


        notificationManager?.createNotificationChannel(channel)
    }

    fun sendNotification() {

        val SUMMARY_ID = 0
        val GROUP_KEY_WORK_EMAIL = "SetGroup"

        val newMessageNotification1 = NotificationCompat.Builder(this@MainActivity, channelID)
            .setSmallIcon(R.drawable.ic_email_24)
            .setContentTitle("Mama")
            .setContentText("Ayo Semangat Belajar..")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setGroup(GROUP_KEY_WORK_EMAIL)
            .build()

        val newMessageNotification2 = NotificationCompat.Builder(this@MainActivity, channelID)
            .setSmallIcon(R.drawable.ic_email_24)
            .setContentTitle("Papa")
            .setContentText("Jangan Mudah Putus Asa.. \nKamu akan dibutuhakn suatu saat!")
            .setGroup(GROUP_KEY_WORK_EMAIL)
            .build()

        val summaryNotification = NotificationCompat.Builder(this@MainActivity, channelID)
            .setContentTitle("Message")
            //set content text to support devices running API level < 24
            .setContentText("Two new messages")
            .setSmallIcon(R.drawable.logoproject_hitam)
            //build summary info into InboxStyle template
            .setStyle(
                NotificationCompat.InboxStyle()
                    .addLine("Alex Faarborg Check this out")
                    .addLine("Jeff Chang Launch Party")
                    .setBigContentTitle("2 new messages")
                    .setSummaryText("• user@bisabelajar.com •")
            )
            //specify which group this notification belongs to
            .setGroup(GROUP_KEY_WORK_EMAIL)
            //set this notification as the summary for the group
            .setGroupSummary(true)
            .build()

        var notificationId1 = 101
        var notificationId2 = 102
        NotificationManagerCompat.from(this).apply {

            notify(notificationId1, newMessageNotification1)
            notify(notificationId2, newMessageNotification2)
            notify(SUMMARY_ID, summaryNotification)
        }
    }

    private fun cekLogin(username:String, password:String){
        sharedPreferencesLogin = getSharedPreferences(myLoginPreference, Context.MODE_PRIVATE)
        val editorLogin: SharedPreferences.Editor = sharedPreferencesLogin!!.edit()
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()

        val stringRequest : StringRequest = object:
            StringRequest(Method.GET, UserApi.GET_ALL_URL, Response.Listener { response ->
                val gson = Gson()

                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("data")
                var users : Array<User> = gson.fromJson(jsonArray.toString(), Array<User>::class.java)

                if (users != null){
                    FancyToast.makeText(this@MainActivity,"Login Sukses",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                }

                val returnIntent = Intent()
                setResult(RESULT_OK,returnIntent)
            }, Response.ErrorListener { error ->
                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    if(error.networkResponse.statusCode == 422){
                        val jsonObject = JSONObject(responseBody)
                        val jsonArray = jsonObject.getJSONObject("errors")
                        println(jsonArray.toString(4))
                        for(i in jsonArray.keys()){
                            when(i){
                                "username"      ->etUsername!!.error = jsonArray.getJSONArray(i).getString(0)
                                "password"      ->etPassword!!.error = jsonArray.getJSONArray(i).getString(0)
                            }
                        }
                        FancyToast.makeText(this@MainActivity,jsonObject.getString("message"),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }else{
                        val errors = JSONObject(responseBody)
                        FancyToast.makeText(this@MainActivity,errors.getString("massage") + "Gagal",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }
                } catch (e: Exception){
                    FancyToast.makeText(this@MainActivity,e.message,FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
//                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
            @Throws(AuthFailureError::class)
            override fun getBody():ByteArray{
                val gson = Gson()
                val requestBody = gson.toJson(username)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }

        }
        queue!!.add(stringRequest)
    }

}