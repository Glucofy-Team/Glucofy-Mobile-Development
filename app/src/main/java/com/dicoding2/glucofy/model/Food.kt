package com.dicoding2.glucofy.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_table")
data class Food (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "calories_kcal")
    val caloriesKcal: Int,

    @ColumnInfo(name = "proteins_g")
    val proteinsG: Int,

    @ColumnInfo(name = "web_scraper_order")
    val webScraperOrder: String,

    @ColumnInfo(name = "glycemic_index")
    val glycemicIndex: Int,

    @ColumnInfo(name = "carbohydrates_g")
    val carbohydratesG: Int,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "glycemic_load")
    val glycemicLoad: Any?,

    @ColumnInfo(name = "fats_g")
    val fatsG: Any?
)
