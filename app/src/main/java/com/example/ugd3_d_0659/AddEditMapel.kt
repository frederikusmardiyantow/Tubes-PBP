package com.example.ugd3_d_0659

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ugd3_d_0659.databinding.ActivityAddEditMapelBinding
import com.example.ugd3_d_0659.webAPI.api.MataPelajaranApi
import com.example.ugd3_d_0659.webAPI.models.MataPelajaran
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class AddEditMapel : AppCompatActivity() {
    private var etMapel: EditText? = null
    private var etPengajar: EditText? = null
    private var btnCancel: Button? = null
    private var btnSave: Button? = null
    //    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null
    private lateinit var binding: ActivityAddEditMapelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add_edit_mapel)
        binding = ActivityAddEditMapelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        queue = Volley.newRequestQueue(this)
        etMapel = binding.etMataPelajaranMapel
        etPengajar = binding.etPengajarMapel

//        layoutLoading = findViewById(R.id.layout_loading)

        btnCancel = binding.buttonCancelMapel
        btnCancel!!.setOnClickListener{ finish() }
        btnSave = binding.buttonSaveMapel

        val tvTitle = binding.tvTitleMapelActivity
        val id = intent.getLongExtra("idMapel", -1)
        if (id== -1L) {
            tvTitle.setText("Tambah Mata Pelajaran")
            btnSave!!.setOnClickListener {
                createMapel()
            }
        } else {
            tvTitle.setText("Ubah Mata Pelajaran")
            getMapelById(id)

            btnSave!!.setOnClickListener {
                updateMapel(id)
            }
        }
    }

    private fun updateMapel(id: Long) {
        val mapel = MataPelajaran(
            etMapel!!.text.toString(),
            etPengajar!!.text.toString()
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.PUT, MataPelajaranApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONObject("data")
                var mapel = gson.fromJson(jsonArray.toString(), MataPelajaran::class.java)

                if (mapel != null)
                    FancyToast.makeText(this@AddEditMapel,"Mata Pelajaran Berhasil Diupdate",
                        FancyToast.LENGTH_LONG,
                        FancyToast.SUCCESS,true).show();

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()

//                setLoading(false)
            }, Response.ErrorListener { error ->
//                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    if(error.networkResponse.statusCode == 422){
                        val jsonObject = JSONObject(responseBody)
                        val jsonArray = jsonObject.getJSONObject("errors")
                        for(i in jsonArray.keys()){
                            when(i){
                                "mataPelajaran"  -> etMapel!!.error = jsonArray.getJSONArray(i).getString(0)
                                "pengajar"  -> etPengajar!!.error = jsonArray.getJSONArray(i).getString(0)
                            }
                        }
                        FancyToast.makeText(this@AddEditMapel,jsonObject.getString("message"),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }else{
                        val errors = JSONObject(responseBody)
                        FancyToast.makeText(this@AddEditMapel, errors.getString("message") + "rwqrqw",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }
                }catch (e: Exception){
                    FancyToast.makeText(this@AddEditMapel,e.message,
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

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val gson = Gson()
                val requestBody = gson.toJson(mapel)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
    }

    private fun getMapelById(id: Long) {
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, MataPelajaranApi.GET_BY_ID_URL + id, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONObject("data")
                val mapel = gson.fromJson(jsonArray.toString(), MataPelajaran::class.java)

                etMapel!!.setText(mapel.mataPelajaran)
                etPengajar!!.setText(mapel.pengajar)


                FancyToast.makeText(this@AddEditMapel,"Mata Pelajaran Berhasil Diambil",
                    FancyToast.LENGTH_LONG,
                    FancyToast.DEFAULT,true).show();
//                setLoading(false)
            }, Response.ErrorListener { error ->
//                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(this@AddEditMapel,errors.getString("message"),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,true).show();
                }catch (e: Exception){
                    FancyToast.makeText(this@AddEditMapel,e.message,
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

    private fun createMapel() {
        val mapel = MataPelajaran(
            etMapel!!.text.toString(),
            etPengajar!!.text.toString(),
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.POST, MataPelajaranApi.ADD_URL, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONObject("data")
                var mapel = gson.fromJson(jsonArray.toString(), MataPelajaran::class.java)

                if (mapel != null)
                    FancyToast.makeText(this@AddEditMapel,"Mapel Berhasil Ditambahkan",
                        FancyToast.LENGTH_LONG,
                        FancyToast.SUCCESS,true).show();

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()

//                setLoading(false)
            }, Response.ErrorListener { error ->
//                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    if(error.networkResponse.statusCode == 422){
                        val jsonObject = JSONObject(responseBody)
                        val jsonArray = jsonObject.getJSONObject("errors")
                        for(i in jsonArray.keys()){
                            when(i){
                                "mataPelajaran"     -> etMapel!!.error = jsonArray.getJSONArray(i).getString(0)
                                "pengajar"  -> etPengajar!!.error = jsonArray.getJSONArray(i).getString(0)
                            }
                        }
                        FancyToast.makeText(this@AddEditMapel,jsonObject.getString("message"),
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,true).show();
                    }else{
                        val errors = JSONObject(responseBody)
                        FancyToast.makeText(this@AddEditMapel,errors.getString("message") + "rwqrqw",
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,true).show();
                    }
                }catch (e: Exception){
                    FancyToast.makeText(this@AddEditMapel,e.message,
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

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val gson = Gson()
                val requestBody = gson.toJson(mapel)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
    }
}