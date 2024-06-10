package com.dicoding2.glucofy.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageTodayEntity

@Dao
interface GlucoseAverageTodayDao {
    @Query("SELECT * from glucose_average_today")
    fun getAllGlucoseAverageToday(): LiveData<List<GlucoseAverageTodayEntity>>

    @Query("DELETE FROM glucose_average_today")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGlucose(glucose: List<GlucoseAverageTodayEntity>?)
}