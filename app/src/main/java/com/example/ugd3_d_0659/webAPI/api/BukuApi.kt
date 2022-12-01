package com.example.ugd3_d_0659.webAPI.api

class BukuApi {
    companion object {
        val BASE_URL = "http://192.168.100.115/api_tubes_pbp/public/api/"
//        val BASE_URL = "http://10.53.13.175/api_tubes_pbp/public/api/"

        val GET_ALL_URL = BASE_URL + "buku"
        val  GET_BY_ID_URL = BASE_URL + "buku/"
        val ADD_URL = BASE_URL + "buku"
        val UPDATE_URL = BASE_URL + "buku/"
        val DELETE_URL = BASE_URL + "buku/"
    }
}