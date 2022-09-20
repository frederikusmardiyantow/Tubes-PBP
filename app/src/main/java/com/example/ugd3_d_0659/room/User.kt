package com.example.ugd3_d_0659.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val nama : String,
    val username : String,
    val email : String,
    val password : String,
    val tglLahir : String,
    val noTelp : String

)