package com.dicoding2.glucofy.di

import android.content.Context
import android.util.Log
import com.dicoding2.glucofy.data.UserPreference
import com.dicoding2.glucofy.data.remote.retrofit.ApiConfig
import com.dicoding2.glucofy.data.remote.retrofit.ApiService
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideApiConfig(context: Context): ApiService {
        val pref = UserPreference(context)
        val user = runBlocking { pref.getUser() }
        Log.d("test",user.token.toString())
        return ApiConfig.getApiService(user.token)
    }
}