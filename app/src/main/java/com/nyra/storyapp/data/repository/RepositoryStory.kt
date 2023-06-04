package com.nyra.storyapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.nyra.storyapp.data.model.DetailStory
import com.nyra.storyapp.data.remot.ApiResponse
import com.nyra.storyapp.data.remot.story.AddStoryResponse
import com.nyra.storyapp.data.remot.story.GetStoryResponse
import com.nyra.storyapp.data.remot.story.ServiceStory
import com.nyra.storyapp.data.source.DataSourceStory
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryStory @Inject constructor(private val dataSourceStory: DataSourceStory, private val serviceStory: ServiceStory) {
    fun getAllStories(): LiveData<PagingData<DetailStory>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                DataSourceStory(serviceStory)
            }
        ).liveData
    }

    suspend fun addNewStory(file: MultipartBody.Part, description: RequestBody): Flow<ApiResponse<AddStoryResponse>> {
        return dataSourceStory.addNewStory(file, description)
    }

    suspend fun getLocationWithStory(): Flow<ApiResponse<GetStoryResponse>> {
        return dataSourceStory.getLocationWithStory()
    }
}