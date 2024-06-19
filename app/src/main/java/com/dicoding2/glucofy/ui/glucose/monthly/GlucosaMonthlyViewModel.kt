package com.dicoding2.glucofy.ui.glucose.monthly

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.repository.GlucofyRepository
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageMonthlyEntity
class GlucosaMonthlyViewModel (private val glucofyRepository: GlucofyRepository) : ViewModel() {
    fun getAllDataGlucose(): LiveData<List<GlucoseAverageMonthlyEntity>> = glucofyRepository.getAllGlucoseAverageMonthly()
}