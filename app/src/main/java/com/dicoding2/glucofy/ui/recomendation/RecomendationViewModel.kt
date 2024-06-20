package com.dicoding2.glucofy.ui.recomendation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.remote.response.RecomendationResponse
import com.dicoding2.glucofy.data.remote.retrofit.ApiService
import com.dicoding2.glucofy.di.Injection
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class RecomendationFoodName(val foodName: String)

class RecomendationViewModel(private val apiService: ApiService) : ViewModel() {
    private val _recomendationFood = MutableLiveData<RecomendationResponse>()
    val recomendationFood: LiveData<RecomendationResponse> = _recomendationFood

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun postRecomendation(foodName: String) {
        _isLoading.value = true
        val json = Gson().toJson(RecomendationFoodName(foodName))

        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        val client = apiService.postRecomendationJson(requestBody)
        client.enqueue(object : Callback<RecomendationResponse> {
            override fun onResponse(
                call: Call<RecomendationResponse>,
                response: Response<RecomendationResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _recomendationFood.value = response.body()
                } else {

                }
            }

            override fun onFailure(call: Call<RecomendationResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun clearFoodData() {
        _recomendationFood.value = RecomendationResponse("",0)
    }

    companion object {
        private const val TAG = "RecomendationFood"

        @Volatile
        private var instance: RecomendationViewModel? = null
        fun getInstance(context: Context): RecomendationViewModel =
            instance ?: synchronized(this) {
                instance ?: RecomendationViewModel(Injection.provideApiConfig(context))
            }.also { instance = it }
    }
}