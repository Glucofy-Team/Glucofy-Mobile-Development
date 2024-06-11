package com.dicoding2.glucofy.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding2.glucofy.data.repository.AlarmRepository
import com.dicoding2.glucofy.model.Alarm

class CreateAlarmViewModel : ViewModel() {
    private lateinit var alarmRepository: AlarmRepository
    fun insert(alarm: Alarm) {
        alarmRepository.insert(alarm)
    }

    fun update(alarm: Alarm) {
        alarmRepository.update(alarm)
    }
}