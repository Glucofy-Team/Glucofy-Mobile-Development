package com.dicoding2.glucofy.helper

import android.view.View
import com.dicoding2.glucofy.model.Alarm

interface OnToggleAlarmListener {
    fun onToggle(alarm: Alarm?)
    fun onDelete(alarm: Alarm?)
    fun onItemClick(alarm: Alarm?, view: View?)
}