package com.dicoding2.glucofy.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FoodViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is food Fragment"
    }
    val text: LiveData<String> = _text
}