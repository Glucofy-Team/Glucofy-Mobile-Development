package com.dicoding2.glucofy.data.remote.response

import com.google.gson.annotations.SerializedName

data class NewFoodResponse(

	@field:SerializedName("gi_category")
	val giCategory: String? = null,

	@field:SerializedName("food_name")
	val foodName: String? = null,

	@field:SerializedName("gi_value")
	val giValue: String? = null,

	@field:SerializedName("gl_value")
	val glValue: String? = null,

	@field:SerializedName("carbs")
	val carbs: Any? = null,

	@field:SerializedName("fats")
	val fats: Double? = null,

	@field:SerializedName("proteins")
	val proteins: Double? = null,

	@field:SerializedName("calories")
	val calories: Double? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("gl_category")
	val glCategory: String? = null,

	var isSuccessful: Boolean? = null
)
//{
//	init {
//	    isSuccessful = foodName!= null && giValue != null && glValue != null && carbs != null && fats != null && proteins != null && calories != null && category != null && glCategory != null
//	}
//}
