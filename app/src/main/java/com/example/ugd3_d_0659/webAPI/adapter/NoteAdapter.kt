package com.example.ugd3_d_0659.webAPI.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_d_0659.EditNoteActivity
import com.example.ugd3_d_0659.MenuNote
import com.example.ugd3_d_0659.R
import com.example.ugd3_d_0659.webAPI.models.Note
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.collections.ArrayList

class NoteAdapter(private  var noteList: List<Note>, context: Context) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>(), Filterable {
    private  var filteredNoteList: MutableList<Note>
    private val context: Context

    init {
        filteredNoteList = ArrayList(noteList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, vieType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_note, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredNoteList.size
    }

    fun setNoteList(noteList: Array<Note>){
        this.noteList = noteList.toList()
        filteredNoteList = noteList.toMutableList()
    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        val note = filteredNoteList[position]
        holder.tvTitle.text = note.title
        holder.tvNote.text = note.note


        holder.btnDelete.setOnClickListener{
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfimasi")
                .setMessage("Apakah anda yakin ingin menghapus catatan ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Hapus") { _, _ ->
                    if (context is MenuNote) note.id?.let { it1 ->
                        context.deleteNote(it1)
                    }
                }
                .show()
        }

        holder.cvNote.setOnClickListener {
            val i = Intent(context, EditNoteActivity::class.java)
            i.putExtra("idNote", note.id)
            if(context is MenuNote)
                context.startActivityForResult(i, MenuNote.LAUNCH_ADD_ACTIVITY)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<Note> = java.util.ArrayList()
                if (charSequenceString.isEmpty()){
                    filtered.addAll(noteList)
                }else{
                    for (note in noteList){
                        if (note.title.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add(note)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredNoteList.clear()
                filteredNoteList.addAll((filterResults.values as List<Note>))
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var tvNote: TextView
        var btnDelete: ImageButton
        var cvNote: CardView

        init {
            tvTitle= itemView.findViewById(R.id.tv_title_note)
            tvNote=itemView.findViewById(R.id.tv_note)
            btnDelete=itemView.findViewById(R.id.btn_delete)
            cvNote=itemView.findViewById(R.id.cv_note)
        }
    }
}