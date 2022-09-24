package com.example.ugd3_d_0659

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ugd3_d_0659.room.User
import com.example.ugd3_d_0659.room.UserDB
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_profil.*

class FragmentProfil : Fragment() {
    private val myPreference = "myPref"
    private val id = "nameKey"
    var sharedPreferences: SharedPreferences? = null
    private lateinit var nama : TextView
    private lateinit var username : TextView
    private lateinit var email : TextView
    private lateinit var password : TextView
    private lateinit var tglLahir : TextView
    private lateinit var noTelp : TextView
    private lateinit var user : User
    val db by lazy { activity?.let { UserDB(it) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)

        val id = sharedPreferences!!.getInt(id,0)

        nama = view.findViewById(R.id.namaDiProfil)
        username = view.findViewById(R.id.usernameDiProfil)
        email = view.findViewById(R.id.emailDiProfil)
        password = view.findViewById(R.id.passwordDiProfil)
        tglLahir = view.findViewById(R.id.tanggalDiProfil)
        noTelp = view.findViewById(R.id.telpDiProfil)

        user = db?.userDao()?.getUser(id)?.get(0)!!

        nama.text = user.nama
        username.text = user.username
        email.text = user.email
        password.text = user.password
        tglLahir.text = user.tglLahir
        noTelp.text = user.noTelp

        btnEditDiProfil.setOnClickListener(View.OnClickListener{
            var mFragmentEditProfil = FragmentEditProfil()

            (activity as HomeActivity).changeFragment(mFragmentEditProfil)
        })
    }

}