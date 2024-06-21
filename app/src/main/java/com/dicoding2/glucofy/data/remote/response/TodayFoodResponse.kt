package com.dicoding2.glucofy.data.remote.response

import com.google.gson.annotations.SerializedName

data class TodayFoodResponse(

	@field:SerializedName("data")
	val data: List<TodayFoodListItem?>? = null,

	@field:SerializedName("totalCalories")
	val totalCalories: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class TodayFoodListItem(

	@field:SerializedName("gIndex")
	val gIndex: Double? = null,

	@field:SerializedName("foodName")
	val foodName: String? = null,

	@field:SerializedName("gLoad")
	val gLoad: Double? = null,

	@field:SerializedName("datetime")
	val datetime: String? = null,

	@field:SerializedName("carbs")
	val carbs: Double? = null,

	@field:SerializedName("fats")
	val fats: Double? = null,

	@field:SerializedName("proteins")
	val proteins: Double? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("giCategory")
	val giCategory: String? = null,

	@field:SerializedName("glCategory")
	val glCategory: String? = null,

	@field:SerializedName("calories")
	val calories: Double? = null,

	@field:SerializedName("category")
	val category: String? = null
)
