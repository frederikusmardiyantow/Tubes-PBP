package com.example.ugd3_d_0659.webAPI.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_d_0659.databinding.ItemUserBinding
import com.example.ugd3_d_0659.webAPI.AddEditUserActivity
import com.example.ugd3_d_0659.webAPI.ApiMainActivity
import com.example.ugd3_d_0659.webAPI.models.User
import java.util.*
import kotlin.collections.ArrayList

class UserAdapter(private var userList: List<User>, context: Context): RecyclerView.Adapter<UserAdapter.ViewHolder>(), Filterable {

    private var filteredUserList: MutableList<User>
    private val context: Context

    init {
        filteredUserList = ArrayList(userList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredUserList.size
    }

    fun setUserList(userList: Array<User>){
        this.userList = userList.toList()
        filteredUserList = userList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = filteredUserList[position]
        holder.tvNama.text = user.nama
        holder.tvUsername.text = user.username
        holder.tvEmail.text = user.email
        holder.tvTglLahir.text = user.tglLahir
        holder.tvTelp.text = user.telp
//
//        holder.btnDelete.setOnClickListener {
//            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
//            materialAlertDialogBuilder.setTitle("Konfirmasi")
//                .setMessage("Apakah anda yakin ingin menghapus user ini?")
//                .setNegativeButton("Batal", null)
//                .setPositiveButton("Hapus"){_,_ ->
//                    if (context is ApiMainActivity) user.id?.let { it1 ->
//                        context.deleteUser(
//                            it1
//                        )
//                    }
//                }
//                .show()
//
//        }

        holder.cvUser.setOnClickListener {
            val i = Intent(context, AddEditUserActivity::class.java)
            i.putExtra("id", user.id)
            if(context is ApiMainActivity)
                context.startActivityForResult(i, ApiMainActivity.LAUNCH_ADD_ACTIVITY)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<User> = java.util.ArrayList()
                if(charSequenceString.isEmpty()){
                    filtered.addAll(userList)
                }else{
                    for (user in userList){
                        if(user.nama.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))

                        )filtered.add(user)

                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults( CharSequence: CharSequence, filterResults: FilterResults) {
                filteredUserList.clear()
                filteredUserList.addAll(filterResults.values as List<User>)
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: ItemUserBinding) : RecyclerView.ViewHolder(itemView.root){
        var tvNama: TextView
        var tvUsername: TextView
        var tvEmail: TextView
        var tvTglLahir: TextView
        var tvTelp: TextView
        //var btnDelete: ImageButton
        var cvUser: CardView

        init {
            tvNama = itemView.tvNama
            tvUsername = itemView.tvUsername
            tvEmail = itemView.tvEmail
            tvTglLahir = itemView.tvTglLahir
            tvTelp =  itemView.tvTelp
            //btnDelete = itemView.btnDelete
            cvUser = itemView.cvUser
        }

    }

}