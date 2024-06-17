package com.dicoding2.glucofy.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding2.glucofy.data.local.room.FoodDatabase
import com.dicoding2.glucofy.data.pagingsource.FoodPagingSource
import com.dicoding2.glucofy.data.remote.response.DetailFoodResponse
import com.dicoding2.glucofy.data.remote.response.FoodListItem
import com.dicoding2.glucofy.data.remote.retrofit.ApiService

class FoodRepository (
    private val foodDatabase : FoodDatabase,
    private val apiService: ApiService,
    ) {
    fun getFoods(query: String? = null) : LiveData<PagingData<FoodListItem>> {
        Log.d("Food Repository", "Fetching Food list with query")
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                FoodPagingSource(apiService, query)
            }
        ).liveData
    }

    suspend fun getFoodDetail(foodId: String) : DetailFoodResponse{
        return apiService.getFoodDetail(foodId)
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