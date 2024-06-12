package com.dicoding2.glucofy.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.repository.AlarmRepository
import com.dicoding2.glucofy.model.Alarm

class AlarmViewModel(private val alarmRepository: AlarmRepository) : ViewModel() {
    private val alarmsLiveData: LiveData<List<Alarm>> = alarmRepository.getAlarmsLiveData()

    fun update(alarm: Alarm) {
        alarmRepository.update(alarm)
    }

    fun getAlarmsLiveData(): LiveData<List<Alarm>> {
        return alarmsLiveData
    }

    fun delete(alarmId: Int) {
        alarmRepository.delete(alarmId)
    }
}
