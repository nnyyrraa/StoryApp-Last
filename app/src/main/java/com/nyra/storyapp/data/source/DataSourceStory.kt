package com.nyra.storyapp.data.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nyra.storyapp.data.model.DetailStory
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
class DataSourceStory @Inject constructor(private val serviceStory: ServiceStory) : PagingSource<Int, DetailStory>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, DetailStory>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DetailStory> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = serviceStory.getAllStories(position, params.loadSize)

            LoadResult.Page(
                data = responseData.listStory ?: emptyList(),
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position -1,
                nextKey = if (responseData.listStory.isNullOrEmpty()) null else position +1
            )
        } catch (ex: Exception) {
            return LoadResult.Error(ex)
        }
    }

    suspend fun addNewStory(file: MultipartBody.Part, description: RequestBody): Flow<ApiResponse<AddStoryResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = serviceStory.addNewStory(file, description)
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

    suspend fun getLocationWithStory(): Flow<ApiResponse<GetStoryResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = serviceStory.getLocationWithStory(1)
                emit(ApiResponse.Success(response))
            } catch (ex: Exception) {
                Log.d("StoryViewModel", "getLocationWithStory: ${ex.message.toString()} ")
                emit(ApiResponse.Error(ex.message.toString()))
            }
        }
    }
}