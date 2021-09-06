package com.samarth.ktornoteapp.repository

import com.samarth.ktornoteapp.data.local.models.LocalNote
import com.samarth.ktornoteapp.data.remote.models.User
import com.samarth.ktornoteapp.utils.Result
import kotlinx.coroutines.flow.Flow

interface NoteRepo {

    suspend fun createUser(user:User):Result<String>
    suspend fun login(user:User):Result<String>
    suspend fun getUser():Result<User>
    suspend fun logout():Result<String>


    suspend fun createNote(note:LocalNote): Result<String>
    suspend fun updateNote(note:LocalNote): Result<String>
    fun getAllNotes():Flow<List<LocalNote>>
    suspend fun getAllNotesFromServer()
}