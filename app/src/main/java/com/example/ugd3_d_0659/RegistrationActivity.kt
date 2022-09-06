package com.example.ugd3_d_0659

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout

class RegistrationActivity : AppCompatActivity() {
    private lateinit var inputNama : TextInputLayout
    private lateinit var inputUsernameRegis : TextInputLayout
    private lateinit var inputEmail : TextInputLayout
    private lateinit var inputPasswordRegis : TextInputLayout
    private lateinit var inputConfirm : TextInputLayout
    private lateinit var inputTL : TextInputLayout
    private lateinit var inputTelepon : TextInputLayout
    private lateinit var regisLayout: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("User Login")
        inputNama = findViewById(R.id.inputLayoutNama)
        inputUsernameRegis = findViewById(R.id.inputLayoutUsername)
        inputEmail =findViewById(R.id.inputLayoutEmail)
        inputPasswordRegis = findViewById(R.id.inputLayoutPassword)
        inputConfirm = findViewById(R.id.inputLayoutKonfirmPassword)
        inputTL = findViewById(R.id.inputLayoutTanggalLahir)
        inputTelepon = findViewById(R.id.inputLayoutTelepon)
        regisLayout =findViewById(R.id.regisLayout)
        val btnBack: Button = findViewById(R.id.btnBack)
        val btnRegis: Button = findViewById(R.id.btnRegistration)

        btnBack.setOnClickListener {
            val moveLogin = Intent(this@RegistrationActivity, MainActivity::class.java)
            startActivity(moveLogin)
        }

        btnRegis.setOnClickListener(View.OnClickListener {
            var checkRegis = false
            val nama : String = inputNama.getEditText()?.getText().toString()
            val username: String = inputUsernameRegis.getEditText()?.getText().toString()
            val email : String = inputEmail.getEditText()?.getText().toString()
            val password: String = inputPasswordRegis.getEditText()?.getText().toString()
            val confirm : String = inputConfirm.getEditText()?.getText().toString()
            val tanggal : String = inputTL.getEditText()?.getText().toString()
            val telepon : String = inputTelepon.getEditText()?.getText().toString()
            val bundle = Bundle()

            if(nama.isEmpty()){
                inputNama.setError("Nama tidak boleh kosong")
                checkRegis = false
            }

            if(username.isEmpty()){
                inputUsernameRegis.setError("Username tidak boleh kosong")
                checkRegis = false
            }

            if(email.isEmpty()){
                inputEmail.setError("Email tidak boleh kosong")
                checkRegis = false
            }

            if(password.isEmpty()){
                inputPasswordRegis.setError("Password tidak boleh kosong")
                checkRegis = false
            }

            if(confirm.isEmpty()){
                inputConfirm.setError("Password tidak boleh kosong")
                checkRegis = false
            }

            if(tanggal.isEmpty()){
                inputTL.setError("Tanggal lahir tidak boleh kosong")
                checkRegis = false
            }

            if(telepon.isEmpty()){
                inputTelepon.setError("Nomor telepon tidak boleh kosong")
                checkRegis = false
            }

            if( password.compareTo(confirm)<0){
                inputConfirm.setError(" Password Salah")
                checkRegis = false
            }else{
                checkRegis = true
            }

            bundle.putString("username",username)
            bundle.putString("password",password)


            if(!checkRegis) return@OnClickListener
            val moveMain = Intent(this@RegistrationActivity, HomeActivity::class.java)
            moveMain.putExtra("login", bundle)
            startActivity(moveMain)
        })
    }
}