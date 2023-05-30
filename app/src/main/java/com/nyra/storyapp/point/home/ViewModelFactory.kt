package com.nyra.storyapp.point.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nyra.storyapp.data.repository.RepositoryStory
import com.nyra.storyapp.point.story.StoryViewModel

class ViewModelFactory(private val repositoryStory: RepositoryStory) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(StoryViewModel::class.java) -> {
                StoryViewModel(repositoryStory) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}