package com.dicoding2.glucofy.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageMonthlyEntity
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageTodayEntity
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageWeeklyEntity
import com.dicoding2.glucofy.data.local.entity.GlucoseDataEntity

@Database(
    entities = [GlucoseAverageTodayEntity::class, GlucoseAverageWeeklyEntity::class, GlucoseAverageMonthlyEntity::class, GlucoseDataEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GlucofyRoomDatabase : RoomDatabase() {
    abstract fun glucoseAverageMonthlyDao(): GlucoseAverageMonthlyDao
    abstract fun glucoseAverageTodayDao(): GlucoseAverageTodayDao
    abstract fun glucoseAverageWeeklyDao(): GlucoseAverageWeeklyDao
    abstract fun glucoseDataDao(): GlucoseDataDao

    companion object {
        @Volatile
        private var INSTANCE: GlucofyRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): GlucofyRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    GlucofyRoomDatabase::class.java, "glucofy_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}