package com.dicoding2.glucofy.data

import android.content.Context
import com.dicoding2.glucofy.data.local.entity.UserEntity

class UserPreference (context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(value: UserEntity) {
        val editor = preferences.edit()
        editor.putString(TOKEN, value.token)
        editor.apply()
    }

    fun getUser(): UserEntity {
        val model = UserEntity()
        model.token = preferences.getString(TOKEN, "")
        return model
    }

    fun deleteUser() {
        val editor = preferences.edit()
        editor.putString(TOKEN, "")
        editor.apply()
    }

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val TOKEN = "token"
    }
}