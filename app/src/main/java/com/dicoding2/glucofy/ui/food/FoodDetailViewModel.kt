package com.dicoding2.glucofy.ui.food

import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.repository.FoodRepository

class FoodDetailViewModel(private val repository: FoodRepository) : ViewModel() {

    companion object{
        private const val TAG = "FoodDetailViewModel"
    }
}