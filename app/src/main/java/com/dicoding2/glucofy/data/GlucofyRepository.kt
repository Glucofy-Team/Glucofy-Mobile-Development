package com.dicoding2.glucofy.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageMonthlyEntity
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageTodayEntity
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageWeeklyEntity
import com.dicoding2.glucofy.data.local.room.GlucofyRoomDatabase
import com.dicoding2.glucofy.data.remote.response.GlucosaResponse
import com.dicoding2.glucofy.data.remote.retrofit.ApiService
import com.dicoding2.glucofy.helper.generateRandomString

class GlucofyRepository (
    private val glucofyRoomDatabase: GlucofyRoomDatabase,
    private val apiService: ApiService
){
    fun getAllGlucoseAverageToday(): LiveData<List<GlucoseAverageTodayEntity>> = glucofyRoomDatabase.glucoseAverageTodayDao().getAllGlucoseAverageToday()
    fun getAllGlucoseAverageWeekly(): LiveData<List<GlucoseAverageWeeklyEntity>> = glucofyRoomDatabase.glucoseAverageWeeklyDao().getAllGlucoseAverageWeekly()
    fun getAllGlucoseAverageMonthly(): LiveData<List<GlucoseAverageMonthlyEntity>> = glucofyRoomDatabase.glucoseAverageMonthlyDao().getAllGlucoseAverageMonthly()

    fun getRequestGlucose(): LiveData<Result<GlucosaResponse>> = liveData {
        emit(Result.Loading)
        try{
            val response = apiService.getGlucosa()

            val glucoseToday = response.today?.data?.map {data ->
                GlucoseAverageTodayEntity(
                    data?.id ?: "", data?.datetime, data?.glucose
                )
            }

            glucofyRoomDatabase.glucoseAverageTodayDao().deleteAll()
            glucofyRoomDatabase.glucoseAverageTodayDao().insertGlucose(glucoseToday)

            val glucoseWeekly = response.averages?.weeklyThisMonth?.map {data ->
                GlucoseAverageWeeklyEntity(
                    generateRandomString(10), data?.date, data?.glucose
                )
            }

            glucofyRoomDatabase.glucoseAverageWeeklyDao().deleteAll()
            glucofyRoomDatabase.glucoseAverageWeeklyDao().insertGlucose(glucoseWeekly)

            val glucoseMonthly = response.averages?.monthly?.map {data ->
                GlucoseAverageMonthlyEntity(
                    generateRandomString(10), data?.date, data?.glucose
                )
            }

            glucofyRoomDatabase.glucoseAverageMonthlyDao().deleteAll()
            glucofyRoomDatabase.glucoseAverageMonthlyDao().insertGlucose(glucoseMonthly)

            emit(Result.Success(response))
        }catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        private const val TAG = "Glucofyrepository"
        @Volatile
        private var instance: GlucofyRepository? = null
        fun getInstance(
            glucofyRoomDatabase: GlucofyRoomDatabase,
            apiService: ApiService,
        ): GlucofyRepository =
            instance ?: synchronized(this) {
                instance ?: GlucofyRepository(glucofyRoomDatabase, apiService)
            }.also { instance = it }
    }
}