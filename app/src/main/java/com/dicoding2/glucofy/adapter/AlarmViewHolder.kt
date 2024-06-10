package com.dicoding2.glucofy.adapter

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.dicoding2.glucofy.databinding.ItemAlarmBinding
import com.dicoding2.glucofy.helper.DayUtil
import com.dicoding2.glucofy.helper.OnToggleAlarmListener
import com.unique.simplealarmclock.model.Alarm

class AlarmViewHolder(binding: ItemAlarmBinding) : RecyclerView.ViewHolder(binding.root) {
    private val alarmTime: TextView = binding.tvAlarmTime
    private val alarmRecurringDays: TextView = binding.tvAlarmRecurringDays
    private val alarmTitle: TextView = binding.tvAlarmTitle
    private val alarmStarted: SwitchCompat = binding.btnAlarmStarted
    private val alarmDay: TextView = binding.tvAlarmDay

    fun bind(alarm: Alarm, listener: OnToggleAlarmListener) {
        val alarmText = String.format("%02d:%02d", alarm.hour, alarm.minute)

        alarmTime.text = alarmText
        alarmStarted.isChecked = alarm.isStarted

        if (alarm.title.isNotEmpty()) {
            alarmTitle.text = alarm.title
        } else {
            alarmTitle.text = "My alarm"
        }
        if (alarm.isRecurring) {
            alarmDay.text = alarm.recurringDaysText
        } else {
            alarmDay.setText(DayUtil.getDay(alarm.hour, alarm.minute))
        }
        alarmStarted.setOnCheckedChangeListener { buttonView, _ ->
            if (buttonView.isShown || buttonView.isPressed) listener.onToggle(
                alarm
            )
        }

        itemView.setOnClickListener { view -> listener.onItemClick(alarm, view) }
    }
}