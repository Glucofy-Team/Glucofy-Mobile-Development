package com.dicoding2.glucofy.data.remote.retrofit

import com.dicoding2.glucofy.data.remote.response.AddGlucosaResponse
import com.dicoding2.glucofy.data.remote.response.FoodResponse
import com.dicoding2.glucofy.data.remote.response.GlucosaResponse
import com.dicoding2.glucofy.data.remote.response.LoginResponse
import com.dicoding2.glucofy.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("auth/login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("auth/register")
    fun postRegister(
        @Field("firstName") firstname: String,
        @Field("lastName") lastname: String,
        @Field("phoneNumber") phonenumber: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("gender") gender: String,
        @Field("weight") weight: String,
        @Field("height") height: String,
        @Field("age") age: String,
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("tracker/add")
    fun postGlocosa(
        @Field("glucose") glucose: String,
        @Field("condition") condition: String,
        @Field("notes") notes: String,
        @Field("datetime") datetime: String
    ): Call<AddGlucosaResponse>

    @GET("tracker")
    suspend fun getGlucosa(

    ): GlucosaResponse

    @GET("food")
    suspend fun getFood(
        @Query("name") name: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): FoodResponse
}