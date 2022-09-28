package com.example.ugd3_d_0659

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.ugd3_d_0659.room.User
import com.example.ugd3_d_0659.room.UserDB

class SplashScreen : AppCompatActivity() {
    private val myPref = "myPref"
    private val screen = "nameKey"
    var sharedPreferences: SharedPreferences? = null
    val db by lazy { UserDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(myPref,
            Context.MODE_PRIVATE)

        if (sharedPreferences!!.contains(screen)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            println("masuk situu")
        }else{
            setContentView(R.layout.activity_splash_screen)
            val editor: SharedPreferences.Editor =
                sharedPreferences!!.edit()
            editor.putString(screen, "Terisi")
            editor.apply()
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 1000)
            db.userDao().addUser(
                User(0, "admin", "admin", "admin", "admin", "11/11/2000", "085701160012")
            )
            println("masuk sinii")
        }

    }
}