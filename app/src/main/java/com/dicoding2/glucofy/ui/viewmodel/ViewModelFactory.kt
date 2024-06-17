package com.dicoding2.glucofy.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding2.glucofy.data.repository.GlucofyRepository
import com.dicoding2.glucofy.data.repository.AlarmRepository
import com.dicoding2.glucofy.data.repository.FoodRepository
import com.dicoding2.glucofy.di.Injection

class ViewModelFactory private constructor(
    private val glucofyRepository: GlucofyRepository,
    private val alarmRepository: AlarmRepository,
    private val foodRepository: FoodRepository,
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
            modelClass.isAssignableFrom(InputViewModel::class.java) -> {
                return InputViewModel(foodRepository) as T
            }
            modelClass.isAssignableFrom(FoodDetailViewModel::class.java) -> {
                return FoodDetailViewModel(foodRepository) as T
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
                    Injection.provideAlarmRepository(context),
                    Injection.provideFoodRepository(context)
                ).also { instance = it }
            }
    }
}
