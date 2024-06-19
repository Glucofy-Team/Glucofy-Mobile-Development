package com.dicoding2.glucofy.data.repository

import android.content.Context
import android.content.Intent
import android.util.Log
import com.dicoding2.glucofy.data.local.room.FoodDatabase
import com.dicoding2.glucofy.data.remote.response.ErrorResponse
import com.dicoding2.glucofy.data.remote.response.NewFoodResponse
import com.dicoding2.glucofy.data.remote.retrofit.ApiService
import com.dicoding2.glucofy.data.remote.retrofit.NewFoodRequest
import com.dicoding2.glucofy.ui.food.FoodDetailActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputFoodRepository(
    private val foodDatabase: FoodDatabase,
    private val apiService: ApiService,
) {
    fun postNewFood(
        context: Context,
        foodName: String,
        category: String,
        carbs: Number,
        protein: Number,
        fats: Number,
        calories: Number,
        onSuccess: (NewFoodResponse) -> Unit,
        onError: (ErrorResponse?) -> Unit
    ) {
        val request = NewFoodRequest(foodName, category, calories, protein, carbs, fats)
//        apiService.postNewFoodJson(request)
//            .enqueue(object : Callback<NewFoodResponse> {
//                override fun onResponse(
//                    call: Call<NewFoodResponse>,
//                    response: Response<NewFoodResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val foodResponse = response.body()!!
//                        onSuccess(foodResponse)
//                        Log.d(TAG, "onResponse: Success, navigating to FoodDetailActivity")
//                        navigateToDetailActivity(context, foodResponse)
//                    } else {
//                        val errorBody = response.errorBody()?.string()
//                        val errorResponse = try {
//                            Gson().fromJson(errorBody, ErrorResponse::class.java)
//                        } catch (e: Exception) {
//                            null
//                        }
//                        onError(errorResponse)
//                    }
//                }
//
//                override fun onFailure(call: Call<NewFoodResponse>, t: Throwable) {
//                    onError(null)
//                }
//            })
    }
    private fun navigateToDetailActivity(context: Context, food: NewFoodResponse) {
        val intent = Intent(context, FoodDetailActivity::class.java).apply {
            putExtra("foodName", food.foodName)
            putExtra("calories", food.calories)
            putExtra("fats", food.fats as Double)
            putExtra("proteins", food.proteins as Double)
            putExtra("carbs", food.carbs as Double)
            putExtra("gIndex", food.giValue)
            putExtra("gLoad", food.glValue as Double)
            putExtra("category", food.category)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)  // Add this line if context is not an activity
        }
        context.startActivity(intent)
        Log.d(TAG, "navigateToDetailActivity: Intent started")
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
