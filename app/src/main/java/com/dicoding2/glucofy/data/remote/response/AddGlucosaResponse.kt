package com.dicoding2.glucofy.data.remote.response

import com.google.gson.annotations.SerializedName

data class AddGlucosaResponse(

	@field:SerializedName("trackerId")
	val trackerId: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)
