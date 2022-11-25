package com.example.ugd3_d_0659.webAPI.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_d_0659.EditNoteActivity
import com.example.ugd3_d_0659.MenuNote
import com.example.ugd3_d_0659.R
import com.example.ugd3_d_0659.webAPI.models.Note
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.barcodes.BarcodeQRCode
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.TextAlignment
import com.shashank.sony.fancytoastlib.FancyToast
import java.io.ByteArrayOutputStream

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
        holder.btnPrint.setOnClickListener {
            val title= holder.tvTitle.text.toString()
            val note = holder.tvNote.text.toString()
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    createPdf(title,note)
                }
            }catch (e: FileNotFoundException){
                e.printStackTrace()
            }
        }
    }
    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Throws(
        FileNotFoundException::class
    )
    private fun createPdf(title: String, note: String) {
        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        val file = File(pdfPath, "pdf_note.pdf")
        FileOutputStream(file)
        val writer = PdfWriter(file)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)

        pdfDocument.defaultPageSize = PageSize.A5
        document.setMargins(5f, 5f, 5f, 5f)
        @SuppressLint("UseCompatLoadingForDrawables")
        val d = context.getDrawable(R.drawable.logoproject_hitam)

        //penambahan gambar pada Gambar atas
        val bitmap = (d as BitmapDrawable?)!!.bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val bitmapdata = stream.toByteArray()
        val imageData = ImageDataFactory.create(bitmapdata)
        val image = Image(imageData).setHorizontalAlignment(HorizontalAlignment.CENTER)

        val namapengguna = Paragraph("Note").setBold().setFontSize(24f)
            .setTextAlignment(TextAlignment.CENTER)
        val group = Paragraph(
            """
                                Note yang di Print : 
                            """.trimIndent()).setTextAlignment(TextAlignment.CENTER).setFontSize(12f)
        val width = floatArrayOf(100f,200f)
        val table = Table(width)

        table.setHorizontalAlignment(HorizontalAlignment.CENTER)
        table.addCell(Cell().add(Paragraph("Title")))
        table.addCell(Cell().add(Paragraph(title)))
        table.addCell(Cell().add(Paragraph("Note")))
        table.addCell(Cell().add(Paragraph(note)))
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        table.addCell(Cell().add(Paragraph("Tanggal buat PDF")))
        table.addCell(Cell().add(Paragraph(LocalDate.now().format(dateTimeFormatter))))
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss a")
        table.addCell(Cell().add(Paragraph("Pukul Pembuatan")))
        table.addCell(Cell().add(Paragraph(LocalTime.now().format(timeFormatter))))
        val barcodeQRCode = BarcodeQRCode(
            """
                $title
                $note
                ${LocalDate.now().format(dateTimeFormatter)}
                ${LocalTime.now().format(timeFormatter)}
            """.trimIndent())
        val qrCodeObject = barcodeQRCode.createFormXObject(ColorConstants.BLACK, pdfDocument)
        val qrCodeImage = Image(qrCodeObject).setWidth(80f).setHorizontalAlignment(HorizontalAlignment.CENTER)

        document.add(image)
        document.add(namapengguna)
        document.add(group)
        document.add(table)
        document.add(qrCodeImage)

        document.close()
//        Toast.makeText(this, "Pdf Created", Toast.LENGTH_LONG).show()
        FancyToast.makeText(context.applicationContext,"Pdf Created", FancyToast.LENGTH_LONG, FancyToast.INFO,false).show();
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
        var btnPrint: ImageButton

        init {
            tvTitle= itemView.findViewById(R.id.tv_title_note)
            tvNote=itemView.findViewById(R.id.tv_note)
            btnDelete=itemView.findViewById(R.id.btn_delete)
            cvNote=itemView.findViewById(R.id.cv_note)
            btnPrint=itemView.findViewById(R.id.btn_print)
        }
    }
}