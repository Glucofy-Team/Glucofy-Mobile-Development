package com.dicoding2.glucofy.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.dicoding2.glucofy.App.Companion.CHANNEL_ID
import com.dicoding2.glucofy.App.Companion.CHANNEL_NAME
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.ui.RingActivity
import com.dicoding2.glucofy.model.Alarm
import java.io.IOException

class AlarmService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private var vibrator: Vibrator? = null
    private var ringtone: Uri? = null
    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.isLooping = true
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        ringtone = RingtoneManager.getActualDefaultRingtoneUri(
            this.baseContext,
            RingtoneManager.TYPE_ALARM
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val bundle = intent?.getBundleExtra(getString(R.string.bundle_alarm_obj))
        val alarm = bundle?.getSerializable(getString(R.string.arg_alarm_obj)) as? Alarm

        // Start RingActivity immediately
        val ringActivityIntent = Intent(this, RingActivity::class.java).apply {
            putExtra(getString(R.string.bundle_alarm_obj), bundle)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // This flag is necessary to start an activity from a service
        }
        startActivity(ringActivityIntent)

        // Create an intent for the notification tap action
        val notificationIntent = Intent(this, RingActivity::class.java).apply {
            putExtra(getString(R.string.bundle_alarm_obj), bundle)
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Create a MediaPlayer instance and set the alarm tone
        val mediaPlayer = MediaPlayer()
        val ringtoneUri = alarm?.tone?.let { Uri.parse(it) } ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        try {
            mediaPlayer.setDataSource(this, ringtoneUri)
            mediaPlayer.prepareAsync()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        // Build the notification
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(alarm?.title ?: getString(R.string.default_alarm_title))
            .setSmallIcon(R.drawable.ic_alarm_white_24dp)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
                enableVibration(true)
                vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = builder.build()

        // Start the service in the foreground
        startForeground(FOREGROUND_NOTIFICATION_ID, notification)

        // Start the MediaPlayer once it's prepared
        mediaPlayer.setOnPreparedListener { it.start() }

        return START_STICKY
    }




    override fun onDestroy() {
        super.onDestroy()

        mediaPlayer!!.stop()
        vibrator!!.cancel()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    companion object {
        private const val FOREGROUND_NOTIFICATION_ID = 1
    }
}