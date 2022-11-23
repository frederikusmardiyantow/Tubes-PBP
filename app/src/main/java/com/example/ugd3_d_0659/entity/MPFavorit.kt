package com.example.ugd3_d_0659.entity

import com.example.ugd3_d_0659.R

class MPFavorit (var mataPelajaran: String, var gambar:Int) {

    companion object{
        var listOfMPFavorit = arrayOf(
            MPFavorit("Bahasa Indonesia", R.drawable.gambar_bindonesia),
            MPFavorit("Pendidikan Jasmani", R.drawable.gambar_penjasorkes),
            MPFavorit("IPA",  R.drawable.gambar_ipa),
            MPFavorit("PKN", R.drawable.gambar_pkn)
        )
    }
}