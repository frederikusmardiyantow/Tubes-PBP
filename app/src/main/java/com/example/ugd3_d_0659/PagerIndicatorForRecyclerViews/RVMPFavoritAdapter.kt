package com.example.ugd3_d_0659.PagerIndicatorForRecyclerViews

import android.content.Context
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_d_0659.entity.MPFavorit
import com.example.ugd3_d_0659.R

class RVMPFavoritAdapter (private val context: Context) : RecyclerView.Adapter<RVMPFavoritAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewTypes: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_favorite_pelajaran, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(MPFavorit.listOfMPFavorit [position])
    }

    override fun getItemCount(): Int = PreferenceManager.getDefaultSharedPreferences(context)
        .getInt(PagerNumberPickerDialogPreference.KEY_NUM_PAGES, PagerNumberPickerDialogPreference.DEFAULT_PAGES)

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(mpFav: MPFavorit) {
            itemView.findViewById<TextView>(R.id.tv_nama_pelajaran_fav).setText(mpFav.mataPelajaran)
            itemView.findViewById<ImageView>(R.id.iv_gambar_mp_fav).setImageResource(mpFav.gambar)
        }
    }

}