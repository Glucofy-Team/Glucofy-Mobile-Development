package com.dicoding2.glucofy.ui.glucose.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.Result
import com.dicoding2.glucofy.data.repository.GlucofyRepository
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageTodayEntity
import com.dicoding2.glucofy.data.local.entity.GlucoseDataEntity
import com.dicoding2.glucofy.data.remote.response.GlucosaResponse

class GlucosaTodayViewModel(private val glucofyRepository: GlucofyRepository) : ViewModel() {

    fun getAllDataGlucose(): LiveData<List<GlucoseAverageTodayEntity>> = glucofyRepository.getAllGlucoseAverageToday()
    fun getDataGlucose(): LiveData<List<GlucoseDataEntity>> = glucofyRepository.getAllDataGlucose()
    suspend fun deleteGlucoseById(id: String): LiveData<Boolean> = glucofyRepository.deleteGlucose(id)
}