package com.samarth.ktornoteapp.data.remote

import com.samarth.ktornoteapp.data.remote.models.RemoteNote
import com.samarth.ktornoteapp.data.remote.models.SimpleResponse
import com.samarth.ktornoteapp.data.remote.models.User
import com.samarth.ktornoteapp.utils.Constants.API_VERSION
import retrofit2.http.*

interface NoteApi {



    @Headers("Content-Type: application/json")
    @POST("$API_VERSION/users/register")
    suspend fun createAccount(
        @Body user:User
    ): SimpleResponse




    @Headers("Content-Type: application/json")
    @POST("$API_VERSION/users/login")
    suspend fun login(
        @Body user:User
    ): SimpleResponse



    // ======== NOTES ============

    @Headers("Content-Type: application/json")
    @POST("$API_VERSION/notes/create")
    suspend fun createNote(
        @Header("Authorization") token:String,
        @Body note:RemoteNote
    ): SimpleResponse




    @Headers("Content-Type: application/json")
    @GET("$API_VERSION/notes")
    suspend fun getAllNote(
        @Header("Authorization") token:String
    ): List<RemoteNote>





    @Headers("Content-Type: application/json")
    @POST("$API_VERSION/notes/update")
    suspend fun updateNote(
        @Header("Authorization") token:String,
        @Body note:RemoteNote
    ): SimpleResponse


    @Headers("Content-Type: application/json")
    @DELETE("$API_VERSION/notes/delete")
    suspend fun deleteNote(
        @Header("Authorization") token:String,
        @Query("id") noteId:String
    ): SimpleResponse








}