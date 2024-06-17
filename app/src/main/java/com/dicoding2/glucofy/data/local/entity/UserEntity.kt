package com.dicoding2.glucofy.data.local.entity

data class UserEntity (
    var token: String? = null,
    var phoneNumber: String? = null,
    var gender: String? = null,
    var weight: String? = null,
    var age: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var height: String? = null,
    var email: String? = null
)