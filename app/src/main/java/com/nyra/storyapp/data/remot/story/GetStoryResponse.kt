package com.nyra.storyapp.data.remot.story

import com.google.gson.annotations.SerializedName
import com.nyra.storyapp.data.model.DetailStory

data class GetStoryResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("listStory")
    val listStory: List<DetailStory>
)
