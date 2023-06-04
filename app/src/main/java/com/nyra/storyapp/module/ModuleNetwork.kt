package com.nyra.storyapp.module

import android.content.Context
import com.nyra.storyapp.BuildConfig.BASE_URL
import com.nyra.storyapp.utils.ManagerSession
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class ModuleNetwork {
    @Provides
    fun okhttpclientProvides(managerSession: ManagerSession): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(Interceptor { chain ->
                val req = chain.request()
                if (managerSession.getToken != null){
                    val headers = req.newBuilder()
                        .addHeader("Authorization", "Bearer ${managerSession.getToken}")
                        .build()
                    chain.proceed(headers)
                } else {
                    chain.proceed(req)
                }
            })
            .connectTimeout(150, TimeUnit.SECONDS)
            .readTimeout(150, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun retrofitProvides(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    fun provideSessionManager(@ApplicationContext context: Context) = ManagerSession(context)
}
