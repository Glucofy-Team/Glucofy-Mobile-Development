package com.dicoding2.glucofy.data.remote.retrofit

import com.dicoding2.glucofy.data.remote.response.AddGlucosaResponse
import com.dicoding2.glucofy.data.remote.response.DetailFoodResponse
import com.dicoding2.glucofy.data.remote.response.FoodResponse
import com.dicoding2.glucofy.data.remote.response.GlucosaResponse
import com.dicoding2.glucofy.data.remote.response.LoginResponse
import com.dicoding2.glucofy.data.remote.response.RegisterResponse
import com.dicoding2.glucofy.data.remote.response.SuccessResponse
import com.dicoding2.glucofy.data.remote.response.UserProfilePostResponse
import com.dicoding2.glucofy.data.remote.response.UserProfileResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.PUT

interface ApiService {
    @GET("/user/profile")
    suspend fun getUserProfile(

    ): UserProfileResponse

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
    @PUT("user/update")
    fun postUpdateUser(
        @FieldMap fields: Map<String, String>,
    ): Call<UserProfilePostResponse>

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

    @DELETE("tracker/delete/{id}")
    suspend fun deleteGlucosaById(
        @Path("id") id: String
    ): SuccessResponse

    @GET("food")
    suspend fun getFood(
        @Query("name") name: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 2
    ): FoodResponse

    @GET("food/detail/{id}")
    suspend fun getFoodDetail(
        @Path("id") foodID: String
    ): DetailFoodResponse

}