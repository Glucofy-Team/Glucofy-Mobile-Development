package com.dicoding2.glucofy.ui.alarm

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding2.glucofy.R
import com.dicoding2.glucofy.data.service.AlarmService
import com.dicoding2.glucofy.databinding.ActivityRingBinding
import com.dicoding2.glucofy.model.Alarm
import com.dicoding2.glucofy.ui.factory.ViewModelFactory
import java.util.Calendar

class RingActivity() : AppCompatActivity() {

    private lateinit var alarm: Alarm
    private lateinit var viewModel: AlarmViewModel
    private lateinit var binding: ActivityRingBinding
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRingBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())

        setShowWhenLocked(true)
        setTurnScreenOn(true)

        viewModel = obtainViewModel(this)
        val bundle = intent.getBundleExtra(getString(R.string.bundle_alarm_obj))
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                alarm = bundle.getSerializable(getString(R.string.arg_alarm_obj), Alarm::class.java)!!
            } else {
                @Suppress("DEPRECATION")
                alarm = bundle.getSerializable(getString(R.string.arg_alarm_obj)) as Alarm
            }
        }

        binding.activityRingDismiss.setOnClickListener {dismissAlarm() }

        binding.activityRingSnooze.setOnClickListener { snoozeAlarm() }

        animateClock()
    }

    private fun animateClock() {
        val rotateAnimation = ObjectAnimator.ofFloat(
            binding.activityRingClock,
            "rotation",
            0f,
            30f,
            0f,
            -30f,
            0f
        )
        rotateAnimation.repeatCount = ValueAnimator.INFINITE
        rotateAnimation.setDuration(800)
        rotateAnimation.start()
    }

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onDestroy() {
        super.onDestroy()
        setShowWhenLocked(false)
        setTurnScreenOn(false)
    }

    private fun dismissAlarm() {
        alarm.setStarted(false)
        alarm.cancelAlarm(baseContext)
        viewModel.update(alarm)
        val intentService = Intent(
            applicationContext,
            AlarmService::class.java
        )
        applicationContext.stopService(intentService)
        finish()
    }

    private fun snoozeAlarm() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.MINUTE, 5)

        alarm.hour = calendar[Calendar.HOUR_OF_DAY]
        alarm.minute = calendar[Calendar.MINUTE]
        alarm.title = "Snooze " + alarm.title
        alarm.schedule(applicationContext)

        val intentService = Intent(
            applicationContext,
            AlarmService::class.java
        )
        applicationContext.stopService(intentService)
        finish()
    }

    private fun obtainViewModel(activity: AppCompatActivity): AlarmViewModel {
        val factory = ViewModelFactory.getInstance(application)
        return ViewModelProvider(activity, factory)[AlarmViewModel::class.java]
    }
}