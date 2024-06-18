package com.dicoding2.glucofy.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding2.glucofy.data.local.entity.GlucoseDataEntity

@Dao
interface GlucoseDataDao {
    @Query("SELECT * from glucose_data")
    fun getAllDataGlucose(): LiveData<List<GlucoseDataEntity>>

    @Query("DELETE FROM glucose_data")
    suspend fun deleteAll()

    @Query("DELETE FROM glucose_data WHERE id = :id")
    suspend fun deleteById(id: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGlucose(glucose: List<GlucoseDataEntity>)
}