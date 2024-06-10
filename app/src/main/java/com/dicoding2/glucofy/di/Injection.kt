package com.dicoding2.glucofy.di

import android.content.Context
import android.util.Log
import com.dicoding2.glucofy.data.GlucofyRepository
import com.dicoding2.glucofy.data.UserPreference
import com.dicoding2.glucofy.data.local.room.GlucofyRoomDatabase
import com.dicoding2.glucofy.data.remote.retrofit.ApiConfig
import com.dicoding2.glucofy.data.remote.retrofit.ApiService
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): GlucofyRepository {
        val pref = UserPreference(context)
        val user = runBlocking { pref.getUser() }
        val apiService = ApiConfig.getApiService(user.token)
        val database = GlucofyRoomDatabase.getDatabase(context)
        return GlucofyRepository.getInstance(database ,apiService)
    }

    fun provideApiConfig(context: Context): ApiService {
        val pref = UserPreference(context)
        val user = runBlocking { pref.getUser() }
        Log.d("test",user.token.toString())
        return ApiConfig.getApiService(user.token)
    }
}