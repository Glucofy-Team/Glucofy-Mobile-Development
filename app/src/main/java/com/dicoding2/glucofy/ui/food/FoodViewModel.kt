package com.dicoding2.glucofy.ui.food

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding2.glucofy.data.remote.response.DetailFoodResponse
import com.dicoding2.glucofy.data.remote.response.FoodListItem
import com.dicoding2.glucofy.data.remote.response.MyFoodResponse
import com.dicoding2.glucofy.data.repository.FoodRepository
import kotlinx.coroutines.launch

class FoodViewModel(private val foodRepository: FoodRepository) : ViewModel() {

    // Explore Food
    val food: LiveData<PagingData<FoodListItem>> =
        foodRepository.getFoods().cachedIn(viewModelScope)

    private val _searchFoodResults = MutableLiveData<PagingData<FoodListItem>>()
    val searchFoodResults: LiveData<PagingData<FoodListItem>> get() = _searchFoodResults

    // My Food
    private val _myFood = MutableLiveData<MyFoodResponse>()
    val myFood: LiveData<MyFoodResponse> = _myFood

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage


    //FoooDetail
    private val _foodDetail= MutableLiveData<DetailFoodResponse>()
    val foodDetail: LiveData<DetailFoodResponse> = _foodDetail

    fun saveFood( foodName: String, calories: String, fats: String, carbs: String, proteins: String, gIndex: String, gLoad: String, category: String)
            = foodRepository.postFood(foodName, calories, fats, carbs, proteins, gIndex, gLoad, category)

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
                val newResult = foodRepository.getMyFoods()
                _myFood.value = newResult
                Log.d("FoodViewModel", "MyFood data received:")
            } catch (e: Exception) {
                _myFood.value = MyFoodResponse()
                _errorMessage.value = e.message
                Log.e("FoodViewModel", "Error: ${e.message}")
            }
        }
    }

    fun deleteFoodById(id: String) {
        viewModelScope.launch {
            try {
                foodRepository.deleteFoodById(id)
                Log.d("FoodViewModel", "Food data deleted")
            } catch (e: Exception) {
                Log.e("FoodViewModel", "Error: ${e.message}")
            }
        }
    }
}
