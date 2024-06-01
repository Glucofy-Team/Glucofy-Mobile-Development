package com.dicoding2.glucofy.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("user")
	val user: String,

	@field:SerializedName("status")
	val status: Int,

	@field:SerializedName("token")
	val token: String
)
