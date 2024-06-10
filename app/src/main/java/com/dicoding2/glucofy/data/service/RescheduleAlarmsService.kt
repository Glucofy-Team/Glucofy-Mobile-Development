package com.dicoding2.glucofy.data.service

import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.PowerManager
import androidx.lifecycle.LifecycleService
import com.dicoding2.glucofy.data.repository.AlarmRepository

class RescheduleAlarmsService : LifecycleService() {

    private val alarmRepository: AlarmRepository by lazy { AlarmRepository(application) }

    private val wakeLock: PowerManager.WakeLock by lazy {
        (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "RescheduleAlarmsService::lock").apply {
                acquire(10*60*1000L /*10 minutes*/)
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        alarmRepository.getAlarmsLiveData().observe(this) { alarms ->
            alarms?.let {
                for (alarm in it) {
                    if (alarm.isStarted) {
                        alarm.schedule(applicationContext)
                    }
                }
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        wakeLock.release()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}