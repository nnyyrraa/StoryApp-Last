package com.nyra.storyapp.data.remot.autentikasi

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ServiceAuth {
    @POST("register")
    suspend fun userRegister(
        @Body bodyAuth: BodyAuth
    ): Response<ResponseAuth>

    @POST("login")
    suspend fun userLogin(
        @Body bodyLogin: BodyLogin
    ): ResponseAuth
}