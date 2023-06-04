package com.nyra.storyapp.data.lokal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nyra.storyapp.data.lokal.entity.EntityStory

@Dao
interface DaoStory {
    @Query("SELECT * FROM list_story")
    fun getAllStory(): List<EntityStory>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStory(storyList: List<EntityStory>)

    @Query("DELETE FROM list_story")
    suspend fun deleteAllStory()
}