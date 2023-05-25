package com.nyra.storyapp.data.mapper

import com.nyra.storyapp.data.lokal.entity.EntityStory
import com.nyra.storyapp.data.model.DetailStory

fun storyToEntityStory(story: DetailStory): EntityStory {
    return EntityStory(
        id = story.id,
        photoUrl = story.photoUrl
    )
}