package com.example.ugd3_d_0659

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.ugd3_d_0659.room.User
import com.example.ugd3_d_0659.room.UserDB
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_edit_profil.*
import kotlinx.android.synthetic.main.fragment_profil.*
import java.util.*

class FragmentEditProfil : Fragment() {
//    private val myPreference = "myPref"
//    private val id = "nameKey"
//    var sharedPreferences: SharedPreferences? = null
//    private lateinit var nama : TextInputLayout
//    private lateinit var username : TextInputLayout
//    private lateinit var email : TextInputLayout
//    private lateinit var tglLahir : TextInputLayout
//    private lateinit var telepon : TextInputLayout
//    private lateinit var user : User
//    private lateinit var rootView: View
//    val db by lazy { activity?.let { UserDB(it) } }
//
//    val cal = Calendar.getInstance()
//    val myYear = cal.get(Calendar.YEAR)
//    val myDay = cal.get(Calendar.DAY_OF_MONTH)
//    val myMonth = cal.get(Calendar.MONTH)
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        rootView = inflater.inflate(R.layout.fragment_edit_profil, container, false)
//        return rootView
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        var mFragmentProfil = FragmentProfil()
//
//        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)
//
//        val id = sharedPreferences!!.getInt(id,0)
//
//        nama = view.findViewById(R.id.inputLayoutEditNama)
//        username = view.findViewById(R.id.inputLayoutEditUsernameRegis)
//        email = view.findViewById(R.id.inputLayoutEditEmail)
//        tglLahir = view.findViewById(R.id.inputLayoutEditTanggalLahir)
//        telepon = view.findViewById(R.id.inputLayoutEditTelepon)
//
//        user = db?.userDao()?.getUser(id)?.get(0)!!
//
//        nama.getEditText()?.setText(user.nama)
//        username.getEditText()?.setText(user.username)
//        email.getEditText()?.setText(user.email)
//        tglLahir.getEditText()?.setText(user.tglLahir)
//        telepon.getEditText()?.setText(user.noTelp)
//
//        btnBack.setOnClickListener(View.OnClickListener{
//            (activity as HomeActivity).changeFragment(mFragmentProfil)
//        })
//
//        btnEdit.setOnClickListener(View.OnClickListener{
//
//            db!!.userDao().updateUser(
//                User(user.id,
//                    nama.getEditText()?.getText().toString(),
//                    username.getEditText()?.getText().toString(),
//                    email.getEditText()?.getText().toString(),
//                    user.password,
//                    tglLahir.getEditText()?.getText().toString(),
//                    telepon.getEditText()?.getText().toString())
//            )
//            Snackbar.make(rootView,"Success your Edit Profile.. Congrats!", Snackbar.LENGTH_LONG).show()
//            (activity as HomeActivity).changeFragment(mFragmentProfil)
//        })
//
//        inputTextEditTanggalLahirLangsung.setOnFocusChangeListener { view, b ->
//            val datePicker =
//                activity?.let { it1 ->
//                    DatePickerDialog(it1, DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
//                        inputTextEditTanggalLahirLangsung.setText("${dayOfMonth}/${(month.toInt() + 1).toString()}/${year}")
//                    },myYear,myMonth,myDay)
//                }
//            if(b){
//                datePicker?.show()
//            }else{
//                datePicker?.hide()
//            }
//        }
//
//    }
}