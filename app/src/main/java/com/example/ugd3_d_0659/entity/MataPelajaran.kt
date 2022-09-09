package com.example.ugd3_d_0659.entity

import com.example.ugd3_d_0659.R

class MataPelajaran(var mataPelajaran: String, var pengajar: String, var gambar:Int) {

    companion object{
        var listOfMataPelajaran = arrayOf(
            MataPelajaran("Bahasa Indonesia", "Tania Syari", R.drawable.gambar_bindonesia),
            MataPelajaran("Pendidikan Jasmani", "Bagus Pandu", R.drawable.gambar_penjasorkes),
            MataPelajaran("IPA", "Sri Indahsah", R.drawable.gambar_ipa),
            MataPelajaran("PKN","Gading Nardi", R.drawable.gambar_pkn),
            MataPelajaran("Sejarah","Mulyani Haning",R.drawable.gambar_sejarah),
            MataPelajaran("Fisika","Paulus Rendra",R.drawable.gambar_fisika),
            MataPelajaran("Kimia","Mulyadi Yandi",R.drawable.gambar_kimia),
            MataPelajaran("Seni Budaya","Agung Candra",R.drawable.gambar_seni_budaya),
            MataPelajaran("Informatika","Daniel Nando",R.drawable.gambar_informatika),
            MataPelajaran("Bahasa Mandarin","Shen Lianda",R.drawable.gambar_mandarin)
        )
    }
}