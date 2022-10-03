package com.example.ugd3_d_0659

import android.app.*
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.Icon
import android.os.Build
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.ugd3_d_0659.databinding.ActivityMainBinding
import com.example.ugd3_d_0659.databinding.ActivityRegistrationBinding
import com.example.ugd3_d_0659.room.User
import com.example.ugd3_d_0659.room.UserDB
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    //private lateinit var inputUsername: TextInputLayout
    //private lateinit var inputPassword: TextInputLayout
    //private lateinit var mainLayout: ConstraintLayout
    var mBundle: Bundle? = null
    var tempUsername: String = "admin"
    var tempPassword: String = "admin"
    private val myPreference = "myPref"
    private val myLoginPreference = "myLogin"
    private val id = "nameKey"
    private val user = "userLogin"
    private val pass = "passLogin"
    private lateinit var binding: ActivityMainBinding
    var sharedPreferences: SharedPreferences? = null
    val db by lazy { UserDB(this) }

    private var notificationManager: NotificationManager? = null
    private val channelID = "Tubes.news"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationManager =
            getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        createNotificationChannel(
            channelID,
            "Message", "Ini Pesan Mama dan Papa.."
        )

        sharedPreferences = getSharedPreferences(myLoginPreference, Context.MODE_PRIVATE)

        getSupportActionBar()?.hide()

        //inputUsername = findViewById(R.id.inputLayoutUsername)
        //inputPassword = findViewById(R.id.inputLayoutPassword)
        //mainLayout = findViewById(R.id.mainLayout)

        if (sharedPreferences != null) {
            val usernameLogin = sharedPreferences!!.getString(user, "")
            val passwordLogin = sharedPreferences!!.getString(pass, "")

            binding.inputLayoutUsername.getEditText()?.setText(usernameLogin)
            binding.inputLayoutPassword.getEditText()?.setText(passwordLogin)
        }

        if (intent.getBundleExtra("login") != null) {
            getBundle()
            binding.inputLayoutUsername.getEditText()?.setText(tempUsername)
            binding.inputLayoutPassword.getEditText()?.setText(tempPassword)
        }

        //val btnRegis: Button = findViewById(R.id.btnRegistration)
        //val btnLogin: Button = findViewById(R.id.btnLogin)

        binding.btnRegistration.setOnClickListener {
            val intent = Intent(this@MainActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin = false
            var username: String = binding.inputLayoutUsername.getEditText()?.getText().toString()
            var password: String = binding.inputLayoutPassword.getEditText()?.getText().toString()
            sharedPreferences = getSharedPreferences(
                myPreference,
                Context.MODE_PRIVATE
            )
            val editor: SharedPreferences.Editor =
                sharedPreferences!!.edit()
            sharedPreferences = getSharedPreferences(
                myLoginPreference,
                Context.MODE_PRIVATE
            )
            val editorLogin: SharedPreferences.Editor =
                sharedPreferences!!.edit()


            if (db.userDao().getUserLogin(username, password) != null) {
                tempUsername = db.userDao().getUserLogin(username, password)!!.username
                tempPassword = db.userDao().getUserLogin(username, password)!!.password
            }

            if (username.isEmpty()) {
                binding.inputLayoutUsername.setError("Username must be filled with text!")
                checkLogin = false
            } else {
                binding.inputLayoutUsername.error = null
            }

            if (password.isEmpty()) {
                binding.inputLayoutPassword.setError("Password must be filled with text")
                checkLogin = false
            } else {
                binding.inputLayoutPassword.error = null
            }

            if ((username == tempUsername && password == tempPassword)) {
                editor.putInt(id, db.userDao().getUserLogin(username, password)!!.id)
                editor.apply()
                editorLogin.putString(
                    user,
                    db.userDao().getUserLogin(username, password)!!.username
                )
                editorLogin.putString(
                    pass,
                    db.userDao().getUserLogin(username, password)!!.password
                )
                editorLogin.apply()
                checkLogin = true
            } else {
                if (username.isNotEmpty() && password.isNotEmpty() && db.userDao()
                        .getUserLogin(username, password) == null
                ) {
                    Snackbar.make(
                        binding.mainLayout,
                        "Login Invalid! Check your input Username and Password",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }

            if (!checkLogin) return@OnClickListener
            val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
            sendNotification()
            startActivity(moveHome)
        })

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
}