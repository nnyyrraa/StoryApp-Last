package com.nyra.storyapp.data.model

import com.google.gson.annotations.SerializedName

data class UserStory(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("token")
    val token: String
)
