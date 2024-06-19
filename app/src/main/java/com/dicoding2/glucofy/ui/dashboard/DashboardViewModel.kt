package com.dicoding2.glucofy.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.Result
import com.dicoding2.glucofy.data.remote.response.UserProfileResponse
import com.dicoding2.glucofy.data.repository.GlucofyRepository

class DashboardViewModel (
    private val repository: GlucofyRepository,
): ViewModel() {
    fun getUserProfile(): LiveData<Result<UserProfileResponse>> = repository.getProfile()

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}