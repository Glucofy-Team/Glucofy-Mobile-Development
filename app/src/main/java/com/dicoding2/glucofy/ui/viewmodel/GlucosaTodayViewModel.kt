package com.dicoding2.glucofy.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.GlucofyRepository
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageTodayEntity

class GlucosaTodayViewModel(private val glucofyRepository: GlucofyRepository) : ViewModel() {

    fun getAllDataGlucose(): LiveData<List<GlucoseAverageTodayEntity>> = glucofyRepository.getAllGlucoseAverageToday()
}