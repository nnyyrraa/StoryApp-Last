package com.nyra.storyapp.data.lokal.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_story")
data class EntityStory(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo(name = "photoUrl")
    val photoUrl: String
)