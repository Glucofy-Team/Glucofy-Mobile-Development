package com.dicoding2.glucofy.data.local.entity

import retrofit2.http.Field

data class RegisterEntity (
    var first_name: String? = null,
    var last_name: String? = null,
    var phone_number: String? = null,
    var email: String? = null,
    var password: String? = null
)