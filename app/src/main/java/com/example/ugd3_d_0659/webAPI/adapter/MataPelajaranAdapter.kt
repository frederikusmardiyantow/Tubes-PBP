package com.example.ugd3_d_0659.webAPI.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_d_0659.*
import com.example.ugd3_d_0659.webAPI.models.MataPelajaran
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.collections.ArrayList

class MataPelajaranAdapter (private  var mapelList: List<MataPelajaran>, context: Context) :
    RecyclerView.Adapter<MataPelajaranAdapter.ViewHolder>(), Filterable {
    private  var filteredMapelList: MutableList<MataPelajaran>
    private val context: Context

    init {
        filteredMapelList = ArrayList(mapelList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, vieType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_mapel, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredMapelList.size
    }

    fun setMapelList(mapelList: Array<MataPelajaran>){
        this.mapelList = mapelList.toList()
        filteredMapelList = mapelList.toMutableList()
    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        val mapel = filteredMapelList[position]
        holder.tvMataPelajaran.text = mapel.mataPelajaran
        holder.tvPengajar.text = mapel.pengajar
        holder.image.setImageResource(R.drawable.logoproject)

        holder.btnDelete.setOnClickListener{
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfimasi")
                .setMessage("Apakah anda yakin ingin menghapus mata pelajaran ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Hapus") { _, _ ->
                    if (context is MenuMapel) mapel.id?.let { it1 ->
                        context.deleteMapel(it1)
                    }
                }
                .show()
        }
        holder.btnEdit.setOnClickListener {
            val i = Intent(context, AddEditMapel::class.java)
            i.putExtra("idMapel", mapel.id)
            if(context is MenuMapel)
                context.startActivityForResult(i, MenuMapel.LAUNCH_ADD_ACTIVITY)
        }
        holder.cvMapel.setOnClickListener (object :View.OnClickListener{
            override fun onClick(v: View?) {
                    val activity= v!!.context as AppCompatActivity
                    val fragmentKelasIndonesia=FragmentKelasIndonesia()
                    activity.supportFragmentManager.beginTransaction().replace(R.id.menu_mapel,fragmentKelasIndonesia).addToBackStack(null).commit()
            }
        })
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<MataPelajaran> = java.util.ArrayList()
                if (charSequenceString.isEmpty()){
                    filtered.addAll(mapelList)
                }else{
                    for (mapel in mapelList){
                        if (mapel.mataPelajaran.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add(mapel)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredMapelList.clear()
                filteredMapelList.addAll((filterResults.values as List<MataPelajaran>))
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvMataPelajaran: TextView
        var tvPengajar: TextView
        var btnEdit: ImageButton
        var btnDelete: ImageButton
        var image: ImageView
        var cvMapel: CardView

        init {
            tvMataPelajaran= itemView.findViewById(R.id.tv_nama_pelajaran_mapel)
            tvPengajar=itemView.findViewById(R.id.tv_pengajar_mapel)
            btnEdit= itemView.findViewById(R.id.btn_edit_mapel)
            btnDelete=itemView.findViewById(R.id.btn_delete_mapel)
            image=itemView.findViewById(R.id.tv_gambar_mapel)
            cvMapel=itemView.findViewById(R.id.cv_mapel)
        }
    }
}