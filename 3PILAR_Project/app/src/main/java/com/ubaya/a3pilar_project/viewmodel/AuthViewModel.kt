package com.ubaya.a3pilar_project.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ubaya.a3pilar_project.data.AppDatabase
import com.ubaya.a3pilar_project.model.User
import com.ubaya.a3pilar_project.repository.UserRepository
import kotlinx.coroutines.launch
import com.ubaya.a3pilar_project.ui.LoginFragment


class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getInstance(application).userDao()
    private val repository = UserRepository(userDao)

    val loginStatus = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun register(user: User, confirmPassword: String) {
        viewModelScope.launch {
            val existing = repository.getUser(user.username)
            if (existing != null) {
                errorMessage.postValue("Username sudah digunakan")
            } else if (user.password != confirmPassword) {
                errorMessage.postValue("Password tidak cocok")
            } else {
                repository.registerUser(user)
                loginStatus.postValue(true)
                saveSession(user.username)
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = repository.getUser(username)
            if (user != null && user.password == password) {
                loginStatus.postValue(true)
                saveSession(username)
            } else {
                errorMessage.postValue("Login gagal: Username atau password salah")
            }
        }
    }

    private fun saveSession(username: String) {
        val prefs = getApplication<Application>()
            .getSharedPreferences("session", Context.MODE_PRIVATE)
        prefs.edit().putString("username", username).apply()
    }
}
