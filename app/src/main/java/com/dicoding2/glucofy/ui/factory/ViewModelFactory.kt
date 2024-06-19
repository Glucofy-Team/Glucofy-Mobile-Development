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
import com.dicoding2.glucofy.ui.food.ExploreFoodViewModel
import com.dicoding2.glucofy.ui.food.FoodDetailViewModel
import com.dicoding2.glucofy.ui.food.FoodViewModel
import com.dicoding2.glucofy.ui.food.InputNewFoodViewModel
import com.dicoding2.glucofy.ui.food.MyFoodViewModel
import com.dicoding2.glucofy.ui.viewmodel.GlucosaLogViewModel
import com.dicoding2.glucofy.ui.viewmodel.GlucosaMonthlyViewModel
import com.dicoding2.glucofy.ui.viewmodel.GlucosaTodayViewModel
import com.dicoding2.glucofy.ui.viewmodel.GlucosaWeeklyViewModel
import com.dicoding2.glucofy.ui.viewmodel.ProfileViewModel

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
          modelClass.isAssignableFrom(ExploreFoodViewModel::class.java) -> {
            return ExploreFoodViewModel(foodRepository) as T
          }
           modelClass.isAssignableFrom(FoodDetailViewModel::class.java) -> {
             return FoodDetailViewModel(foodRepository) as T
          }
           modelClass.isAssignableFrom(InputNewFoodViewModel::class.java) -> {
               return InputNewFoodViewModel(InputFoodRepository) as T
           }
           modelClass.isAssignableFrom(FoodViewModel::class.java) -> {
               return FoodViewModel(foodRepository) as T
           }
           modelClass.isAssignableFrom(MyFoodViewModel::class.java) -> {
               return MyFoodViewModel(foodRepository) as T
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
