package com.nyra.storyapp.data.source

import com.nyra.storyapp.data.lokal.dao.DaoStory
import com.nyra.storyapp.data.mapper.storyToEntityStory
import com.nyra.storyapp.data.remot.ApiResponse
import com.nyra.storyapp.data.remot.story.AddStoryResponse
import com.nyra.storyapp.data.remot.story.GetStoryResponse
import com.nyra.storyapp.data.remot.story.ServiceStory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataSourceStory @Inject constructor(
    private val daoStory: DaoStory,
    private val serviceStory: ServiceStory
){
    suspend fun getAllStory(token: String): Flow<ApiResponse<GetStoryResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = serviceStory.getAllStory(token)
                if (!response.error) {
                    daoStory.deleteAllStory()
                    val listStory = response.listStory.map {
                        storyToEntityStory(it)
                    }
                    daoStory.insertStory(listStory)
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (ex: Exception) {
                emit(ApiResponse.Error(ex.message.toString()))
            }
        }
    }

    suspend fun addNewStory(token: String, file: MultipartBody.Part, description: RequestBody): Flow<ApiResponse<AddStoryResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = serviceStory.addNewStory(token, file, description)
                if (!response.error) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (ex: Exception) {
                emit(ApiResponse.Error(ex.message.toString()))
            }
        }
    }
}