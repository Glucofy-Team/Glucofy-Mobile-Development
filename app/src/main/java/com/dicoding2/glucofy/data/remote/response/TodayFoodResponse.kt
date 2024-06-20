package com.dicoding2.glucofy.data.remote.response

import com.google.gson.annotations.SerializedName

data class TodayFoodResponse(

	@field:SerializedName("data")
	val todayFoodListItem: List<TodayFoodListItem?>? = null,

	@field:SerializedName("totalCalories")
	val totalCalories: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class TodayFoodListItem(

	@field:SerializedName("gIndex")
	val gIndex: Int? = null,

	@field:SerializedName("foodName")
	val foodName: String? = null,

	@field:SerializedName("gLoad")
	val gLoad: Int? = null,

	@field:SerializedName("datetime")
	val datetime: String? = null,

	@field:SerializedName("carbs")
	val carbs: Int? = null,

	@field:SerializedName("fats")
	val fats: Int? = null,

	@field:SerializedName("proteins")
	val proteins: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("giCategory")
	val giCategory: String? = null,

	@field:SerializedName("glCategory")
	val glCategory: String? = null,

	@field:SerializedName("calories")
	val calories: Int? = null,

	@field:SerializedName("category")
	val category: String? = null
)
