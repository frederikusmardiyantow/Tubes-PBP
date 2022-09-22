package com.example.ugd3_d_0659

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_d_0659.room.User
import java.sql.Types

abstract class RVProfilAdapter (private val data: Array<User>):RecyclerView.Adapter<RVProfilAdapter.viewHolder>() {



    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvNama.text = currentItem.nama
        holder.tvEmail.text = currentItem.email
        holder.tvUser.text = currentItem.username
        holder.tvNomorPhone.text = currentItem.noTelp
    }


    class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvNama : TextView = itemView.findViewById(R.id.tv_nama)
        val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
        val tvUser: TextView = itemView.findViewById(R.id.tv_user)
        val tvNomorPhone: TextView = itemView.findViewById(R.id.tv_nomorPhone)
        val tvGambarProfil: ImageView = itemView.findViewById(R.id.tv_gambarProfil)
    }


}