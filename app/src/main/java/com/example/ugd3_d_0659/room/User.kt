package com.example.ugd3_d_0659.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var nama : String,
    var username : String,
    var email : String,
    var password : String,
    var tglLahir : String,
    var noTelp : String

)