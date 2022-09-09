package com.example.ugd3_d_0659

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_d_0659.entity.MataPelajaran
import java.text.FieldPosition

class RVMataPelajaranAdapter (private  val data: Array<MataPelajaran>) : RecyclerView.Adapter<RVMataPelajaranAdapter.viewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewTypes: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_mata_pelajaran, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvPelajaran.text = currentItem.mataPelajaran
        holder.tvPengajar.text = currentItem.pengajar
        holder.tvGambar.setImageResource(currentItem.gambar)
    }

    override fun getItemCount(): Int {
        //disini kita memberitahu jumlah dari item pda recycler view kita
        return data.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvPelajaran : TextView = itemView.findViewById(R.id.tv_nama_pelajaran)
        val tvPengajar : TextView = itemView.findViewById(R.id.tv_pengajar)
        val tvGambar : ImageView = itemView.findViewById(R.id.tv_gambar)
    }
}