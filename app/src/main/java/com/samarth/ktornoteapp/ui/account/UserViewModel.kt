package com.samarth.ktornoteapp.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samarth.ktornoteapp.data.remote.models.User
import com.samarth.ktornoteapp.repository.NoteRepo
import com.samarth.ktornoteapp.utils.Constants.MAXIMUM_PASSWORD_LENGTH
import com.samarth.ktornoteapp.utils.Constants.MINIMUM_PASSWORD_LENGTH
import com.samarth.ktornoteapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val noteRepo: NoteRepo
): ViewModel() {

    private val _registerState = MutableSharedFlow<Result<String>>()
    val registerState:SharedFlow<Result<String>> = _registerState

    private val _loginState = MutableSharedFlow<Result<String>>()
    val loginState:SharedFlow<Result<String>> = _loginState

    private val _currentUserState = MutableSharedFlow<Result<User>>()
    val currentUserState:SharedFlow<Result<User>> = _currentUserState

    fun createUser(
        name:String,
        email:String,
        password:String,
        confirmPassword:String
    ) = viewModelScope.launch {
        _registerState.emit(Result.Loading())

        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || password != confirmPassword){
            _registerState.emit(Result.Error("Some Fields are empty"))
            return@launch
        }

        if(!isEmailValid(email)){
            _registerState.emit(Result.Error("Email is not Valid!"))
            return@launch
        }

        if(!isPasswordValid(password)){
            _registerState.emit(Result.Error("Password should be between $MINIMUM_PASSWORD_LENGTH and $MAXIMUM_PASSWORD_LENGTH"))
            return@launch
        }

        val newUser = User(
            name,
            email,
            password
        )
        _registerState.emit(noteRepo.createUser(newUser))
    }

    fun loginUser(
        name:String,
        email:String,
        password:String
    ) = viewModelScope.launch {
        _loginState.emit(Result.Loading())

        if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
            _loginState.emit(Result.Error("Some Fields are empty"))
            return@launch
        }

        if(!isEmailValid(email)){
            _loginState.emit(Result.Error("Email is not Valid!"))
            return@launch
        }

        if(!isPasswordValid(password)){
            _loginState.emit(Result.Error("Password should be between $MINIMUM_PASSWORD_LENGTH and $MAXIMUM_PASSWORD_LENGTH"))
            return@launch
        }

        val newUser = User(
            name,
            email,
            password
        )
        _loginState.emit(noteRepo.login(newUser))
    }


    fun getCurrentUser()  = viewModelScope.launch{
        _currentUserState.emit(Result.Loading())
        _currentUserState.emit(noteRepo.getUser())
    }

    fun logout() = viewModelScope.launch {
        val result = noteRepo.logout()
        if(result is Result.Success){
            getCurrentUser()
        }
    }


    private fun isEmailValid(email: String):Boolean {
        var regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        val pattern = Pattern.compile(regex)
        return (email.isNotEmpty() && pattern.matcher(email).matches())
    }

    private fun isPasswordValid(password: String):Boolean {

        return (password.length in MINIMUM_PASSWORD_LENGTH..MAXIMUM_PASSWORD_LENGTH)

    }

}