package com.example.ugd3_d_0659.webAPI.api

class NoteApi {
    companion object {
        val BASE_URL = "http://10.53.9.236/api_tubes_pbp/public/api/"

        val GET_ALL_URL = BASE_URL + "note/"
        val  GET_BY_ID_URL = BASE_URL + "note/"
        val ADD_URL = BASE_URL + "note"
        val UPDATE_URL = BASE_URL + "note/"
        val DELETE_URL = BASE_URL + "note/"
    }
}