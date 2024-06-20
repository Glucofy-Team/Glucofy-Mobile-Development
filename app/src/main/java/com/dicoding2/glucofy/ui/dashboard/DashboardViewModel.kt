package com.dicoding2.glucofy.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding2.glucofy.data.Result
import com.dicoding2.glucofy.data.remote.response.TodayFoodResponse
import com.dicoding2.glucofy.data.remote.response.UserProfileResponse
import com.dicoding2.glucofy.data.repository.GlucofyRepository
import kotlinx.coroutines.launch

class DashboardViewModel (
    private val repository: GlucofyRepository,
): ViewModel() {
    fun getUserProfile(): LiveData<Result<UserProfileResponse>> = repository.getProfile()

    private val _todayFood = MutableLiveData<TodayFoodResponse>()
    val todayFood: LiveData<TodayFoodResponse> = _todayFood

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getTodayFood() {
        viewModelScope.launch {
            try {
                val newResult = repository.getTodayFood()
                _todayFood.value = newResult
                Log.d("DashoardViewModel", "TodayFood data received:")
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e("FoodViewModel", "Error: ${e.message}")
            }
        }
    }
}