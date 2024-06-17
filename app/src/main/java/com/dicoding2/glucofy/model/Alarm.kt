package com.dicoding2.glucofy.model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.broadcastreceiver.AlarmBroadcastReceiver
import com.dicoding2.glucofy.helper.DayUtil
import java.io.Serializable
import java.util.Calendar
import java.util.Locale

@Entity(tableName = "alarm_table")
class Alarm(
    @field:PrimaryKey var alarmId: Int,
    var hour: Int,
    var minute: Int,
    var title: String,
    var isStarted: Boolean,
    val isRecurring: Boolean,
    val isMonday: Boolean,
    val isTuesday: Boolean,
    val isWednesday: Boolean,
    val isThursday: Boolean,
    val isFriday: Boolean,
    val isSaturday: Boolean,
    val isSunday: Boolean,
    var tone: String,
    var isVibrate: Boolean
) : Serializable {

    @JvmName("getStarted")
    fun setStarted(started: Boolean) {
        this.isStarted = started
    }

    fun schedule(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java).apply {
            val bundle = Bundle().apply {
                putSerializable(context.getString(R.string.arg_alarm_obj), this@Alarm)
            }
            putExtra(context.getString(R.string.bundle_alarm_obj), bundle)
        }
        val alarmPendingIntent = PendingIntent.getBroadcast(
            context, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Check if the permission is granted
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Call the method introduced in Android 12 or later
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                context.startActivity(intent)
                return
            }
        } else {
            val alarmTimeMillis = System.currentTimeMillis()
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                alarmTimeMillis,
                alarmPendingIntent
            )
        }




        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            // if alarm time has already passed, increment day by 1
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        if (isRecurring) {
            val toastText = String.format(
                Locale.getDefault(),
                "Recurring Alarm %s scheduled for %s at %02d:%02d",
                title.toLowerCase(Locale.getDefault()),
                getRecurringDaysText(),
                hour,
                minute
            )
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()

            val RUN_DAILY = (24 * 60 * 60 * 1000).toLong()
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                RUN_DAILY,
                alarmPendingIntent
            )
        } else {
            val toastText = "One Time Alarm $title scheduled for ${DayUtil.toDay(calendar[Calendar.DAY_OF_WEEK])} at $hour:$minute"
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmPendingIntent
            )
        }

        isStarted = true
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(
            context, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(alarmPendingIntent)
        isStarted = false
        val toastText = "Alarm cancelled for $hour:$minute"
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        Log.i("cancel", toastText)
    }

    fun getRecurringDaysText(): String? {
        if (isRecurring) {
            return null
        }

        val days = StringBuilder()

        when {
            isMonday -> days.append("Mo ")
            isTuesday -> days.append("Tu ")
            isWednesday -> days.append("We ")
            isThursday -> days.append("Th ")
            isFriday -> days.append("Fr ")
            isSaturday -> days.append("Sa ")
            isSunday -> days.append("Su ")
        }
        return days.toString()
    }
}
