package com.dicoding2.glucofy.data.remote.response

import com.google.gson.annotations.SerializedName

data class MyFoodResponse(

	@field:SerializedName("data")
	val myFoodListItem: List<MyFoodListItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class MyFoodListItem(

	@field:SerializedName("gIndex")
	val gIndex: Int? = null,

	@field:SerializedName("foodName")
	val foodName: String? = null,

	@field:SerializedName("gLoad")
	val gLoad: Any? = null,

	@field:SerializedName("datetime")
	val datetime: String? = null,

	@field:SerializedName("carbs")
	val carbs: Any? = null,

	@field:SerializedName("fats")
	val fats: Any? = null,

	@field:SerializedName("proteins")
	val proteins: Any? = null,

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
