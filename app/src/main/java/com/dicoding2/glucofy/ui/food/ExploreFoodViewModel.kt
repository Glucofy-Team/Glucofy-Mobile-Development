package com.dicoding2.glucofy.ui.food

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding2.glucofy.data.remote.response.FoodListItem
import com.dicoding2.glucofy.data.repository.FoodRepository
import kotlinx.coroutines.launch

class ExploreFoodViewModel(private val foodRepository: FoodRepository) : ViewModel() {

    val food: LiveData<PagingData<FoodListItem>> =
        foodRepository.getFoods().cachedIn(viewModelScope)

    private val _searchResults = MutableLiveData<PagingData<FoodListItem>>()
    val searchResults: LiveData<PagingData<FoodListItem>> get() = _searchResults

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

//    private val _showAddButton = MutableLiveData<Boolean>()
//    val showAddButton: LiveData<Boolean> get() = _showAddButton

    fun findFoods(name: String) {
        viewModelScope.launch {
            try {
                val newResult = foodRepository.getFoods(name).cachedIn(viewModelScope)
                newResult.observeForever { pagingData ->
                    _searchResults.value = pagingData
                    Log.d("InputViewModel", "Data received: $pagingData")
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e("InputViewModel", "Error: ${e.message}")
            }
        }
    }

}
