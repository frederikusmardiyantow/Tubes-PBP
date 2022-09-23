package com.example.ugd3_d_0659

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.ugd3_d_0659.room.User
import com.example.ugd3_d_0659.room.UserDB
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class FragmentHome : Fragment() {
    private lateinit var nama : TextView
    private lateinit var user : User
    private val myPreference = "myPref"
    private val id = "nameKey"
    var sharedPreferences: SharedPreferences? = null
    val db by lazy { activity?.let { UserDB(it) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)

        val id = sharedPreferences!!.getInt(id,0)

        nama = view.findViewById(R.id.nama)

        user = db?.userDao()?.getUser(id)?.get(0)!!

        nama.text = user.nama


    }


}