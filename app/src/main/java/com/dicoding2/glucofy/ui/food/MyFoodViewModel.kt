package com.dicoding2.glucofy.ui.food

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding2.glucofy.data.remote.response.MyFoodListItem
import com.dicoding2.glucofy.data.repository.FoodRepository
import kotlinx.coroutines.launch

class MyFoodViewModel (
    private val repository: FoodRepository
): ViewModel() {
    val myFood: LiveData<PagingData<MyFoodListItem>> = repository.getMyFoods()

    private val _searchResults = MutableLiveData<PagingData<MyFoodListItem>>()
    val searchResults: LiveData<PagingData<MyFoodListItem>> get() = _searchResults

    fun findMyFoods() {
        viewModelScope.launch {
            try {
                val newResult = repository.getMyFoods().cachedIn(viewModelScope)
                newResult.observeForever{pagingData ->
                    _searchResults.value = pagingData

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}