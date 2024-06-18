package com.dicoding2.glucofy.ui.food

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.remote.response.DetailFoodResponse
import com.dicoding2.glucofy.data.repository.FoodRepository

class FoodDetailViewModel(private val repository: FoodRepository) : ViewModel() {

    private val _food = MutableLiveData<DetailFoodResponse>()
    val food: LiveData<DetailFoodResponse> = _food

    companion object{
        private const val TAG = "FoodDetailViewModel"
    }
}