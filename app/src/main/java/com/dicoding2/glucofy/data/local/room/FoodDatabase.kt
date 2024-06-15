package com.dicoding2.glucofy.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dicoding2.glucofy.helper.AnyTypeConverter
import com.dicoding2.glucofy.model.Food


@Database(entities = [Food::class], version = 1, exportSchema = false)
@TypeConverters(AnyTypeConverter::class)
abstract class FoodDatabase : RoomDatabase(){
    companion object {
        @Volatile
        private var INSTANCE: FoodDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FoodDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java, "food_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { FoodDatabase.INSTANCE = it }
            }
        }
    }
}