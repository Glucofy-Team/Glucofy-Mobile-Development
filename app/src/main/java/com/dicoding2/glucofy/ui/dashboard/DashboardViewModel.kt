package com.dicoding2.glucofy.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding2.glucofy.data.Result
import com.dicoding2.glucofy.data.remote.response.DataItem
import com.dicoding2.glucofy.data.remote.response.TodayFoodResponse
import com.dicoding2.glucofy.data.remote.response.UserProfileResponse
import com.dicoding2.glucofy.data.repository.FoodRepository
import com.dicoding2.glucofy.data.repository.GlucofyRepository
import kotlinx.coroutines.launch

class DashboardViewModel (
    private val glucofyRepository: GlucofyRepository,
    private val foodRepository: FoodRepository
): ViewModel() {
    fun getUserProfile(): LiveData<Result<UserProfileResponse>> = glucofyRepository.getProfile()

    private val _todayFood = MutableLiveData<TodayFoodResponse>()
    val todayFood: LiveData<TodayFoodResponse> = _todayFood

    private val _lastGlucoseData = MutableLiveData<DataItem?>()
    val lastGlucoseData: LiveData<DataItem?> = _lastGlucoseData

    fun getTodayFood() {
        viewModelScope.launch {
            try {
                val newResult = foodRepository.getTodayFood()
                _todayFood.value = newResult
                Log.d("DashboardViewModel", "TodayFood data received:")
            } catch (e: Exception) {
                Log.e("DashboardViewModel", "Error: ${e.message}")
            }
        }
    }

    fun getLastGlucose() {
        viewModelScope.launch {
            _lastGlucoseData.value = glucofyRepository.getGlucoseByTime()
        }
    }
}