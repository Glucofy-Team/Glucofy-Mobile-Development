package com.dicoding2.glucofy.ui.food

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.remote.response.NewFoodResponse
import com.dicoding2.glucofy.data.repository.InputFoodRepository

class InputNewFoodViewModel(private val repository: InputFoodRepository) : ViewModel() {
    private val _food = MutableLiveData<NewFoodResponse>()
    val food: MutableLiveData<NewFoodResponse> = _food

    fun postNewFood(foodName: String, category: String, carbs: Number, protein: Number, fats: Number, calories: Number) {
        repository.postNewFood(
            foodName, category, carbs, protein, fats, calories,
            onSuccess = { newFoodResponse -> _food.value = newFoodResponse },
            onError = { errorResponse ->
                _food.value = NewFoodResponse("", errorResponse?.message ?: "Unknown Error", -1)
            }
        )
    }
    fun clearFoodData(){
        _food.value = NewFoodResponse()
    }
}