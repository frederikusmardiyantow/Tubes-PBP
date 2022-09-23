package com.example.ugd3_d_0659

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_d_0659.entity.Peserta

class RVPesertaAdapter (private  val data: Array<Peserta>) : RecyclerView.Adapter<RVPesertaAdapter.viewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewTypes: Int): RVPesertaAdapter.viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_peserta, parent, false)
        return RVPesertaAdapter.viewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvNama.text = currentItem.nama
        holder.tvEmail.text = currentItem.kelas
    }


    class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvNama : TextView = itemView.findViewById(R.id.tv_nama)
        val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
    }


}