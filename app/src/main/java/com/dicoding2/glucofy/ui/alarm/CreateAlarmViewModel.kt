package com.dicoding2.glucofy.ui.alarm

import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.repository.AlarmRepository
import com.dicoding2.glucofy.model.Alarm

class CreateAlarmViewModel(private val alarmRepository: AlarmRepository) : ViewModel() {

    fun insert(alarm: Alarm) {
        alarmRepository.insert(alarm)
    }

    fun update(alarm: Alarm) {
        alarmRepository.update(alarm)
    }
}