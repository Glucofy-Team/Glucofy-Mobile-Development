package com.dicoding2.glucofy.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.GlucofyRepository
import com.dicoding2.glucofy.data.Result
import com.dicoding2.glucofy.data.remote.response.GlucosaResponse

class GlucosaLogViewModel(private val glucofyRepository: GlucofyRepository) : ViewModel() {
    fun getRequestGlucose(): LiveData<Result<GlucosaResponse>> = glucofyRepository.getRequestGlucose()
}