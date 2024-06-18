package com.dicoding2.glucofy.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding2.glucofy.data.repository.GlucofyRepository
import com.dicoding2.glucofy.data.Result
import com.dicoding2.glucofy.data.remote.response.UserProfileResponse
import com.dicoding2.glucofy.data.repository.AlarmRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val glucofyRepository: GlucofyRepository, private val alarmRepository: AlarmRepository) : ViewModel() {
    fun getUserProfile(): LiveData<Result<UserProfileResponse>> = glucofyRepository.getProfile()

    fun clearTableGlucose() {
        viewModelScope.launch {
            alarmRepository.deleteAll()
            glucofyRepository.clearTableGlucose()
        }
    }
}