package com.dicoding2.glucofy.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class Data(

	@field:SerializedName("firstName")
	val firstName: String,

	@field:SerializedName("lastName")
	val lastName: String,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("weight")
	val weight: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("age")
	val age: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("height")
	val height: Int
)
