package com.example.helloworld

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.content.SharedPreferences

class AuthViewModelFactory(private val sharedPrefs: SharedPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(sharedPrefs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}