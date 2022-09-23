package com.example.ugd3_d_0659.room

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun addUser(user : User)

    @Update
    fun updateUser(user : User)

    @Delete
    fun deleteUser(user : User)

    @Query("SELECT * FROM user")
    fun getUsers(): List<User>

    @Query("SELECT * FROM user WHERE id =:user_id")
    fun getUser(user_id: Int): List<User>

    @Query("SELECT * FROM user WHERE username =:username AND password =:password")
    fun getUserLogin(username: String, password: String): User?

    @Query("SELECT * FROM user WHERE username =:username")
    fun getUsername(username: String): User?


}