package com.dicoding2.glucofy.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.remote.response.UserProfilePostResponse
import com.dicoding2.glucofy.data.remote.retrofit.ApiService
import com.dicoding2.glucofy.di.Injection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileEditViewModel(private val apiService: ApiService) : ViewModel() {
    private val _profileUpdate = MutableLiveData<UserProfilePostResponse>()
    val profileUpdate: LiveData<UserProfilePostResponse> = _profileUpdate

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun postEditProfile(firstname: String, lastname: String, phonenumber: String,password: String, gender: String, weight: String, height: String, age: String) {

        val fields = mutableMapOf<String, String>(
            "firstName" to firstname,
            "lastName" to lastname,
            "phoneNumber" to phonenumber,
            "gender" to gender,
            "weight" to weight,
            "height" to height,
            "age" to age
        )

        if (!password.isNullOrEmpty()) {
            fields["password"] = password
        }

        _isLoading.value = true

        val client = apiService.postUpdateUser(fields)
        client.enqueue(object : Callback<UserProfilePostResponse> {
            override fun onResponse(call: Call<UserProfilePostResponse>, response: Response<UserProfilePostResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _profileUpdate.value = response.body()
                }
            }
            override fun onFailure(call: Call<UserProfilePostResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    companion object{
        private const val TAG = "ProfileEditViewModel"
        @Volatile
        private var instance: ProfileEditViewModel? = null
        fun getInstance(context: Context): ProfileEditViewModel =
            instance ?: synchronized(this) {
                instance ?: ProfileEditViewModel(Injection.provideApiConfig(context))
            }.also { instance = it }
    }
}