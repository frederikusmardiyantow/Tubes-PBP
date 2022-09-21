package com.example.ugd3_d_0659

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.ugd3_d_0659.databinding.ActivityRegistrationBinding
import java.util.*

class RegistrationActivity : AppCompatActivity() {
    //private lateinit var inputNama : TextInputLayout
    //private lateinit var inputUsernameRegis : TextInputLayout
    //private lateinit var inputEmail : TextInputLayout
    //private lateinit var inputPasswordRegis : TextInputLayout
    //private lateinit var inputConfirm : TextInputLayout
    //private lateinit var inputTL : TextInputLayout
    //private lateinit var inputTelepon : TextInputLayout
    //private lateinit var inputTextTLLangsung : TextInputEditText
    //private lateinit var regisLayout: ConstraintLayout
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_registration)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val cal = Calendar.getInstance()
        val myYear = cal.get(Calendar.YEAR)
        val myDay = cal.get(Calendar.DAY_OF_MONTH)
        val myMonth = cal.get(Calendar.MONTH)

        supportActionBar?.hide()
        //inputNama = findViewById(R.id.inputLayoutNama)
        //inputUsernameRegis = findViewById(R.id.inputLayoutUsernameRegis)
        //inputEmail =findViewById(R.id.inputLayoutEmail)
        //inputPasswordRegis = findViewById(R.id.inputLayoutPasswordRegis)
        //inputConfirm = findViewById(R.id.inputLayoutKonfirmPassword)
        //inputTL = findViewById(R.id.inputLayoutTanggalLahir)
        //inputTelepon = findViewById(R.id.inputLayoutTelepon)
        //regisLayout =findViewById(R.id.regisLayout)
        //inputTextTLLangsung = findViewById(R.id.inputTextTanggalLahirLangsung)



        binding.btnBack.setOnClickListener {
            val moveLogin = Intent(this@RegistrationActivity, MainActivity::class.java)
            startActivity(moveLogin)
        }

        binding.btnRegistration.setOnClickListener(View.OnClickListener {
            var checkRegis = false
            val nama : String = binding.inputLayoutNama.editText?.text.toString()
            val username: String = binding.inputLayoutUsernameRegis.editText?.text.toString()
            val email : String = binding.inputLayoutEmail.editText?.text.toString()
            val password: String = binding.inputLayoutPasswordRegis.editText?.text.toString()
            val confirm : String = binding.inputLayoutKonfirmPassword.editText?.text.toString()
            val tanggal : String = binding.inputLayoutTanggalLahir.editText?.text.toString()
            val telepon : String = binding.inputLayoutTelepon.editText?.text.toString()
            val mBundle = Bundle()

            if(nama.isEmpty()){
                binding.inputLayoutNama.error = "Nama tidak boleh kosong"
                checkRegis = false
            }

            if(username.isEmpty()){
                binding.inputLayoutUsernameRegis.error = "Username tidak boleh kosong"
                checkRegis = false
            }

            if(email.isEmpty()){
                binding.inputLayoutEmail.error = "Email tidak boleh kosong"
                checkRegis = false
            }

            if(password.isEmpty()){
                binding.inputLayoutPasswordRegis.error = "Password tidak boleh kosong"
                checkRegis = false
            }

            if(confirm.isEmpty()){
                binding.inputLayoutKonfirmPassword.error = "Password tidak boleh kosong"
                checkRegis = false
            }

            if(password.length!=8 || confirm.length!=8){
                binding.inputLayoutPasswordRegis.error = "Password harus 8 karakter!"
                binding.inputLayoutKonfirmPassword.error = "Password harus 8 karakter!"
                checkRegis = false
            }

            if(confirm!=password){
                binding.inputLayoutPasswordRegis.error = "Password tidak sama!"
                binding.inputLayoutKonfirmPassword.error = "Password tidak sama!"
                checkRegis = false
            }

            if(tanggal.isEmpty()){
                binding.inputLayoutTanggalLahir.error = "Tanggal lahir tidak boleh kosong"
                checkRegis = false
            }

            if(telepon.isEmpty()){
                binding.inputLayoutTelepon.error = "Nomor telepon tidak boleh kosong"
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

        binding.inputTextTanggalLahirLangsung.setOnFocusChangeListener { view, b ->
            val datePicker= DatePickerDialog(this,DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
                binding.inputTextTanggalLahirLangsung.setText("${dayOfMonth}/${(month.toInt()+1)}/${year}")

            }, myYear, myMonth, myDay)

            if(b){
                datePicker.show()
            }else{
                datePicker.hide()
            }
        }
    }
}