package com.dicoding2.glucofy.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageMonthlyEntity
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageTodayEntity
import com.dicoding2.glucofy.data.local.entity.GlucoseAverageWeeklyEntity

@Database(
    entities = [GlucoseAverageTodayEntity::class, GlucoseAverageWeeklyEntity::class, GlucoseAverageMonthlyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GlucofyRoomDatabase : RoomDatabase() {
    abstract fun glucoseAverageMonthlyDao(): GlucoseAverageMonthlyDao
    abstract fun glucoseAverageTodayDao(): GlucoseAverageTodayDao
    abstract fun glucoseAverageWeeklyDao(): GlucoseAverageWeeklyDao

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