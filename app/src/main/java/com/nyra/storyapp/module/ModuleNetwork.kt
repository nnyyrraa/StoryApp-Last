package com.nyra.storyapp.module

import com.nyra.storyapp.BuildConfig.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    companion object {
        private var token:String? = null
    }
    @Provides
    fun okhttpclientProvides(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            /*.addInterceptor(Interceptor { chain ->
                val req = chain.request()
                if (token != null){
                    val headers = req.newBuilder()
                        .addHeader("Authorization", "bearer $token")
                        .build()
                    chain.proceed(headers)
                } else {
                    chain.proceed(req)
                }
            })*/
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
}
