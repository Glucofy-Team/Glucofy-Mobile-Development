package com.dicoding2.glucofy.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.room.ColumnInfo
import com.dicoding2.glucofy.data.Result
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageMonthlyEntity
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageTodayEntity
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageWeeklyEntity
import com.dicoding2.glucofy.data.local.entity.GlucoseDataEntity
import com.dicoding2.glucofy.data.local.room.GlucofyRoomDatabase
import com.dicoding2.glucofy.data.remote.response.DataItem
import com.dicoding2.glucofy.data.remote.response.GlucosaResponse
import com.dicoding2.glucofy.data.remote.response.UserProfileResponse
import com.dicoding2.glucofy.data.remote.retrofit.ApiService
import com.dicoding2.glucofy.helper.generateRandomString

class GlucofyRepository (
    private val glucofyRoomDatabase: GlucofyRoomDatabase,
    private val apiService: ApiService
){
    fun getAllGlucoseAverageToday(): LiveData<List<GlucoseAverageTodayEntity>> = glucofyRoomDatabase.glucoseAverageTodayDao().getAllGlucoseAverageToday()
    fun getAllGlucoseAverageWeekly(): LiveData<List<GlucoseAverageWeeklyEntity>> = glucofyRoomDatabase.glucoseAverageWeeklyDao().getAllGlucoseAverageWeekly()
    fun getAllGlucoseAverageMonthly(): LiveData<List<GlucoseAverageMonthlyEntity>> = glucofyRoomDatabase.glucoseAverageMonthlyDao().getAllGlucoseAverageMonthly()
    fun getAllDataGlucose(): LiveData<List<GlucoseDataEntity>> = glucofyRoomDatabase.glucoseDataDao().getAllDataGlucose()

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
            if (glucoseToday != null) {
                glucofyRoomDatabase.glucoseAverageTodayDao().insertGlucose(glucoseToday)
            }


            val glucoseData = response.data?.map {data ->
                GlucoseDataEntity(
                    data?.id ?: "", data?.datetime, data?.glucose, data?.notes, data?.condition
                )
            }

            glucofyRoomDatabase.glucoseDataDao().deleteAll()
            if (glucoseData != null) {
                glucofyRoomDatabase.glucoseDataDao().insertGlucose(glucoseData)
            }

            val glucoseWeekly = response.averages?.weeklyThisMonth?.map {data ->
                GlucoseAverageWeeklyEntity(
                    generateRandomString(10), data?.date, data?.glucose
                )
            }

            glucofyRoomDatabase.glucoseAverageWeeklyDao().deleteAll()
            if (glucoseWeekly != null) {
                glucofyRoomDatabase.glucoseAverageWeeklyDao().insertGlucose(glucoseWeekly)
            }

            val glucoseMonthly = response.averages?.monthly?.map {data ->
                GlucoseAverageMonthlyEntity(
                    generateRandomString(10), data?.date, data?.glucose
                )
            }

            glucofyRoomDatabase.glucoseAverageMonthlyDao().deleteAll()
            if (glucoseMonthly != null) {
                glucofyRoomDatabase.glucoseAverageMonthlyDao().insertGlucose(glucoseMonthly)
            }

            emit(Result.Success(response))
        }catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getProfile(): LiveData<Result<UserProfileResponse>> = liveData {
        emit(Result.Loading)
        try{
            val response = apiService.getUserProfile()
            emit(Result.Success(response))
        }catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun deleteGlucose(id: String): LiveData<Boolean>{
        try {
            apiService.deleteGlucosaById(id)

            glucofyRoomDatabase.glucoseDataDao().deleteById(id)

            return liveData {  true }
        }catch (e: Exception){
            return liveData { false }
        }

    }

    suspend fun clearTableGlucose(): LiveData<Result<Boolean>> {
        glucofyRoomDatabase.glucoseAverageTodayDao().deleteAll()
        glucofyRoomDatabase.glucoseAverageWeeklyDao().deleteAll()
        glucofyRoomDatabase.glucoseAverageMonthlyDao().deleteAll()
        glucofyRoomDatabase.glucoseDataDao().deleteAll()
        return liveData {
            emit(Result.Success(true))
        }
    }

    suspend fun getGlucoseByTime(): DataItem? {
        try{
            val response = apiService.getGlucosa()
            val data = response.data ?: emptyList()

            val dataList = data.mapNotNull { item ->
                if (item?.id != null && item.datetime != null) {
                    DataItem(
                        item.glucose,
                        item.condition,
                        item.datetime,
                        item.notes,
                        item.id,
                    )
                } else {
                    null
                }
            }
            val sortedList = dataList.sortedByDescending { it.datetime }
            return sortedList.firstOrNull()
        }catch (e: Exception){
            return null
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