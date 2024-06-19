package com.dicoding2.glucofy.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dicoding2.glucofy.model.Alarm

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alarm: Alarm)

    @Query("SELECT * FROM alarm_table ORDER BY alarmId ASC")
    fun getAlarms(): LiveData<List<Alarm>>

    @Update
    fun update(alarm: Alarm)

    @Query("Delete from alarm_table where alarmId = :alarmID")
    fun delete(alarmID: Int)

    @Query("Delete from alarm_table")
    fun deleteAll()
}