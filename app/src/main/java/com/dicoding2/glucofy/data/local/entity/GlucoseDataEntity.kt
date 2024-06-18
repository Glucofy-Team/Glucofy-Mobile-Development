package com.dicoding2.glucofy.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "glucose_data")
class GlucoseDataEntity (
    @PrimaryKey val id: String,

    @ColumnInfo(name = "date")
    val date: String? = null,

    @ColumnInfo(name = "glucose")
    val glucose: Int? = null,

    @ColumnInfo(name = "condition")
    val condition: String? = null,
)