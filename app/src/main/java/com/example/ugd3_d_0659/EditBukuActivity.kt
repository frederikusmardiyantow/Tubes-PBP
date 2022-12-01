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
import com.example.ugd3_d_0659.databinding.ActivityEditBukuBinding
import com.example.ugd3_d_0659.webAPI.api.BukuApi
import com.example.ugd3_d_0659.webAPI.models.Buku
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.fragment_profil.view.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class EditBukuActivity : AppCompatActivity() {
    private var etJudulBuku: EditText? = null
    private var etPenerbit: EditText? = null
    private var etTahunTerbit: EditText? = null
    private var etPengarang: EditText? = null
    private var etJumlahHalaman: EditText? = null
    private var etISBN: EditText? = null
    private var btnCancel: Button? = null
    private var btnSave: Button? = null
//    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null
    private lateinit var binding: ActivityEditBukuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_edit_note)
        binding = ActivityEditBukuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        queue = Volley.newRequestQueue(this)
        etJudulBuku = binding.etJudulBuku
        etPenerbit = binding.etPenerbitBuku
        etTahunTerbit = binding.etTahunTebitBuku
        etPengarang = binding.etPengarangBuku
        etJumlahHalaman = binding.etJumlahHalamanBuku
        etISBN = binding.etIsbnBuku

//        layoutLoading = findViewById(R.id.layout_loading)

        btnCancel = binding.btnCancel
        btnCancel!!.setOnClickListener{ finish() }
        btnSave = binding.btnSave

        val tvTitle = binding.tvTitleBukuActivity
        val id = intent.getLongExtra("idBuku", -1)
        if (id== -1L) {
            tvTitle.setText("Tambah Buku")
            btnSave!!.setOnClickListener {
                createBuku()
            }
        } else {
            tvTitle.setText("Ubah Buku")
            getBukuById(id)

            btnSave!!.setOnClickListener {
                updateBuku(id)
            }
        }
    }

    private fun updateBuku(id: Long) {
//        setLoading(true)

        val buku = Buku(
            etJudulBuku!!.text.toString(),
            etPenerbit!!.text.toString(),
            etTahunTerbit!!.text.toString(),
            etPengarang!!.text.toString(),
            etJumlahHalaman!!.text.toString(),
            etISBN!!.text.toString()
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.PUT, BukuApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONObject("data")
                var buku = gson.fromJson(jsonArray.toString(), Buku::class.java)

                if (buku != null)
                    FancyToast.makeText(this@EditBukuActivity,"Buku Berhasil Diupdate",
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
                                "judul"     -> etJudulBuku!!.error = jsonArray.getJSONArray(i).getString(0)
                                "penerbit"  -> etPenerbit!!.error = jsonArray.getJSONArray(i).getString(0)
                                "tahunTerbit"-> etTahunTerbit!!.error = jsonArray.getJSONArray(i).getString(0)
                                "pengarang" -> etPengarang!!.error = jsonArray.getJSONArray(i).getString(0)
                                "jumlahHalaman"-> etJumlahHalaman!!.error = jsonArray.getJSONArray(i).getString(0)
                                "isbn"      -> etISBN!!.error = jsonArray.getJSONArray(i).getString(0)
                            }
                        }
                        FancyToast.makeText(this@EditBukuActivity,jsonObject.getString("message"),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }else{
                        val errors = JSONObject(responseBody)
                        FancyToast.makeText(this@EditBukuActivity,errors.getString("message") + "rwqrqw",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }
                }catch (e: Exception){
                    FancyToast.makeText(this@EditBukuActivity,e.message,
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
                val requestBody = gson.toJson(buku)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
    }

    private fun createBuku() {
//        setLoading(true)

        val buku = Buku(
            etJudulBuku!!.text.toString(),
            etPenerbit!!.text.toString(),
            etTahunTerbit!!.text.toString(),
            etPengarang!!.text.toString(),
            etJumlahHalaman!!.text.toString(),
            etISBN!!.text.toString()
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.POST, BukuApi.ADD_URL, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONObject("data")
                var buku = gson.fromJson(jsonArray.toString(), Buku::class.java)

                if (buku != null)
                    FancyToast.makeText(this@EditBukuActivity,"Buku Berhasil Ditambahkan",
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
                                "judul"     -> etJudulBuku!!.error = jsonArray.getJSONArray(i).getString(0)
                                "penerbit"  -> etPenerbit!!.error = jsonArray.getJSONArray(i).getString(0)
                                "tahunTerbit"-> etTahunTerbit!!.error = jsonArray.getJSONArray(i).getString(0)
                                "pengarang" -> etPengarang!!.error = jsonArray.getJSONArray(i).getString(0)
                                "jumlahHalaman"-> etJumlahHalaman!!.error = jsonArray.getJSONArray(i).getString(0)
                                "isbn"      -> etISBN!!.error = jsonArray.getJSONArray(i).getString(0)
                            }
                        }
                        FancyToast.makeText(this@EditBukuActivity,jsonObject.getString("message"),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }else{
                        val errors = JSONObject(responseBody)
                        FancyToast.makeText(this@EditBukuActivity,errors.getString("message") + "rwqrqw",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }
                }catch (e: Exception){
                    FancyToast.makeText(this@EditBukuActivity,e.message,
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
                val requestBody = gson.toJson(buku)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
    }

    private fun getBukuById(id: Long) {
//        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, BukuApi.GET_BY_ID_URL + id, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONObject("data")
                val buku = gson.fromJson(jsonArray.toString(), Buku::class.java)

                etJudulBuku!!.setText(buku.judul)
                etPenerbit!!.setText(buku.penerbit)
                etTahunTerbit!!.setText(buku.tahunTerbit)
                etPengarang!!.setText(buku.pengarang)
                etJumlahHalaman!!.setText(buku.jumlahHalaman)
                etISBN!!.setText(buku.isbn)

                FancyToast.makeText(this@EditBukuActivity,"Buku Berhasil Diambil",
                    FancyToast.LENGTH_LONG,
                    FancyToast.DEFAULT,true).show();
//                setLoading(false)
            }, Response.ErrorListener { error ->
//                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(this@EditBukuActivity,errors.getString("message"),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,true).show();
                }catch (e: Exception){
                    FancyToast.makeText(this@EditBukuActivity,e.message,
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

//    private fun setLoading(isLoading: Boolean) {
//        if (isLoading){
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
//            )
//            layoutLoading!!.visibility = View.VISIBLE
//        }else{
//            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//            layoutLoading!!.visibility = View.INVISIBLE
//        }
//    }
}