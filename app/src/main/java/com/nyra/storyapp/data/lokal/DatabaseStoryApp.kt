package com.nyra.storyapp.data.lokal

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nyra.storyapp.data.lokal.dao.DaoStory
import com.nyra.storyapp.data.lokal.entity.EntityStory

@Database(
    entities = [EntityStory::class],
    version = 1,
    exportSchema = false
)
abstract class DatabaseStoryApp: RoomDatabase() {
    abstract fun getDaoStory(): DaoStory
}