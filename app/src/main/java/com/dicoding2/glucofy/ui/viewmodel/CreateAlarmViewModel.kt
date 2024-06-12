package com.dicoding2.glucofy.ui.viewmodel

import com.dicoding2.glucofy.data.repository.AlarmRepository
import com.dicoding2.glucofy.model.Alarm
import androidx.lifecycle.ViewModel

class CreateAlarmViewModel(private val alarmRepository: AlarmRepository) : ViewModel() {

    fun insert(alarm: Alarm) {
        alarmRepository.insert(alarm)
    }

    fun update(alarm: Alarm) {
        alarmRepository.update(alarm)
    }
}