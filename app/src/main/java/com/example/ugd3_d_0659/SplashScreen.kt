package com.example.ugd3_d_0659

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {
    private val myPref = "myPrefSplash"
    private val screen = "nameKey"
    var sharedPreferences: SharedPreferences? = null

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
        }

    }
}