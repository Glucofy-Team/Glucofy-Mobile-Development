package com.dicoding2.glucofy.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding2.glucofy.data.repository.AlarmRepository
import com.dicoding2.glucofy.data.repository.FoodRepository
import com.dicoding2.glucofy.data.repository.GlucofyRepository
import com.dicoding2.glucofy.data.repository.InputFoodRepository
import com.dicoding2.glucofy.di.Injection
import com.dicoding2.glucofy.ui.alarm.AlarmViewModel
import com.dicoding2.glucofy.ui.alarm.CreateAlarmViewModel
import com.dicoding2.glucofy.ui.dashboard.DashboardViewModel
import com.dicoding2.glucofy.ui.food.FoodDetailViewModel
import com.dicoding2.glucofy.ui.food.FoodViewModel
import com.dicoding2.glucofy.ui.food.InputNewFoodViewModel
import com.dicoding2.glucofy.ui.glucose.log.GlucosaLogViewModel
import com.dicoding2.glucofy.ui.glucose.monthly.GlucosaMonthlyViewModel
import com.dicoding2.glucofy.ui.glucose.today.GlucosaTodayViewModel
import com.dicoding2.glucofy.ui.glucose.weekly.GlucosaWeeklyViewModel
import com.dicoding2.glucofy.ui.profile.ProfileViewModel

class ViewModelFactory private constructor(
    private val glucofyRepository: GlucofyRepository,
    private val alarmRepository: AlarmRepository,
    private val foodRepository: FoodRepository,
    private val InputFoodRepository : InputFoodRepository,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return when {
          modelClass.isAssignableFrom(GlucosaLogViewModel::class.java) -> {
              return GlucosaLogViewModel(glucofyRepository) as T
          }
          modelClass.isAssignableFrom(GlucosaTodayViewModel::class.java) -> {
              return GlucosaTodayViewModel(glucofyRepository) as T
          }
          modelClass.isAssignableFrom(GlucosaWeeklyViewModel::class.java) -> {
              return GlucosaWeeklyViewModel(glucofyRepository) as T
          }
          modelClass.isAssignableFrom(GlucosaMonthlyViewModel::class.java) -> {
              return GlucosaMonthlyViewModel(glucofyRepository) as T
          }
          modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
              return ProfileViewModel(glucofyRepository,alarmRepository) as T
          }
          modelClass.isAssignableFrom(CreateAlarmViewModel::class.java) -> {
              return CreateAlarmViewModel(alarmRepository) as T
          }
          modelClass.isAssignableFrom(AlarmViewModel::class.java) -> {
              return AlarmViewModel(alarmRepository) as T
          }
           modelClass.isAssignableFrom(FoodDetailViewModel::class.java) -> {
             return FoodDetailViewModel(foodRepository) as T
          }
           modelClass.isAssignableFrom(FoodViewModel::class.java) -> {
               return FoodViewModel(foodRepository) as T
           }
           modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
               return DashboardViewModel(glucofyRepository) as T
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
                    Injection.provideFoodRepository(context),
                    Injection.provideFoodRepositoryML(context)
                ).also { instance = it }
            }
    }
}
