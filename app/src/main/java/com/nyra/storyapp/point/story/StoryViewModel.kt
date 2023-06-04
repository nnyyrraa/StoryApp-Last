package com.nyra.storyapp.point.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nyra.storyapp.data.model.DetailStory
import com.nyra.storyapp.data.remot.ApiResponse
import com.nyra.storyapp.data.remot.story.AddStoryResponse
import com.nyra.storyapp.data.repository.RepositoryStory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(private val repositoryStory: RepositoryStory): ViewModel() {
    fun addNewStory(file: MultipartBody.Part, description: RequestBody): LiveData<ApiResponse<AddStoryResponse>> {
        val result = MutableLiveData<ApiResponse<AddStoryResponse>>()
        viewModelScope.launch {
            repositoryStory.addNewStory(file, description).collect {
                result.postValue(it)
            }
        }
        return result
    }

    val story: LiveData<PagingData<DetailStory>> =
        repositoryStory.getAllStories().cachedIn(viewModelScope)
}
