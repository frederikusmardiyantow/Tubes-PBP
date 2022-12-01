package com.example.ugd3_d_0659.webAPI.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_d_0659.*
import com.example.ugd3_d_0659.webAPI.models.Buku
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.collections.ArrayList

class BukuAdapter (private  var bukuList: List<Buku>, context: Context) :
    RecyclerView.Adapter<BukuAdapter.ViewHolder>(), Filterable {
        private  var filteredBukuList: MutableList<Buku>
        private val context: Context

        init {
            filteredBukuList = ArrayList(bukuList)
            this.context = context
        }

        override fun onCreateViewHolder(parent: ViewGroup, vieType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_buku, parent, false)

            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return filteredBukuList.size
        }

        fun setBukuList(bukuList: Array<Buku>){
            this.bukuList = bukuList.toList()
            filteredBukuList = bukuList.toMutableList()
        }

        override fun onBindViewHolder(holder : ViewHolder, position: Int) {
            val buku = filteredBukuList[position]
            holder.tvTitleBuku.text = buku.judul
            holder.tvPengarangBuku.text = buku.pengarang
            holder.image.setImageResource(R.drawable.book_ilustrasi)

            holder.btnDelete.setOnClickListener{
                val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
                materialAlertDialogBuilder.setTitle("Konfimasi")
                    .setMessage("Apakah anda yakin ingin menghapus buku ini?")
                    .setNegativeButton("Batal", null)
                    .setPositiveButton("Hapus") { _, _ ->
                        if (context is MenuBuku) buku.id?.let { it1 ->
                            context.deleteBuku(it1)
                        }
                    }
                    .show()
            }

            holder.cvBuku.setOnClickListener {
                val i = Intent(context, EditBukuActivity::class.java)
                i.putExtra("idBuku", buku.id)
                if(context is MenuBuku)
                    context.startActivityForResult(i, MenuBuku.LAUNCH_ADD_ACTIVITY)
            }
        }

        override fun getFilter(): Filter {
            return object : Filter() {
                override fun performFiltering(charSequence: CharSequence): FilterResults {
                    val charSequenceString = charSequence.toString()
                    val filtered: MutableList<Buku> = java.util.ArrayList()
                    if (charSequenceString.isEmpty()){
                        filtered.addAll(bukuList)
                    }else{
                        for (buku in bukuList){
                            if (buku.judul.lowercase(Locale.getDefault())
                                    .contains(charSequenceString.lowercase(Locale.getDefault()))
                            ) filtered.add(buku)
                        }
                    }
                    val filterResults = FilterResults()
                    filterResults.values = filtered
                    return filterResults
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                    filteredBukuList.clear()
                    filteredBukuList.addAll((filterResults.values as List<Buku>))
                    notifyDataSetChanged()
                }
            }
        }

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            var tvTitleBuku: TextView
            var tvPengarangBuku: TextView
            var btnDelete: ImageButton
            var image: ImageView
            var cvBuku: CardView

            init {
                tvTitleBuku= itemView.findViewById(R.id.tv_title_buku)
                tvPengarangBuku=itemView.findViewById(R.id.tv_pengarang_buku)
                btnDelete=itemView.findViewById(R.id.btn_delete)
                image=itemView.findViewById(R.id.imageBuku)
                cvBuku=itemView.findViewById(R.id.cv_buku)
            }
        }

}