package com.dicoding2.glucofy.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageMonthlyEntity
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageTodayEntity

@Dao
interface GlucoseAverageMonthlyDao {
    @Query("SELECT * from glucose_average_monthly")
    fun getAllGlucoseAverageMonthly(): LiveData<List<GlucoseAverageMonthlyEntity>>

    @Query("DELETE FROM glucose_average_monthly")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGlucose(glucose: List<GlucoseAverageMonthlyEntity>)
}