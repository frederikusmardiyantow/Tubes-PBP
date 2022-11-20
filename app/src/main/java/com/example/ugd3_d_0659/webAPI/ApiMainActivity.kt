package com.example.ugd3_d_0659.webAPI

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ugd3_d_0659.R
import com.example.ugd3_d_0659.databinding.ActivityAddEditBinding
import com.example.ugd3_d_0659.databinding.ActivityApiMainBinding
import com.example.ugd3_d_0659.webAPI.adapter.UserAdapter
import com.example.ugd3_d_0659.webAPI.api.UserApi
import com.example.ugd3_d_0659.webAPI.models.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class ApiMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApiMainBinding
    private var srUser: SwipeRefreshLayout? = null
    private var adapter: UserAdapter? = null
    private var svUser: SearchView? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    companion object{
        const val LAUNCH_ADD_ACTIVITY = 123
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityApiMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        queue = Volley.newRequestQueue(this)

        layoutLoading = findViewById(R.id.layout_loading)
        srUser = binding.srUser
        svUser = binding.svUser

        srUser?.setOnRefreshListener (SwipeRefreshLayout.OnRefreshListener { allUser() })
        svUser?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter!!.filter.filter(p0)
                return false
            }
        })

        val fabAdd:FloatingActionButton = binding.fabAdd
        fabAdd.setOnClickListener {
            val i = Intent(this@ApiMainActivity,  AddEditActivity::class.java)
            startActivityForResult(i, LAUNCH_ADD_ACTIVITY)
        }

        val rvProduk:RecyclerView = binding.rvUser
        adapter = UserAdapter(ArrayList(), this)
        rvProduk.layoutManager = LinearLayoutManager(this)
        rvProduk.adapter = adapter
        allUser()
    }

    private fun allUser(){
        srUser!!.isRefreshing = true
        val stringRequest : StringRequest = object:
            StringRequest(Method.GET, UserApi.GET_ALL_URL, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("data")
                var user : Array<User> = gson.fromJson(jsonArray.toString(), Array<User>::class.java)

                adapter!!.setUserList(user)
                adapter!!.filter.filter(svUser!!.query)
                srUser!!.isRefreshing = false

                if(!user.isEmpty())
                    Toast.makeText(this@ApiMainActivity, "Data berhasil diambil", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this@ApiMainActivity, "Data Kosong!", Toast.LENGTH_SHORT).show()

            }, Response.ErrorListener { error ->
                srUser!!.isRefreshing = false
                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(this@ApiMainActivity, errors.getString("message"), Toast.LENGTH_SHORT).show()
                } catch (e: Exception){
                    Toast.makeText(this@ApiMainActivity, e.message, Toast.LENGTH_SHORT).show()
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

//    fun deleteUser(id: Long){
//        setLoading(true)
//        val stringRequest: StringRequest = object :
//            StringRequest(Method.DELETE, UserApi.DELETE_URL+id, Response.Listener { response ->
//                setLoading(false)
//
//                val gson = Gson()
//                var user = gson.fromJson(response, User::class.java)
//                if(user != null)
//                    Toast.makeText(this@ApiMainActivity, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
//
//                allUser()
//            }, Response.ErrorListener { error ->
//                setLoading(false)
//                try {
//                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
//                    val errors = JSONObject(responseBody)
//                    Toast.makeText(this@ApiMainActivity, errors.getString("message"), Toast.LENGTH_SHORT).show()
//                } catch (e: java.lang.Exception){
//                    Toast.makeText(this@ApiMainActivity, e.message, Toast.LENGTH_SHORT).show()
//                }
//            }){
//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): Map<String, String> {
//                val headers = java.util.HashMap<String, String>()
//                headers["Accept"] = "application/json"
//                return headers
//            }
//        }
//        queue!!.add(stringRequest)
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LAUNCH_ADD_ACTIVITY){
            if(resultCode == Activity.RESULT_OK){
                allUser()
            }
        }
    }

    private fun setLoading(isLoading: Boolean){
        if(isLoading){
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading!!.visibility = View.INVISIBLE
        }else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.visibility = View.INVISIBLE
        }
    }
}