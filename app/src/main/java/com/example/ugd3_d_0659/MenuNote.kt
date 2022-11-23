package com.example.ugd3_d_0659

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ugd3_d_0659.webAPI.adapter.NoteAdapter
import com.example.ugd3_d_0659.webAPI.api.NoteApi
import com.example.ugd3_d_0659.webAPI.models.Note
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_menu_note.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class MenuNote : AppCompatActivity() {
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null
    private var srNote : SwipeRefreshLayout? = null
    private var svNote : SearchView? = null
    private var adapter : NoteAdapter? = null
    companion object{
        const val LAUNCH_ADD_ACTIVITY = 123
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_note)
        queue = Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)
        srNote = findViewById(R.id.sr_note)
        svNote = findViewById(R.id.sv_note)

        srNote?.setOnRefreshListener ( SwipeRefreshLayout.OnRefreshListener{ allNote() } )
        svNote?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                adapter!!.filter.filter(s)
                return false
            }
        })

        val btnCreate = findViewById<Button>(R.id.button_create)
        btnCreate.setOnClickListener{
            val i = Intent(this@MenuNote, EditNoteActivity::class.java)
            startActivityForResult(i, LAUNCH_ADD_ACTIVITY)
        }
        val rvProduk = findViewById<RecyclerView>(R.id.rv_note)
        adapter = NoteAdapter(ArrayList(), this)
        rvProduk.layoutManager = LinearLayoutManager(this)
        rvProduk.adapter = adapter
        allNote()
    }

    private fun allNote() {
        srNote!!.isRefreshing = true
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, NoteApi.GET_ALL_URL, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("data")
                var note : Array<Note> = gson.fromJson(jsonArray.toString(), Array<Note>::class.java)

                adapter!!.setNoteList(note)
                adapter!!.filter.filter(svNote!!.query)
                srNote!!.isRefreshing = false
                if (!note.isEmpty())
                    FancyToast.makeText(this@MenuNote,"Data Berhasil Diambil",FancyToast.LENGTH_LONG,FancyToast.DEFAULT,false).show();
//                    Toast.makeText(this@MenuNote, "Data Berhasil Diambil", Toast.LENGTH_SHORT).show()
                else
                    FancyToast.makeText(this@MenuNote,"Data Kosong",FancyToast.LENGTH_LONG,FancyToast.WARNING,false).show();
//                    Toast.makeText(this@MenuNote, "Data Kosong", Toast.LENGTH_SHORT).show()
            }, Response.ErrorListener { error ->
                srNote!!.isRefreshing = false
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(this@MenuNote,errors.getString("message"),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
//                    Toast.makeText(this@MenuNote, errors.getString("message"), Toast.LENGTH_SHORT
//                    ).show()
                }catch (e: Exception){
                    FancyToast.makeText(this@MenuNote,e.message,FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
//                    Toast.makeText(this@MenuNote, e.message, Toast.LENGTH_SHORT).show()
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

    fun deleteNote(id:Long){
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.DELETE, NoteApi.DELETE_URL + id, Response.Listener { response ->
                setLoading(false)
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONObject("data")
                var note = gson.fromJson(jsonArray.toString(), Note::class.java)

                if (note != null)
                    FancyToast.makeText(this@MenuNote,"Data Berhasil Dihapus",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
//                    Toast.makeText(this@MenuNote, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()

                allNote()
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(this@MenuNote,errors.getString("message"),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
//                    Toast.makeText(this@MenuNote, errors.getString("message"), Toast.LENGTH_SHORT
//                    ).show()
                }catch (e: Exception){
                    FancyToast.makeText(this@MenuNote,e.message,FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
//                    Toast.makeText(this@MenuNote, e.message, Toast.LENGTH_SHORT).show()
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
            allNote()
    }
}

