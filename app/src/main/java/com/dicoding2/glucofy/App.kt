package com.dicoding2.glucofy

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate() {
        super.onCreate()

        createNotificationChannnel()

        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val dn = sharedPreferences.getBoolean(getString(R.string.dayNightTheme), true)
        if (dn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private fun createNotificationChannnel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.app_name) + "Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            )

            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    companion object {
        const val CHANNEL_ID: String = "ALARM_SERVICE_CHANNEL"
        const val CHANNEL_NAME: String = "ALARM SERVICE"
    }
}
