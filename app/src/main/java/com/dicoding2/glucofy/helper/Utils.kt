package com.dicoding2.glucofy.helper

import android.content.Context
import android.widget.Toast

fun generateRandomString(length: Int): String {
    val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { chars.random() }
        .joinToString("")
}

fun toast(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}