package com.nyra.storyapp.data.remot.story

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ServiceStory {
    @GET("story")
    suspend fun getAllStory(
        @Header("Authorization") token: String
    ): GetStoryResponse

    @Multipart
    @POST("story")
    suspend fun addNewStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): AddStoryResponse
}