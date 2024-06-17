package com.dicoding2.glucofy.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.repository.GlucofyRepository
import com.dicoding2.glucofy.data.Result
import com.dicoding2.glucofy.data.remote.response.UserProfileResponse

class ProfileViewModel(private val glucofyRepository: GlucofyRepository) : ViewModel() {
    fun getUserProfile(): LiveData<Result<UserProfileResponse>> = glucofyRepository.getProfile()
}