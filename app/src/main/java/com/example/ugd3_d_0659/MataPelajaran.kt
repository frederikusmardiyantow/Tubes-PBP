package com.example.ugd3_d_0659

class MataPelajaran(var mataPelajaran: String, var pengajar: String, var materi:String) {

    companion object{
        var listOfMataPelajaran = arrayOf(
            MataPelajaran("Bahasa Indonesia", "Tania Syari", "Suku Kata"),
            MataPelajaran("Pendidikan Jasmani", "Bagus Pandu", "Lompat Jauh"),
            MataPelajaran("IPA", "Sri Indahsah", "Anatomi Tubuh"),
            MataPelajaran("PKN","Gading Nardi", "Dasar dasar Negara Indonesia"),
            MataPelajaran("Sejarah","Mulyani Haning","Penjajahan Jepang"),
            MataPelajaran("Fisika","Paulus Rendra","Gaya Tekan"),
            MataPelajaran("Kimia","Mulyadi Yandi","Tabel Kimia"),
            MataPelajaran("Seni Budaya","Agung Candra","Cetak Tanah Liat"),
            MataPelajaran("Informatika","Daniel Nando","Python"),
            MataPelajaran("Bahasa Mandarin","Shen Lianda","Menulis Hanzi")
        )
    }
}