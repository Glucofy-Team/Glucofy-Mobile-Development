package com.dicoding2.glucofy.ui.food

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.remote.response.ErrorResponse
import com.dicoding2.glucofy.data.remote.response.NewFoodResponse
import com.dicoding2.glucofy.data.repository.FoodRepository
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputNewFoodViewModel(private val repository: FoodRepository) : ViewModel() {
    private val _food = MutableLiveData<NewFoodResponse>()
    val food: MutableLiveData<NewFoodResponse> = _food

    private val apiService = repository.apiService

    fun postNewFood(foodName: String, category: String, carbs: Int, protein: Int, fats: Int, calories: Int) {
        val client = apiService.postNewFood(foodName, category, calories, protein, carbs, fats)
        client.enqueue(object : Callback<NewFoodResponse> {
            override fun onResponse(call: Call<NewFoodResponse>, response: Response<NewFoodResponse>) {
                if (response.isSuccessful) {
                    _food.value = response.body()
                }else{
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        try {
                            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                            val message = errorResponse.message
                            val status = errorResponse.status
                            _food.value = NewFoodResponse("",message,status)
                        } catch (_: Exception) {}
                    }
                }
            }
            override fun onFailure(call: Call<NewFoodResponse>, t: Throwable) {
            }
        })
    }

    fun clearFoodData(){
        _food.value = NewFoodResponse()
    }
}