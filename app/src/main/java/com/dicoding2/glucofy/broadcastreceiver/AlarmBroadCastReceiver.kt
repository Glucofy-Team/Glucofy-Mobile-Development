package com.dicoding2.glucofy.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.model.Alarm
import com.unique.simplealarmclock.service.AlarmService
import com.dicoding2.glucofy.data.service.RescheduleAlarmsService
import java.util.Calendar

class AlarmBroadcastReceiver : BroadcastReceiver() {
    private var alarm: Alarm? = null
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            val toastText = String.format("Alarm Reboot")
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            startRescheduleAlarmsService(context)
        } else {
            val bundle = intent.getBundleExtra(context.getString(R.string.bundle_alarm_obj))
            if (bundle != null) alarm =
                bundle.getSerializable(context.getString(R.string.arg_alarm_obj)) as Alarm?
            val toastText = String.format("Alarm Received")
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            if (alarm != null) {
                if (!alarm!!.isRecurring) {
                    startAlarmService(context, alarm!!)
                } else {
                    if (isAlarmToday(alarm!!)) {
                        startAlarmService(context, alarm!!)
                    }
                }
            }
        }
    }

    private fun isAlarmToday(alarm1: Alarm): Boolean {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        val today = calendar[Calendar.DAY_OF_WEEK]

        when (today) {
            Calendar.MONDAY -> {
                return alarm1.isMonday
            }

            Calendar.TUESDAY -> {
                return alarm1.isTuesday
            }

            Calendar.WEDNESDAY -> {
                return alarm1.isWednesday
            }

            Calendar.THURSDAY -> {
                return alarm1.isThursday
            }

            Calendar.FRIDAY -> {
                return alarm1.isFriday
            }

            Calendar.SATURDAY -> {
                return alarm1.isSaturday
            }

            Calendar.SUNDAY -> {
                return alarm1.isSunday
            }
        }
        return false
    }

    private fun startAlarmService(context: Context, alarm1: Alarm) {
        val intentService = Intent(context, AlarmService::class.java)
        val bundle = Bundle()
        bundle.putSerializable(context.getString(R.string.arg_alarm_obj), alarm1)
        intentService.putExtra(context.getString(R.string.bundle_alarm_obj), bundle)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }

    private fun startRescheduleAlarmsService(context: Context) {
        val intentService = Intent(
            context,
            RescheduleAlarmsService::class.java
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }
}