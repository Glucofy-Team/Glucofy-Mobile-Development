package com.dicoding2.glucofy.ui.glucose.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.repository.GlucofyRepository

class DetailViewModel(private val glucofyRepository: GlucofyRepository) : ViewModel() {

    suspend fun deleteGlucoseById(id: String): LiveData<Boolean> = glucofyRepository.deleteGlucose(id)
}