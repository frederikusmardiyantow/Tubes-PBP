package com.example.ugd3_d_0659

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ugd3_d_0659.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences? = null
    private var binding:FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
        //inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)

        val namaUserLogin = sharedPreferences!!.getString("nama","")

        binding?.nama?.setText(namaUserLogin)
    }
}