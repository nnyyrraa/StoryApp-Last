package com.nyra.storyapp.data.remot.autentikasi

import com.google.gson.annotations.SerializedName

data class BodyLogin(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
