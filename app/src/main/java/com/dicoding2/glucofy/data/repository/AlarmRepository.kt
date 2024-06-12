package com.dicoding2.glucofy.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding2.glucofy.data.local.room.AlarmDao
import com.dicoding2.glucofy.data.local.room.AlarmDatabase
import com.dicoding2.glucofy.data.remote.retrofit.ApiService
import com.dicoding2.glucofy.model.Alarm

class AlarmRepository(
    private val alarmDatabase: AlarmDatabase,
) {
    private val alarmDao: AlarmDao?
    private val alarmsLiveData: LiveData<List<Alarm>>

    init {
        val db: AlarmDatabase = alarmDatabase
        alarmDao = db.alarmDao()
        alarmsLiveData = alarmDao?.getAlarms() ?: MutableLiveData(emptyList())
    }

    fun insert(alarm: Alarm) {
        AlarmDatabase.databaseWriteExecutor.execute {
            alarmDao?.insert(alarm)
        }
    }

    fun getAlarmsLiveData(): LiveData<List<Alarm>> {
        return alarmsLiveData
    }

    fun update(alarm: Alarm) {
        AlarmDatabase.databaseWriteExecutor.execute {
            alarmDao?.update(alarm)
        }
    }

    fun delete(alarmId: Int) {
        AlarmDatabase.databaseWriteExecutor.execute {
            alarmDao?.delete(alarmId)
        }
    }

    companion object {
        @Volatile
        private var instance: AlarmRepository? = null
        fun getInstance(
            alarmDatabase: AlarmDatabase,
        ): AlarmRepository =
            instance ?: synchronized(this) {
                instance ?: AlarmRepository(alarmDatabase)
        }.also { instance = it }
    }
}
