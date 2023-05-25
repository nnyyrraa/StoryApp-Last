package com.nyra.storyapp.data.remot.autentikasi

import com.google.gson.annotations.SerializedName
import com.nyra.storyapp.data.model.UserStory

data class ResponseAuth(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("loginResult")
    val loginResult: UserStory
)
