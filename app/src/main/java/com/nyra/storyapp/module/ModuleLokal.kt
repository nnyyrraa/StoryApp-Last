package com.nyra.storyapp.module

import android.content.Context
import androidx.room.Room
import com.nyra.storyapp.data.lokal.DatabaseStoryApp
import com.nyra.storyapp.data.lokal.dao.DaoStory
import com.nyra.storyapp.utils.ValConst.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleLokal {
    @Singleton
    @Provides
    fun databaseProvide(@ApplicationContext context: Context): DatabaseStoryApp {
        return Room.databaseBuilder(context, DatabaseStoryApp::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun daoStoryProvide(database: DatabaseStoryApp): DaoStory = database.getDaoStory()
}