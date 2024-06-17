package com.dicoding2.glucofy.ui.food

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding2.glucofy.data.remote.response.DetailFoodResponse
import com.dicoding2.glucofy.data.repository.FoodRepository
import kotlinx.coroutines.launch

class FoodDetailViewModel(private val repository: FoodRepository) : ViewModel() {

    private val _food = MutableLiveData<DetailFoodResponse>()
    val food: LiveData<DetailFoodResponse> = _food

    fun getFoodDetail(foodId: String ) {
        viewModelScope.launch {
            try {
                val response = repository.getFoodDetail(foodId)
                _food.value = response
            } catch (e: Exception) {
                Log.e(TAG, "getFoodDetail: ${e.message.toString()}")
            }
        }
    }

    companion object{
        private const val TAG = "FoodDetailViewModel"
    }
}