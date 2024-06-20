package com.dicoding2.glucofy.data.remote.response

import com.google.gson.annotations.SerializedName

data class TodayFoodResponse(

	@field:SerializedName("data")
	val data: List<TodayFoodListItem>,

	@field:SerializedName("totalCalories")
	val totalCalories: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class TodayFoodListItem(

	@field:SerializedName("gIndex")
	val gIndex: Int,

	@field:SerializedName("foodName")
	val foodName: String,

	@field:SerializedName("gLoad")
	val gLoad: Any,

	@field:SerializedName("datetime")
	val datetime: String,

	@field:SerializedName("carbs")
	val carbs: Any,

	@field:SerializedName("fats")
	val fats: Any,

	@field:SerializedName("proteins")
	val proteins: Any,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("giCategory")
	val giCategory: String,

	@field:SerializedName("glCategory")
	val glCategory: String,

	@field:SerializedName("calories")
	val calories: Int,

	@field:SerializedName("category")
	val category: String
)
