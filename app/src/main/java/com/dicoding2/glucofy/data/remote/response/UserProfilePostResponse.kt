package com.dicoding2.glucofy.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserProfilePostResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
