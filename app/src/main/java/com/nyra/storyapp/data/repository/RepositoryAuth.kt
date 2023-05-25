package com.nyra.storyapp.data.repository

import com.nyra.storyapp.data.remot.ApiResponse
import com.nyra.storyapp.data.remot.autentikasi.BodyAuth
import com.nyra.storyapp.data.remot.autentikasi.BodyLogin
import com.nyra.storyapp.data.remot.autentikasi.ResponseAuth
import com.nyra.storyapp.data.source.DataSourceAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryAuth @Inject constructor(private val dataSourceAuth: DataSourceAuth) {
    suspend fun userRegister(bodyAuth: BodyAuth): Flow<ApiResponse<Response<ResponseAuth>>> {
        return dataSourceAuth.userRegister(bodyAuth).flowOn(Dispatchers.IO)
    }

    suspend fun userLogin(bodyLogin: BodyLogin): Flow<ApiResponse<ResponseAuth>> {
        return dataSourceAuth.userLogin(bodyLogin).flowOn(Dispatchers.IO)
    }
}
