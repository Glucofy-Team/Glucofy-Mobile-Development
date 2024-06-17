package com.dicoding2.glucofy.data.remote.response

import com.google.gson.annotations.SerializedName

data class NewFoodResponse(

	@field:SerializedName("gi_category")
	val giCategory: String? = null,

	@field:SerializedName("food_name")
	val foodName: String? = null,

	@field:SerializedName("gi_value")
	val giValue: Int? = null,

	@field:SerializedName("gl_value")
	val glValue: Int? = null,

	@field:SerializedName("carbs")
	val carbs: Int? = null,

	@field:SerializedName("fats")
	val fats: Int? = null,

	@field:SerializedName("proteins")
	val proteins: Int? = null,

	@field:SerializedName("calories")
	val calories: Int? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("gl_category")
	val glCategory: String? = null,

	var isSuccessful: Boolean? = null
) {
	init {
	    isSuccessful = foodName!= null && giValue != null && glValue != null && carbs != null && fats != null && proteins != null && calories != null && category != null && glCategory != null
	}
}
