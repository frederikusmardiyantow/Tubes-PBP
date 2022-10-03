package com.example.ugd3_d_0659.view_binding_pelajaran

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_d_0659.databinding.RvItemPelajaranIndonesiaBinding

class PelajaranIndonesiaAdapter (val materiList: List<Materi>): RecyclerView.Adapter<PelajaranIndonesiaAdapter.MainViewHolder>() {
    inner class MainViewHolder (val itemBinding: RvItemPelajaranIndonesiaBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bindItem(task:Materi){
            itemBinding.itemImage.setImageResource(task.image)
            itemBinding.materiBerapa.text = task.materi
            itemBinding.namaMateri.text = task.nama_materi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MainViewHolder {
        return MainViewHolder(RvItemPelajaranIndonesiaBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val materi = materiList[position]
        holder.bindItem(materi)
    }

    override fun getItemCount(): Int {
        return materiList.size
    }

}