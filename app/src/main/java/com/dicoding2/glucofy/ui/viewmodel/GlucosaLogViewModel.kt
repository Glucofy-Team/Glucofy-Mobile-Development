package com.dicoding2.glucofy.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GlucosaLogViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is glucosa log Fragment"
    }
    val text: LiveData<String> = _text
}