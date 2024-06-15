package com.dicoding2.glucofy.di

import android.content.Context
import android.util.Log
import com.dicoding2.glucofy.data.repository.GlucofyRepository
import com.dicoding2.glucofy.data.UserPreference
import com.dicoding2.glucofy.data.local.room.AlarmDatabase
import com.dicoding2.glucofy.data.local.room.FoodDatabase
import com.dicoding2.glucofy.data.local.room.GlucofyRoomDatabase
import com.dicoding2.glucofy.data.remote.retrofit.ApiConfig
import com.dicoding2.glucofy.data.remote.retrofit.ApiService
import com.dicoding2.glucofy.data.repository.AlarmRepository
import com.dicoding2.glucofy.data.repository.FoodRepository
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideGlucofyRepository(context: Context): GlucofyRepository {
        val pref = UserPreference(context)
        val user = runBlocking { pref.getUser() }
        val apiService = ApiConfig.getApiService(user.token)
        val database = GlucofyRoomDatabase.getDatabase(context)
        return GlucofyRepository.getInstance(database, apiService)
    }

    fun provideAlarmRepository(context: Context): AlarmRepository {
        val pref = UserPreference(context)
        val user = runBlocking { pref.getUser() }
        val database = AlarmDatabase.getDatabase(context)
        return AlarmRepository.getInstance(database)
    }
    
    fun provideFoodRepository(context: Context) : FoodRepository {
        val pref = UserPreference(context)
        val user = runBlocking { pref.getUser() }
        val apiService = ApiConfig.getApiService(user.token)
        val database = FoodDatabase.getDatabase(context)
        return FoodRepository.getInstance(database, apiService)
    }

    fun provideApiConfig(context: Context): ApiService {
        val pref = UserPreference(context)
        val user = runBlocking { pref.getUser() }
        Log.d("test",user.token.toString())
        return ApiConfig.getApiService(user.token)
    }
}
