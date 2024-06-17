package com.dicoding2.glucofy.data.repository

import com.dicoding2.glucofy.data.local.room.FoodDatabase
import com.dicoding2.glucofy.data.remote.response.ErrorResponse
import com.dicoding2.glucofy.data.remote.response.NewFoodResponse
import com.dicoding2.glucofy.data.remote.retrofit.ApiService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputFoodRepository(
    private val foodDatabase: FoodDatabase,
    val apiService: ApiService,
) {
    fun postNewFood(
        foodName: String,
        category: String,
        carbs: Number,
        protein: Number,
        fats: Number,
        calories: Number,
        onSuccess: (NewFoodResponse) -> Unit,
        onError: (ErrorResponse?) -> Unit
    ) {
        apiService.postNewFood(foodName, category, calories, protein, carbs, fats)
            .enqueue(object : Callback<NewFoodResponse> {
                override fun onResponse(
                    call: Call<NewFoodResponse>,
                    response: Response<NewFoodResponse>
                ) {
                    if (response.isSuccessful) {
                        onSuccess(response.body()!!)
                    } else {
                        val errorBody = response.errorBody()?.string()
                        val errorResponse = try {
                            Gson().fromJson(errorBody, ErrorResponse::class.java)
                        } catch (e: Exception) {
                            null
                        }
                        onError(errorResponse)
                    }
                }

                override fun onFailure(call: Call<NewFoodResponse>, t: Throwable) {
                    onError(null)
                }
            })
    }

    companion object {
        private const val TAG = "InputFoodRepository"
        @Volatile
        private var instance: InputFoodRepository? = null
        fun getInstance(
            foodDatabase : FoodDatabase,
            apiService: ApiService,
        ): InputFoodRepository =
            instance ?: synchronized(this) {
                instance ?: InputFoodRepository(foodDatabase, apiService)
            }.also { instance = it }
    }
}