package com.dicoding2.glucofy.data.remote.response

import com.google.gson.annotations.SerializedName

data class GlucosaResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("today")
	val today: Today? = null,

	@field:SerializedName("averages")
	val averages: Averages? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class Averages(

	@field:SerializedName("daily")
	val daily: List<DailyItem?>? = null,

	@field:SerializedName("monthly")
	val monthly: List<MonthlyItem?>? = null,

	@field:SerializedName("weeklyThisMonth")
	val weeklyThisMonth: List<WeeklyThisMonthItem?>? = null,

	@field:SerializedName("weekly")
	val weekly: List<WeeklyItem?>? = null
)

data class DataItem(

	@field:SerializedName("glucose")
	val glucose: Int? = null,

	@field:SerializedName("condition")
	val condition: String? = null,

	@field:SerializedName("datetime")
	val datetime: String? = null,

	@field:SerializedName("notes")
	val notes: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class DailyItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("glucose")
	val glucose: Int? = null
)

data class WeeklyThisMonthItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("glucose")
	val glucose: Int? = null
)

data class MonthlyItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("glucose")
	val glucose: Int? = null
)

data class WeeklyItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("glucose")
	val glucose: Int? = null
)

data class Today(

	@field:SerializedName("average")
	val average: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null
)
