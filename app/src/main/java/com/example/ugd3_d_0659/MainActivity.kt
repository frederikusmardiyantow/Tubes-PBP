package com.example.ugd3_d_0659

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var mainLayout: ConstraintLayout
    lateinit var mBundle: Bundle
    lateinit var tempUsername :String
    lateinit var tempPassword : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("User Login")


        inputUsername= findViewById(R.id.inputLayoutUsername)
        inputPassword= findViewById(R.id.inputLayoutPassword)
        mainLayout= findViewById(R.id.mainLayout)

        getBundle()
        inputUsername.getEditText()?.setText(tempUsername)




        val btnRegis: Button = findViewById(R.id.btnRegistration)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        btnRegis.setOnClickListener {
            val intent = Intent(this@MainActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin = false
            var username: String = inputUsername.getEditText()?.getText().toString()
            var password: String = inputPassword.getEditText()?.getText().toString()

            if(username.isEmpty()){
                inputUsername.setError("Username must be filled with text!")
                checkLogin=false
            }
            if(password.isEmpty()){
                inputPassword.setError("Password must be filled with text")
                checkLogin=false
            }

            if(username.compareTo(tempUsername)<0 && password.compareTo(tempPassword)<0) checkLogin=true
            if(!checkLogin) return@OnClickListener
            val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(moveHome)
        })

    }

    fun getBundle() {
        mBundle = intent.getBundleExtra("register")!!
        tempUsername = mBundle.getString("username")!!
        tempPassword = mBundle.getString("password")!!
    }
}