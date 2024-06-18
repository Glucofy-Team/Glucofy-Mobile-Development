package com.dicoding2.glucofy.ui.food

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.repository.FoodRepository

class FoodViewModel(private val repository: FoodRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is food Fragment"
    }
    val text: LiveData<String> = _text
}