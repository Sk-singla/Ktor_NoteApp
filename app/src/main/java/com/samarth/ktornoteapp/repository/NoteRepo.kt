package com.samarth.ktornoteapp.repository

import com.samarth.ktornoteapp.data.remote.models.User
import com.samarth.ktornoteapp.utils.Result

interface NoteRepo {

    suspend fun createUser(user:User):Result<String>
    suspend fun login(user:User):Result<String>
    suspend fun getUser():Result<User>
    suspend fun logout():Result<String>

}