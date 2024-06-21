package com.dicoding2.glucofy.data

import android.content.Context
import android.util.Log
import com.dicoding2.glucofy.data.local.entity.UserEntity

class UserPreference (context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(value: UserEntity) {
        val editor = preferences.edit()
        editor.putString(TOKEN, value.token)
        editor.putString(PHONENUMBER, value.phoneNumber)
        editor.putString(GENDER, value.gender)
        editor.putString(WEIGHT, value.weight)
        editor.putString(AGE, value.age)
        editor.putString(FIRSTNAME, value.firstName)
        editor.putString(LASTNAME, value.lastName)
        editor.putString(HEIGHT, value.height)
        editor.putString(EMAIL, value.email)
        editor.apply()
    }

    fun getUser(): UserEntity {
        val model = UserEntity()
        model.token = preferences.getString(TOKEN, "")
        model.phoneNumber = preferences.getString(PHONENUMBER, "")
        model.gender = preferences.getString(GENDER, "")
        model.weight = preferences.getString(WEIGHT, "")
        model.age = preferences.getString(AGE, "")
        model.firstName = preferences.getString(FIRSTNAME, "")
        model.lastName = preferences.getString(LASTNAME, "")
        model.height = preferences.getString(HEIGHT, "")
        model.email = preferences.getString(EMAIL, "")
        return model
    }

    fun deleteUser() {
        Log.d("getuser","${getUser()}")
        val editor = preferences.edit()
        editor.putString(TOKEN, "")
        editor.putString(PHONENUMBER, "")
        editor.putString(GENDER, "")
        editor.putString(WEIGHT, "")
        editor.putString(AGE, "")
        editor.putString(FIRSTNAME, "")
        editor.putString(LASTNAME, "")
        editor.putString(HEIGHT, "")
        editor.putString(EMAIL, "")
        editor.apply()
    }

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val TOKEN = "token"
        private const val PHONENUMBER = "phoneNumber"
        private const val GENDER = "gender"
        private const val WEIGHT = "weight"
        private const val AGE = "age"
        private const val FIRSTNAME = "firstName"
        private const val LASTNAME = "lastName"
        private const val HEIGHT = "height"
        private const val EMAIL = "email"
    }
}