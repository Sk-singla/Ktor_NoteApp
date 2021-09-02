package com.samarth.ktornoteapp.repository

import com.samarth.ktornoteapp.data.local.dao.NoteDao
import com.samarth.ktornoteapp.data.local.models.LocalNote
import com.samarth.ktornoteapp.data.remote.NoteApi
import com.samarth.ktornoteapp.data.remote.models.RemoteNote
import com.samarth.ktornoteapp.data.remote.models.User
import com.samarth.ktornoteapp.utils.Result
import com.samarth.ktornoteapp.utils.SessionManager
import com.samarth.ktornoteapp.utils.isNetworkConnected
import javax.inject.Inject

class NoteRepoImpl @Inject constructor(
    val noteApi: NoteApi,
    val noteDao: NoteDao,
    val sessionManager: SessionManager
):NoteRepo {


    override suspend fun createNote(note: LocalNote): Result<String> {
        return try {
            noteDao.insertNote(note)
            val token = sessionManager.getJwtToken()
            if(token == null){
                Result.Success("Note is Saved in Local Database!")
            }

            val result = noteApi.createNote(
                "Bearer $token",
                RemoteNote(
                    noteTitle = note.noteTitle,
                    description = note.desription,
                    date = note.date,
                    id = note.noteId
                )
            )

            if(result.success){
                noteDao.insertNote(note.also { it.connected = true })
                Result.Success("Note Saved Successfully!")
            } else {
                Result.Error(result.message)
            }
        } catch (e:Exception){
            e.printStackTrace()
            Result.Error(e.message ?: "Some Problem Occurred!")
        }

    }

    override suspend fun updateNote(note: LocalNote): Result<String> {
        return try {
            noteDao.insertNote(note)
            val token = sessionManager.getJwtToken()
            if(token == null){
                Result.Success("Note is Updated in Local Database!")
            }

            val result = noteApi.updateNote(
                "Bearer $token",
                RemoteNote(
                    noteTitle = note.noteTitle,
                    description = note.desription,
                    date = note.date,
                    id = note.noteId
                )
            )

            if(result.success){
                noteDao.insertNote(note.also { it.connected = true })
                Result.Success("Note Updated Successfully!")
            } else {
                Result.Error(result.message)
            }
        } catch (e:Exception){
            e.printStackTrace()
            Result.Error(e.message ?: "Some Problem Occurred!")
        }
    }

    override suspend fun createUser(user: User): Result<String> {

        return try {
            if(!isNetworkConnected(sessionManager.context)){
                Result.Error<String>("No Internet Connection!")
            }

            val result = noteApi.createAccount(user)
            if(result.success){
                sessionManager.updateSession(result.message,user.name ?:"",user.email)
                Result.Success("User Created Successfully!")
            } else {
                Result.Error<String>(result.message)
            }
        }catch (e:Exception) {
            e.printStackTrace()
            Result.Error<String>(e.message ?: "Some Problem Occurred!")
        }

    }

    override suspend fun login(user: User): Result<String> {
        return try {
            if(!isNetworkConnected(sessionManager.context)){
                Result.Error<String>("No Internet Connection!")
            }

            val result = noteApi.login(user)
            if(result.success){
                sessionManager.updateSession(result.message,user.name ?:"",user.email)
                Result.Success("Logged In Successfully!")
            } else {
                Result.Error<String>(result.message)
            }
        }catch (e:Exception) {
            e.printStackTrace()
            Result.Error<String>(e.message ?: "Some Problem Occurred!")
        }
    }

    override suspend fun getUser(): Result<User> {
        return try {
            val name = sessionManager.getCurrentUserName()
            val email = sessionManager.getCurrentUserEmail()
            if(name == null || email == null){
                Result.Error<User>("User not Logged In!")
            }
            Result.Success(User(name,email!!,""))
        } catch (e:Exception){
            e.printStackTrace()
            Result.Error(e.message ?: "Some Problem Occurred!")
        }
    }

    override suspend fun logout(): Result<String> {
        return try {
            sessionManager.logout()
            Result.Success("Logged Out Successfully!")
        } catch (e:Exception){
            e.printStackTrace()
            Result.Error(e.message ?: "Some Problem Occurred!")
        }
    }
}