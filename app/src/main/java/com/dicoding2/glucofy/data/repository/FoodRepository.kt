package com.dicoding2.glucofy.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding2.glucofy.data.Result
import com.dicoding2.glucofy.data.local.room.FoodDatabase
import com.dicoding2.glucofy.data.pagingsource.FoodPagingSource
import com.dicoding2.glucofy.data.remote.response.FoodAddResponse
import com.dicoding2.glucofy.data.remote.response.FoodListItem
import com.dicoding2.glucofy.data.remote.response.MyFoodResponse
import com.dicoding2.glucofy.data.remote.response.TodayFoodResponse
import com.dicoding2.glucofy.data.remote.retrofit.ApiService

class FoodRepository (
    private val foodDatabase : FoodDatabase,
    private val apiService: ApiService,
    ) {
    fun getFoods(query: String? = null) : LiveData<PagingData<FoodListItem>> {
        Log.d("Food Repository", "Fetching Food list with query")
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                FoodPagingSource(apiService, query)
            }
        ).liveData
    }

    suspend fun getMyFoods() : MyFoodResponse {
        Log.d("Food Repository", "Fetching MyFood")
        return apiService.getMyFood()
    }

    fun postFood(foodName: String, calories: String, fats: String, carbs: String, proteins: String, gIndex: String, gLoad: String, category: String): LiveData<Result<FoodAddResponse>> = liveData {
            emit(Result.Loading)
            try {
                val response = apiService.postFoodAdd(foodName, gIndex, gLoad, "-", "-", carbs, calories, fats, proteins, category)
                emit(Result.Success(response))
            }catch (e: Exception){
                emit(Result.Error(e.message.toString()))
            }
    }

    suspend fun getTodayFood() : TodayFoodResponse {
        Log.d("Food Repository", "Fetching MyFood")
        return apiService.getTodayFood()
    }

    suspend fun deleteFoodById(id: String) {
        try {
            apiService.deleteFoodById(id)
            Log.d(TAG, "deleteFoodById: Success")
        } catch (e: Exception) {
            Log.d(TAG, "deleteFoodById: ${e.message.toString()} ")
        }
    }

    companion object {
        private const val TAG = "FoodRepository"
        @Volatile
        private var instance: FoodRepository? = null
        fun getInstance(
            foodDatabase : FoodDatabase,
            apiService: ApiService,
        ): FoodRepository =
            instance ?: synchronized(this) {
                instance ?: FoodRepository(foodDatabase, apiService)
            }.also { instance = it }
    }
}