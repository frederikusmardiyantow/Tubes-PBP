package com.example.ugd3_d_0659

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ugd3_d_0659.databinding.ActivityMenuBukuBinding
import com.example.ugd3_d_0659.webAPI.adapter.BukuAdapter
import com.example.ugd3_d_0659.webAPI.api.BukuApi
import com.example.ugd3_d_0659.webAPI.models.Buku
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class MenuBuku : AppCompatActivity() {
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null
    private var srBuku : SwipeRefreshLayout? = null
    private var svBuku : SearchView? = null
    private var btnCreate: Button? = null
    private var adapter : BukuAdapter? = null
    private lateinit var binding: ActivityMenuBukuBinding

    companion object{
        const val LAUNCH_ADD_ACTIVITY = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_menu_note)
        binding = ActivityMenuBukuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        queue = Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)
        srBuku = binding.srBuku
        svBuku = binding.svBuku

        srBuku?.setOnRefreshListener ( SwipeRefreshLayout.OnRefreshListener{ allBuku() } )
        svBuku?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                adapter!!.filter.filter(s)
                return false
            }
        })

        btnCreate=binding.btnCreateBuku

        btnCreate!!.setOnClickListener{
            val i = Intent(this@MenuBuku, EditBukuActivity::class.java)
            startActivityForResult(i, LAUNCH_ADD_ACTIVITY)
        }
        val rvBuku = findViewById<RecyclerView>(R.id.rv_buku)
        adapter = BukuAdapter(ArrayList(), this)
        rvBuku.layoutManager = LinearLayoutManager(this)
        rvBuku.adapter = adapter
        allBuku()
    }

    private fun allBuku() {
        srBuku!!.isRefreshing = true
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, BukuApi.GET_ALL_URL, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("data")
                var buku : Array<Buku> = gson.fromJson(jsonArray.toString(), Array<Buku>::class.java)

                adapter!!.setBukuList(buku)
                adapter!!.filter.filter(svBuku!!.query)
                srBuku!!.isRefreshing = false
                if (!buku.isEmpty())
                    FancyToast.makeText(this@MenuBuku,"Data Buku Berhasil Diambil",
                        FancyToast.LENGTH_LONG,
                        FancyToast.DEFAULT,false).show();
                else
                    FancyToast.makeText(this@MenuBuku,"Data Buku Kosong",
                        FancyToast.LENGTH_LONG,
                        FancyToast.WARNING,false).show();
            }, Response.ErrorListener { error ->
                srBuku!!.isRefreshing = false
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(this@MenuBuku,errors.getString("message"),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,true).show();
                }catch (e: Exception){
                    FancyToast.makeText(this@MenuBuku,e.message,
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,true).show();
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

    fun deleteBuku(id:Long){
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.DELETE, BukuApi.DELETE_URL + id, Response.Listener { response ->
                setLoading(false)
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONObject("data")
                var buku = gson.fromJson(jsonArray.toString(), Buku::class.java)

                if (buku != null)
                    FancyToast.makeText(this@MenuBuku,"Data Buku Berhasil Dihapus",
                        FancyToast.LENGTH_LONG,
                        FancyToast.SUCCESS,true).show();

                allBuku()
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(this@MenuBuku,errors.getString("message"),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,true).show();
                }catch (e: Exception){
                    FancyToast.makeText(this@MenuBuku,e.message,
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,true).show();
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

    private fun setLoading(isLoading: Boolean) {
        if (isLoading){
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading!!.visibility = View.VISIBLE
        }else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LAUNCH_ADD_ACTIVITY && resultCode == RESULT_OK)
            allBuku()
    }
}