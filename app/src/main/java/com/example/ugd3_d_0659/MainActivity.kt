package com.example.ugd3_d_0659

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.ugd3_d_0659.room.User
import com.example.ugd3_d_0659.room.UserDB
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var mainLayout: ConstraintLayout
    var mBundle: Bundle? = null
    var tempUsername :String = "admin"
    var tempPassword : String ="admin"
    private val myPreference = "myPref"
    private val myLoginPreference = "myLogin"
    private val id = "nameKey"
    private val user = "userLogin"
    private val pass = "passLogin"
    var sharedPreferences: SharedPreferences? = null
    val db by lazy { UserDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(myLoginPreference, Context.MODE_PRIVATE)

        getSupportActionBar()?.hide()

        inputUsername= findViewById(R.id.inputLayoutUsername)
        inputPassword= findViewById(R.id.inputLayoutPassword)
        mainLayout= findViewById(R.id.mainLayout)

        db.userDao().addUser(
            User(0, "admin", "admin", "admin", "admin", "11/11/2000", "085701160012")
        )

        if(sharedPreferences!=null){
            val usernameLogin = sharedPreferences!!.getString(user,"")
            val passwordLogin = sharedPreferences!!.getString(pass,"")

            inputUsername.getEditText()?.setText(usernameLogin)
            inputPassword.getEditText()?.setText(passwordLogin)
        }

        if(intent.getBundleExtra("login")!=null){
            getBundle()
            inputUsername.getEditText()?.setText(tempUsername)
            inputPassword.getEditText()?.setText(tempPassword)
        }

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
            sharedPreferences = getSharedPreferences(myPreference,
                Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor =
                sharedPreferences!!.edit()
            sharedPreferences = getSharedPreferences(myLoginPreference,
                Context.MODE_PRIVATE)
            val editorLogin: SharedPreferences.Editor =
                sharedPreferences!!.edit()


            if(db.userDao().getUserLogin(username,password)!= null) {
                tempUsername = db.userDao().getUserLogin(username,password)!!.username
                tempPassword = db.userDao().getUserLogin(username,password)!!.password
            }

            if(username.isEmpty()){
                inputUsername.setError("Username must be filled with text!")
                checkLogin=false
            }else{
                inputUsername.error = null
            }

            if(password.isEmpty()){
                inputPassword.setError("Password must be filled with text")
                checkLogin=false
            }else{
                inputPassword.error = null
            }

            if((username == tempUsername && password == tempPassword) ) {
                editor.putInt(id, db.userDao().getUserLogin(username, password)!!.id)
                editor.apply()
                editorLogin.putString(user, db.userDao().getUserLogin(username, password)!!.username)
                editorLogin.putString(pass, db.userDao().getUserLogin(username, password)!!.password)
                editorLogin.apply()
                checkLogin=true
            }else{
                if(username.isNotEmpty() && password.isNotEmpty() && db.userDao().getUserLogin(username,password)==null){
                    Snackbar.make(mainLayout,"Login Invalid! Check your input Username and Password", Snackbar.LENGTH_LONG).show()
                }
            }

            if(!checkLogin) return@OnClickListener
            val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(moveHome)
        })

    }

    fun getBundle() {
        mBundle = intent.getBundleExtra("login")!!
        tempUsername = mBundle!!.getString("username")!!
        tempPassword = mBundle!!.getString("password")!!
    }
}