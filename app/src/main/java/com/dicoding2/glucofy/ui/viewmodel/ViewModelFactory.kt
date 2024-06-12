package com.dicoding2.glucofy.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding2.glucofy.data.GlucofyRepository
import com.dicoding2.glucofy.data.repository.AlarmRepository
import com.dicoding2.glucofy.di.Injection

class ViewModelFactory private constructor(
    private val glucofyRepository: GlucofyRepository,
    private val alarmRepository: AlarmRepository,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(GlucosaLogViewModel::class.java) -> {
                return GlucosaLogViewModel(glucofyRepository) as T
            }
            modelClass.isAssignableFrom(CreateAlarmViewModel::class.java) -> {
                return CreateAlarmViewModel(alarmRepository) as T
            }
            modelClass.isAssignableFrom(AlarmViewModel::class.java) -> {
                return AlarmViewModel(alarmRepository) as T
            }
            else -> {throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")}
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideGlucofyRepository(context),
                    Injection.provideAlarmRepository(context)
                ).also { instance = it }
            }
    }
}
