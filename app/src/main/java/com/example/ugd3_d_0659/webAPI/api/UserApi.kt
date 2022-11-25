package com.example.ugd3_d_0659.webAPI.api

class UserApi {
    companion object{
//        val BASE_URL = "https://6374888108104a9c5f821f50.mockapi.io/api/"
//
//        val GET_ALL_URL = BASE_URL + "user/"
//        val GET_BY_ID_URL = BASE_URL + "user/"
//        val GET_ID_BY_USERNAME = BASE_URL + "user";
////        val GET_BY_USERNAME_URL = BASE_URL + "user?username="
//        val ADD_URL = BASE_URL + "user"
//        val UPDATE_URL = BASE_URL + "user/"
//        //val DELETE_URL = BASE_URL + "user/"ip

        val BASE_URL = "http://192.168.18.186/api_tubes_pbp/public/api/"

        val GET_ALL_URL = BASE_URL + "user"
        val GET_BY_ID_URL = BASE_URL + "user/"
        val LOGIN = BASE_URL + "login"
        //val GET_ID_BY_USERNAME = BASE_URL + "user";
//        val GET_BY_USERNAME_URL = BASE_URL + "user?username="
        val ADD_URL = BASE_URL + "user"
        val UPDATE_URL = BASE_URL + "user/"
        //val DELETE_URL = BASE_URL + "user/"
    }
}