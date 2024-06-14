package com.dicoding2.glucofy.data.remote.response

import com.google.gson.annotations.SerializedName

data class FoodResponse(
	@field:SerializedName("translated name glycemic data")
	val foodListItem: List<FoodListItem>
)
data class FoodListItem(

	@field:SerializedName("glycemic_load")
	val glycemicLoad: Any,

	@field:SerializedName("calories (kcal)")
	val caloriesKcal: Int,

	@field:SerializedName("proteins (g)")
	val proteinsG: Int,

	@field:SerializedName("web-scraper-order")
	val webScraperOrder: String,

	@field:SerializedName("glycemic_index")
	val glycemicIndex: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("carbohydrates (g)")
	val carbohydratesG: Int,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("fats (g)")
	val fatsG: Any
)
