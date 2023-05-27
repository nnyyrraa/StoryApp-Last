package com.nyra.storyapp.point.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyra.storyapp.data.remot.ApiResponse
import com.nyra.storyapp.data.remot.story.GetStoryResponse
import com.nyra.storyapp.data.repository.RepositoryStory
import kotlinx.coroutines.launch

class MapViewModel(private val repositoryStory: RepositoryStory): ViewModel() {
    fun getLocationWithStory(): LiveData<ApiResponse<GetStoryResponse>> {
        val result = MutableLiveData<ApiResponse<GetStoryResponse>>()
        viewModelScope.launch {
            repositoryStory.getLocationWithStory().collect {
                result.postValue(it)
            }
        }
        return result
    }
}