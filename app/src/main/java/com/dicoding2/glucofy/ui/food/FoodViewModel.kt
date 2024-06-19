package com.dicoding2.glucofy.ui.food

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding2.glucofy.data.remote.response.FoodListItem
import com.dicoding2.glucofy.data.remote.response.MyFoodListItem
import com.dicoding2.glucofy.data.repository.FoodRepository
import kotlinx.coroutines.launch

class FoodViewModel(private val foodRepository: FoodRepository) : ViewModel() {

    // Explore Food
    val food: LiveData<PagingData<FoodListItem>> =
        foodRepository.getFoods().cachedIn(viewModelScope)

    private val _searchFoodResults = MutableLiveData<PagingData<FoodListItem>>()
    val searchFoodResults: LiveData<PagingData<FoodListItem>> get() = _searchFoodResults

    // My Food
    val myFood: LiveData<PagingData<MyFoodListItem>> =
        foodRepository.getMyFoods().cachedIn(viewModelScope)

    private val _searchMyFoodResults = MutableLiveData<PagingData<MyFoodListItem>>()
    val searchMyFoodResults: LiveData<PagingData<MyFoodListItem>> get() = _searchMyFoodResults

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    // Function to search foods by name
    fun findFoods(name: String) {
        viewModelScope.launch {
            try {
                val newResult = foodRepository.getFoods(name).cachedIn(viewModelScope)
                newResult.observeForever { pagingData ->
                    _searchFoodResults.value = pagingData
                    Log.d("FoodViewModel", "Food data received: $pagingData")
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e("FoodViewModel", "Error: ${e.message}")
            }
        }
    }

    // Function to search my foods
    fun findMyFoods() {
        viewModelScope.launch {
            try {
                val newResult = foodRepository.getMyFoods().cachedIn(viewModelScope)
                newResult.observeForever { pagingData ->
                    _searchMyFoodResults.value = pagingData
                    Log.d("FoodViewModel", "MyFood data received: $pagingData")
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e("FoodViewModel", "Error: ${e.message}")
            }
        }
    }
}
