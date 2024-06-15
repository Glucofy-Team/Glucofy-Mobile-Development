package com.dicoding2.glucofy.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding2.glucofy.data.remote.response.FoodListItem
import com.dicoding2.glucofy.data.repository.FoodRepository

class InputViewModel(private val foodRepository: FoodRepository) : ViewModel() {

    val food: LiveData<PagingData<FoodListItem>> =
        foodRepository.getFoods().cachedIn(viewModelScope)

    private val _searchResults = MutableLiveData<PagingData<FoodListItem>>()
    val searchResults: LiveData<PagingData<FoodListItem>> get() = _searchResults

    fun findFoods(name: String) {
        val newResult = foodRepository.getFoods(name).cachedIn(viewModelScope)
        _searchResults.value = newResult.value
    }
}