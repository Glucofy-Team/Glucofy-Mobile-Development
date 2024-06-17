package com.dicoding2.glucofy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.local.entity.InsulinEntity

class CalculatorViewModel : ViewModel() {
    private val _insulin = MutableLiveData<InsulinEntity>()
    val insulin: MutableLiveData<InsulinEntity> = _insulin

    fun calculateInsulin(category: String, weight: Int){
        var percent: Int

        if(category == "Insulin Basal"){
            percent = 40
            var iht = 0.5 * weight
            var ibt = (percent / 100.0) * iht

            _insulin.value = InsulinEntity(0,0,ibt.toInt())
        }else if(category == "Insulin Pradial"){
            percent = 60
            var iht = 0.5 * weight
            var ipt = (percent / 100.0) * iht
            var dosis = (1.0 / 3 * ipt).toInt()

            _insulin.value = InsulinEntity(dosis,dosis,dosis)
        }
    }
}