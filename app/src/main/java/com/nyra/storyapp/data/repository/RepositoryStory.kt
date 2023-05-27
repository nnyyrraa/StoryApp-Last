package com.nyra.storyapp.data.repository

import com.nyra.storyapp.data.remot.ApiResponse
import com.nyra.storyapp.data.remot.story.AddStoryResponse
import com.nyra.storyapp.data.remot.story.GetStoryResponse
import com.nyra.storyapp.data.source.DataSourceStory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryStory @Inject constructor(private val dataSourceStory: DataSourceStory) {
    suspend fun getAllStory(token: String): Flow<ApiResponse<GetStoryResponse>> {
        return dataSourceStory.getAllStory(token).flowOn(Dispatchers.IO)
    }

    suspend fun addNewStory(token: String, file: MultipartBody.Part, description: RequestBody): Flow<ApiResponse<AddStoryResponse>> {
        return dataSourceStory.addNewStory(token, file, description)
    }

    suspend fun getLocationWithStory(): Flow<ApiResponse<GetStoryResponse>> {
        return dataSourceStory.getLocationWithStory()
    }
}