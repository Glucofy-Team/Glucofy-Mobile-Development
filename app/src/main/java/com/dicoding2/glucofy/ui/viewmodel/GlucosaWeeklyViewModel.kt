package com.dicoding2.glucofy.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.repository.GlucofyRepository
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageWeeklyEntity

class GlucosaWeeklyViewModel(private val glucofyRepository: GlucofyRepository) : ViewModel() {
    fun getAllDataGlucose(): LiveData<List<GlucoseAverageWeeklyEntity>> = glucofyRepository.getAllGlucoseAverageWeekly()
}