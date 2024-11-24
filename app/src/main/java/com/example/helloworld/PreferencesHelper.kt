
package com.example.helloworld

import android.content.Context

class PreferencesHelper(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveLoginState(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("is_logged_in", false)
    }

    fun logout() {
        saveLoginState(false)
    }
}