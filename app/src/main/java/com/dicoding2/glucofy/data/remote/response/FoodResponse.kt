package com.dicoding2.glucofy.data.remote.response

import com.google.gson.annotations.SerializedName

data class FoodResponse(

	@field:SerializedName("data")
	val foodListItem: List<FoodListItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class FoodListItem(

	@field:SerializedName("gIndex")
	val gIndex: Int,

	@field:SerializedName("gLoad")
	val gLoad: Any,

	@field:SerializedName("foodName")
	val foodName: String?,

	@field:SerializedName("fats")
	val fats: Any,

	@field:SerializedName("carbs")
	val carbs: Any,

	@field:SerializedName("proteins")
	val proteins: Any,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("giCategory")
	val giCategory: String,

	@field:SerializedName("glCategory")
	val glCategory: String,

	@field:SerializedName("calories")
	val calories: Any,

	@field:SerializedName("category")
	val category: String?
)
