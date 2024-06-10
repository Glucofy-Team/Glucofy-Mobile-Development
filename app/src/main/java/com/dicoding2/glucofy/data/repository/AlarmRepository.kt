package com.dicoding2.glucofy.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding2.glucofy.data.database.alarmdatabase.AlarmDao
import com.unique.simplealarmclock.data.AlarmDatabase
import com.unique.simplealarmclock.model.Alarm

class AlarmRepository(application: Application?) {
    private lateinit var  alarmDao: AlarmDao
    private val alarmsLiveData: LiveData<List<Alarm>>

    init {
        val db: AlarmDatabase? = application?.let { AlarmDatabase.getDatabase(it) }
        if (db != null) {
            alarmDao = db.alarmDao()!!
        }
        alarmsLiveData = alarmDao.getAlarms()
    }

    fun insert(alarm: Alarm) {
        AlarmDatabase.databaseWriteExecutor.execute {
            alarmDao.insert(alarm)
        }
    }

    fun getAlarmsLiveData(): LiveData<List<Alarm>> {
        return alarmsLiveData
    }

    fun update(alarm: Alarm) {
        AlarmDatabase.databaseWriteExecutor.execute {
            alarmDao.update(alarm)
        }
    }

    fun delete(alarmId: Int) {
        AlarmDatabase.databaseWriteExecutor.execute {
            alarmDao.delete(alarmId)
        }
    }
}