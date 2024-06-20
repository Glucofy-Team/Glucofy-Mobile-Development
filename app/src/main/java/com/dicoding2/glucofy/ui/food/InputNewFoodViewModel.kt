package com.dicoding2.glucofy.ui.food

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.remote.response.AddGlucosaResponse
import com.dicoding2.glucofy.data.remote.response.ErrorResponse
import com.dicoding2.glucofy.data.remote.response.LoginResponse
import com.dicoding2.glucofy.data.remote.response.NewFoodResponse
import com.dicoding2.glucofy.data.remote.retrofit.ApiService
import com.dicoding2.glucofy.data.repository.InputFoodRepository
import com.dicoding2.glucofy.di.Injection
import com.dicoding2.glucofy.helper.toast
import com.dicoding2.glucofy.ui.glucose.add.AddGlucosaViewModel
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


data class TestData(
    val foodName: String,
    val category: String,
    val carbs: Int,
    val proteins: Int,
    val fats: Int,
    val calories: Int
)

class InputNewFoodViewModel(private val apiService: ApiService) : ViewModel() {
    private val _newFood = MutableLiveData<NewFoodResponse>()
    val newFood: LiveData<NewFoodResponse> = _newFood

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun postNewFood(foodName: String, category: String, carbs: Int, protein: Int, fats: Int, calories: Int) {
        _isLoading.value = true
        val testData = TestData(foodName, category, carbs, protein, fats, calories)

        val json = Gson().toJson(testData)

        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        val client = apiService.postNewFoodJson(requestBody)
        client.enqueue(object : Callback<NewFoodResponse> {
            override fun onResponse(
                call: Call<NewFoodResponse>,
                response: Response<NewFoodResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _newFood.value = response.body()
                } else {

                }
            }

            override fun onFailure(call: Call<NewFoodResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun clearFoodData() {
        _newFood.value = NewFoodResponse()
    }

    companion object {
        private const val TAG = "AddNewFoodViewModel"

        @Volatile
        private var instance: InputNewFoodViewModel? = null
        fun getInstance(context: Context): InputNewFoodViewModel =
            instance ?: synchronized(this) {
                instance ?: InputNewFoodViewModel(Injection.provideApiMlConfig(context))
            }.also { instance = it }
    }
}
