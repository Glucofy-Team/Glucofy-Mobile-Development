package com.dicoding2.glucofy.ui.food

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.dicoding2.glucofy.data.remote.response.MyFoodListItem
import com.dicoding2.glucofy.data.repository.FoodRepository

class MyFoodViewModel (
    private val repository: FoodRepository
): ViewModel() {
    val myFood: LiveData<PagingData<MyFoodListItem>> = repository.getMyFoods
}