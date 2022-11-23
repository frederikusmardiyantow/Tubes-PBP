package com.example.ugd3_d_0659

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ugd3_d_0659.camera.CameraActivity
import com.example.ugd3_d_0659.databinding.FragmentProfilBinding
import com.example.ugd3_d_0659.webAPI.models.User
import com.example.ugd3_d_0659.webAPI.AddEditUserActivity
import com.example.ugd3_d_0659.webAPI.api.UserApi
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_profil.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class FragmentProfil : Fragment() {
    private val myLoginPreference = "myLogin"
    private var idUser:Long = 0
    var sharedPreferencesLogin: SharedPreferences? = null
    private var queue: RequestQueue? = null
    private var binding: FragmentProfilBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfilBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
        //inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onStart() {
        super.onStart()
        getUserById(idUser)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferencesLogin = activity?.getSharedPreferences(myLoginPreference, Context.MODE_PRIVATE)

        queue = Volley.newRequestQueue(requireActivity())
        idUser = sharedPreferencesLogin!!.getLong("idUser",0)
        println("idUsernya ni: "+idUser)
        getUserById(idUser)

        btnEditDiProfil.setOnClickListener{
            val moveEdit = Intent(activity, AddEditUserActivity::class.java)
            moveEdit.putExtra("id", idUser)
            startActivity(moveEdit)
        }

        imageProfil.setOnClickListener {
            requireActivity().run{
                val intent = Intent(this, CameraActivity::class.java)
                startActivity(intent)
            }
        }

        btnLogout.setOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
            builder.setMessage("Apakah kamu yakin ingin LogOut?")
                .setPositiveButton("YES ", object : DialogInterface.OnClickListener {
                    override fun onClick(dialogInterface: DialogInterface, i: Int) {
                        //keluar dari aplikasi
                        activity?.finishAndRemoveTask()
                    }
                })
                .show()
        }
    }

    private fun getUserById(id: Long){
        val stringRequest: StringRequest = object :
            StringRequest(
                Method.GET, UserApi.GET_BY_ID_URL + id,
                { response ->
                    val jsonObject = JSONObject(response)
                    val jsonArray = jsonObject.getJSONObject("data")
                    val User = Gson().fromJson(jsonArray.toString(), User::class.java)

                    binding?.namaDiProfil?.setText(User.nama)
                    binding?.usernameDiProfil?.setText(User.username)
                    binding?.emailDiProfil?.setText(User.email)
                    binding?.passwordDiProfil?.setText(User.password)
                    binding?.tanggalDiProfil?.setText(User.tglLahir)
                    binding?.telpDiProfil?.setText(User.telp)

                    Toast.makeText(requireActivity(),"Data berhasil diambil", Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener{ error ->
                    try{
                        val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this.requireActivity(),
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception){
                        Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)

    }


}