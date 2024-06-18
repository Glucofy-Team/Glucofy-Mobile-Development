package com.dicoding2.glucofy.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailFoodResponse(

	@field:SerializedName("data")
	val detailFood: DetailFood,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class DetailFood(

	@field:SerializedName("gIndex")
	val gIndex: Int,

	@field:SerializedName("foodName")
	val foodName: String?,

	@field:SerializedName("gLoad")
	val gLoad: Int,

	@field:SerializedName("datetime")
	val datetime: Datetime,

	@field:SerializedName("carbs")
	val carbs: Int,

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
	val category: String?
)

data class Datetime(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int,

	@field:SerializedName("_seconds")
	val seconds: Int
)
