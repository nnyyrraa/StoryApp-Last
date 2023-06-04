package com.nyra.storyapp.data.remot.story

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ServiceStory {
    @GET("stories")
    suspend fun getAllStories(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): GetStoryResponse

    @Multipart
    @POST("stories")
    suspend fun addNewStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): AddStoryResponse

    @GET("stories")
    suspend fun getLocationWithStory(
        @Query("location") location: Int,
    ) : GetStoryResponse
}