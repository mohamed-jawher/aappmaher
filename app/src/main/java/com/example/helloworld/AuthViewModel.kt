package com.example.helloworld

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel

data class User(val username: String, val email: String, val password: String, val phone: String)

class AuthViewModel(private val sharedPrefs: SharedPreferences) : ViewModel() {

    // Check if user is logged in
    fun isLoggedIn(): Boolean {
        return sharedPrefs.getBoolean("is_logged_in", false)
    }

    // Login method that validates credentials and updates login state
    fun login(email: String, password: String): Boolean {
        // Get the total number of registered users
        val userCount = sharedPrefs.getInt("user_count", 0)

        // Check against registered users
        for (i in 0 until userCount) {

            val storedEmail = sharedPrefs.getString("user_email_$i", null)
            val storedPassword = sharedPrefs.getString("user_password_$i", null)
            println("émail: $storedEmail")
            println("password: $storedPassword")
            if (storedEmail == email && storedPassword == password) {
                with(sharedPrefs.edit()) {
                    putBoolean("is_logged_in", true)
                    putString("email", email)
                    putString("username", sharedPrefs.getString("user_username_$i", null))
                    apply()
                }
                return true
            }
        }
        return false
    }
    fun getUsername(): String {
        return sharedPrefs.getString("username", "") ?: ""
    }

    // Register method that saves new user credentials
    fun register(username: String, email: String, password: String, phone: String) {
        // Your registration logic to store the user details
        val userCount = sharedPrefs.getInt("user_count", 0)

        with(sharedPrefs.edit()) {
            putString("user_email_$userCount", email)
            putString("user_password_$userCount", password)
            putString("user_username_$userCount", username) // Optional: store username
            putString("user_phone_$userCount", phone) // Optional: store phone
            putInt("user_count", userCount + 1)
            apply()
        }
    }

    // Logout method that clears stored data
    fun logout() {
        with(sharedPrefs.edit()) {
            putBoolean("is_logged_in", false)
            apply()
        }
    }

    // Optional: Method to get the email of the logged-in user
    fun getLoggedInUserEmail(): String? {
        return if (isLoggedIn()) {
            sharedPrefs.getString("email", null)
        } else {
            null
        }
    }
}
