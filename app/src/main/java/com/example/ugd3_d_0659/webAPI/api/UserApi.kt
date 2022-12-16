package com.example.ugd3_d_0659.webAPI.api

class UserApi {
    companion object{
        val BASE_URL = "https://frederikus.com/tubes_pbp/public/api/"

        val GET_ALL_URL = BASE_URL + "user"
        val GET_BY_ID_URL = BASE_URL + "user/"
        val LOGIN = BASE_URL + "user/login"
        val ADD_URL = BASE_URL + "user"
        val UPDATE_URL = BASE_URL + "user/"
    }
}