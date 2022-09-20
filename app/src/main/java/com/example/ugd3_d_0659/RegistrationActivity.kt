package com.example.ugd3_d_0659

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class RegistrationActivity : AppCompatActivity() {
    private lateinit var inputNama : TextInputLayout
    private lateinit var inputUsernameRegis : TextInputLayout
    private lateinit var inputEmail : TextInputLayout
    private lateinit var inputPasswordRegis : TextInputLayout
    private lateinit var inputConfirm : TextInputLayout
    private lateinit var inputTL : TextInputLayout
    private lateinit var inputTelepon : TextInputLayout
    private lateinit var inputTextTLLangsung : TextInputEditText
    private lateinit var regisLayout: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val cal = Calendar.getInstance()
        val myYear = cal.get(Calendar.YEAR)
        val myDay = cal.get(Calendar.DAY_OF_MONTH)
        val myMonth = cal.get(Calendar.MONTH)

        getSupportActionBar()?.hide()

        inputNama = findViewById(R.id.inputLayoutNama)
        inputUsernameRegis = findViewById(R.id.inputLayoutUsernameRegis)
        inputEmail =findViewById(R.id.inputLayoutEmail)
        inputPasswordRegis = findViewById(R.id.inputLayoutPasswordRegis)
        inputConfirm = findViewById(R.id.inputLayoutKonfirmPassword)
        inputTL = findViewById(R.id.inputLayoutTanggalLahir)
        inputTelepon = findViewById(R.id.inputLayoutTelepon)
        regisLayout =findViewById(R.id.regisLayout)
        inputTextTLLangsung = findViewById(R.id.inputTextTanggalLahirLangsung)


        var btnBack: Button = findViewById(R.id.btnBack)
        var btnRegis: Button = findViewById(R.id.btnRegistration)

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
            val mBundle = Bundle()

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

            if(password.length!=8 || confirm.length!=8){
                inputPasswordRegis.setError("Password harus 8 karakter!")
                inputConfirm.setError("Password harus 8 karakter!")
                checkRegis = false
            }

            if(confirm!=password){
                inputPasswordRegis.setError("Password tidak sama!")
                inputConfirm.setError("Password tidak sama!")
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

            if(nama.isNotEmpty() &&  username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty() && password.length==8 && confirm.length==8
                && confirm==password && tanggal.isNotEmpty() && telepon.isNotEmpty()){
                checkRegis = true
            }

            mBundle.putString("username",username)
            mBundle.putString("password",password)

            if(!checkRegis) return@OnClickListener
            val moveMain = Intent(this@RegistrationActivity, MainActivity::class.java)
            moveMain.putExtra("login", mBundle)
            startActivity(moveMain)
        })

        inputTextTLLangsung.setOnFocusChangeListener { view, b ->
            val datePicker= DatePickerDialog(this,DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
                inputTextTLLangsung.setText("${dayOfMonth}/${(month.toInt()+1).toString()}/${year}")

            }, myYear, myMonth, myDay)

            if(b){
                datePicker.show()
            }else{
                datePicker.hide()
            }
        }
    }
}