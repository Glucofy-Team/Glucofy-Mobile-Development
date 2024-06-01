package com.dicoding2.glucofy.data

import android.content.Context
import com.dicoding2.glucofy.data.local.entity.RegisterEntity
import com.dicoding2.glucofy.data.local.entity.UserEntity

class RegisterPreference (context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUserRegister(value: RegisterEntity) {
        val editor = preferences.edit()
        editor.putString(FIRSTNAME, value.first_name)
        editor.putString(LASTNAME, value.last_name)
        editor.putString(PHONENUMBER, value.phone_number)
        editor.putString(EMAIL, value.email)
        editor.putString(PASSWORD, value.password)

        editor.apply()
    }

    fun getUserRegister(): RegisterEntity {
        val model = RegisterEntity()
        model.first_name = preferences.getString(FIRSTNAME, "")
        model.last_name = preferences.getString(LASTNAME    , "")
        model.phone_number = preferences.getString(PHONENUMBER, "")
        model.email = preferences.getString(EMAIL, "")
        model.password = preferences.getString(PASSWORD, "")
        return model
    }

    fun deleteUserRegister() {
        val editor = preferences.edit()
        editor.putString(FIRSTNAME, "")
        editor.putString(LASTNAME    , "")
        editor.putString(PHONENUMBER, "")
        editor.putString(EMAIL, "")
        editor.putString(PASSWORD, "")
        editor.apply()
    }

    companion object {
        private const val PREFS_NAME = "register_pref"
        private const val FIRSTNAME = "FIRSTNAME"
        private const val LASTNAME = "LASTNAME"
        private const val PHONENUMBER = "PHONENUMBER"
        private const val EMAIL = "EMAIL"
        private const val PASSWORD = "PASSWORD"
    }
}