package com.dicoding2.glucofy.data.remote.retrofit

data class NewFoodRequest(
    val foodName: String,
    val category: String,
    val calories: Number,
    val proteins: Number,
    val carbs: Number,
    val fats: Number
)