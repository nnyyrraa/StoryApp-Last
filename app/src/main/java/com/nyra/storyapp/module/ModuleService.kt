package com.nyra.storyapp.module

import com.nyra.storyapp.data.remot.autentikasi.ServiceAuth
import com.nyra.storyapp.data.remot.story.ServiceStory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ModuleService {
    @Provides
    fun serviceAuthProvide(retrofit: Retrofit): ServiceAuth = retrofit.create(ServiceAuth::class.java)

    @Provides
    fun serviceStoryProvide(retrofit: Retrofit): ServiceStory = retrofit.create(ServiceStory::class.java)
}