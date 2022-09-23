package com.example.ugd3_d_0659.entity


class Peserta(var nama: String, var kelas: String) {

    companion object{
        var listOfPeserta = arrayOf(
            Peserta("Paijo Oh Paijo","XI"),
            Peserta("Sukirmin ni Sukirmin","X"),
            Peserta("Setia Banget Budi", "XI"),
            Peserta("Idola Setia Budi", "XII")
        )
    }}