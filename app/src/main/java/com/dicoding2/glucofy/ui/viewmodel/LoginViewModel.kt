package com.dicoding2.glucofy.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.remote.response.LoginResponse
import com.dicoding2.glucofy.data.remote.retrofit.ApiService
import com.dicoding2.glucofy.di.Injection
import com.dicoding2.glucofy.helper.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val context: Context, private val apiService: ApiService): ViewModel() {
    private val _login = MutableLiveData<LoginResponse>()
    val login: LiveData<LoginResponse> = _login

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun postLogin(email: String, password: String) {
        _isLoading.value = true
        val client = apiService.postLogin(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _login.value = response.body()
                }else{
                    toast(context, "Login failed")
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    companion object{
        private const val TAG = "LoginViewModel"
        @Volatile
        private var instance: LoginViewModel? = null
        fun getInstance(context: Context): LoginViewModel =
            instance ?: synchronized(this) {
                instance ?: LoginViewModel(context, Injection.provideApiConfig(context))
            }.also { instance = it }
    }
}