package com.example.ugd3_d_0659.room

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user : User)

    @Update
    suspend fun updateUser(user : User)

    @Delete
    suspend fun deleteUser(user : User)

    @Query("SELECT * FROM user")
    suspend fun getNotes(): List<User>

    @Query("SELECT * FROM user WHERE id =:user_id")
    suspend fun getNote(user_id: Int): List<User>

}