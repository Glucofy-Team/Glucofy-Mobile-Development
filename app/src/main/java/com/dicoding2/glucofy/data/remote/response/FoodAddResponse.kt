package com.dicoding2.glucofy.data.remote.response

import com.google.gson.annotations.SerializedName

data class FoodAddResponse(

	@field:SerializedName("foodId")
	val foodId: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)
