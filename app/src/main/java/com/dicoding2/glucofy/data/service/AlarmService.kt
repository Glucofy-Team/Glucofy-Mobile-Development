package com.dicoding2.glucofy.data.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.dicoding2.glucofy.App.Companion.CHANNEL_ID
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.ui.RingActivity
import com.dicoding2.glucofy.model.Alarm
import java.io.IOException

class AlarmService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private var vibrator: Vibrator? = null
    private var alarm: Alarm? = null
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

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val bundle = intent.getBundleExtra(getString(R.string.bundle_alarm_obj))
        if (bundle != null) alarm =
            bundle.getSerializable(getString(R.string.arg_alarm_obj)) as Alarm?
        val notificationIntent = Intent(
            this,
            RingActivity::class.java
        )
        notificationIntent.putExtra(getString(R.string.bundle_alarm_obj), bundle)
        //        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        var alarmTitle = getString(R.string.alarm_title)
        if (alarm != null) {
            alarmTitle = alarm!!.title
            try {
                mediaPlayer!!.setDataSource(
                    this.baseContext, Uri.parse(
                        alarm!!.tone
                    )
                )
                mediaPlayer!!.prepareAsync()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        } else {
            try {
                mediaPlayer!!.setDataSource(this.baseContext, ringtone!!)
                mediaPlayer!!.prepareAsync()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Ring Ring .. Ring Ring")
            .setContentText(alarmTitle)
            .setSmallIcon(R.drawable.ic_alarm_white_24dp)
            .setSound(null)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setFullScreenIntent(pendingIntent, true)
            .build()
        mediaPlayer!!.setOnPreparedListener { mediaPlayer -> mediaPlayer.start() }

        if (alarm!!.isVibrate) {
            val pattern = longArrayOf(0, 100, 1000)
            vibrator!!.vibrate(pattern, 0)
        }
        startForeground(1, notification)

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
}