package com.dicoding2.glucofy.ui.auth

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.data.remote.response.ErrorResponse
import com.dicoding2.glucofy.data.remote.response.RegisterResponse
import com.dicoding2.glucofy.data.remote.retrofit.ApiService
import com.dicoding2.glucofy.di.Injection
import com.dicoding2.glucofy.helper.toast
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val context: Context, private val apiService: ApiService): ViewModel() {
    private val _register = MutableLiveData<RegisterResponse>()
    val register: LiveData<RegisterResponse> = _register

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun postRegister(firstname: String, lastname: String, phonenumber: String, email: String,password: String, gender: String, weight: String, height: String, age: String) {
        _isLoading.value = true

        val client = apiService.postRegister(firstname,lastname,phonenumber,email,password,gender,weight,height,age)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _register.value = response.body()
                    Log.e(TAG, response.body().toString())
                } else {
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        try {
                            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                            val message = errorResponse.message ?: "Login failed"
                            toast(context, message)
                        } catch (e: Exception) {
                            toast(context, "An unexpected error occurred. Please try again later.")
                        }
                    } else {
                        toast(context, "Login failed. Please check your network connection.")
                    }
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false

            }
        })
    }
    companion object{
        private const val TAG = "LoginViewModel"
        @Volatile
        private var instance: RegisterViewModel? = null
        fun getInstance(context: Context): RegisterViewModel =
            instance ?: synchronized(this) {
                instance ?: RegisterViewModel(context, Injection.provideApiConfig(context))
            }.also { instance = it }
    }
}