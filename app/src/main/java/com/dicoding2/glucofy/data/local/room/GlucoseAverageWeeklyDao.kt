package com.dicoding2.glucofy.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageTodayEntity
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageWeeklyEntity

@Dao
interface GlucoseAverageWeeklyDao {
    @Query("SELECT * from glucose_average_weekly")
    fun getAllGlucoseAverageWeekly(): LiveData<List<GlucoseAverageWeeklyEntity>>

    @Query("DELETE FROM glucose_average_weekly")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGlucose(glucose: List<GlucoseAverageWeeklyEntity>)
}