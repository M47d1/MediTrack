package com.example.meditrack.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("MediTrackPrefs", Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    companion object {
        private const val IS_LOGGED_IN = "isLoggedIn"
        private const val USER_TOKEN = "userToken"
    }

    fun setLogin(isLoggedIn: Boolean, token: String? = null) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean(IS_LOGGED_IN, false)

    // --- TAMBAHKAN DI SINI ---
    fun getToken(): String? = prefs.getString(USER_TOKEN, null)
    // -------------------------

    fun logout() {
        editor.clear()
        editor.apply()
    }
}