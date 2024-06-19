package com.dicoding2.glucofy.ui.food

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.Result
import com.dicoding2.glucofy.data.remote.response.DetailFoodResponse
import com.dicoding2.glucofy.data.remote.response.FoodAddResponse
import com.dicoding2.glucofy.data.repository.FoodRepository

class FoodDetailViewModel(private val repository: FoodRepository) : ViewModel() {

    private val _food = MutableLiveData<DetailFoodResponse>()
    val food: LiveData<DetailFoodResponse> = _food

    fun saveFood( foodName: String, calories: String, fats: String, carbs: String, proteins: String, gIndex: String, gLoad: String, category: String)
        = repository.postFood(foodName, calories, fats, carbs, proteins, gIndex, gLoad, category)


    companion object{
        private const val TAG = "FoodDetailViewModel"
    }
}