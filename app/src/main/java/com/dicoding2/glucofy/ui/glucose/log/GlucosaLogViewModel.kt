package com.dicoding2.glucofy.ui.glucose.log

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding2.glucofy.data.repository.GlucofyRepository
import com.dicoding2.glucofy.data.Result
import com.dicoding2.glucofy.data.remote.response.GlucosaResponse
import kotlinx.coroutines.launch

class GlucosaLogViewModel(private val glucofyRepository: GlucofyRepository) : ViewModel() {
    fun getRequestGlucose(): LiveData<Result<GlucosaResponse>> = glucofyRepository.getRequestGlucose()

    fun clearGlucoseTables() {
        viewModelScope.launch {
            val result = glucofyRepository.clearTableGlucose()
        }
    }
}