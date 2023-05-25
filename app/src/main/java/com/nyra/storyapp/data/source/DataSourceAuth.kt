package com.nyra.storyapp.data.source

import com.nyra.storyapp.data.remot.ApiResponse
import com.nyra.storyapp.data.remot.autentikasi.BodyAuth
import com.nyra.storyapp.data.remot.autentikasi.BodyLogin
import com.nyra.storyapp.data.remot.autentikasi.ResponseAuth
import com.nyra.storyapp.data.remot.autentikasi.ServiceAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataSourceAuth @Inject constructor(private val serviceAuth: ServiceAuth) {
    suspend fun userRegister(bodyAuth: BodyAuth): Flow<ApiResponse<Response<ResponseAuth>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = serviceAuth.userRegister(bodyAuth)
                if (response.code() == 201) {
                    emit(ApiResponse.Success(response))
                } else if (response.code() == 400) {
                    val bodyError = JSONObject(response.errorBody()!!.string())
                    emit(ApiResponse.Error(bodyError.getString("message")))
                }
            } catch (ex: Exception) {
                emit(ApiResponse.Error(ex.message.toString()))
            }
        }
    }

    suspend fun userLogin(bodyLogin: BodyLogin): Flow<ApiResponse<ResponseAuth>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = serviceAuth.userLogin(bodyLogin)
                if (!response.error) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (ex: Exception) {
                emit(ApiResponse.Error(ex.message.toString()))
            }
        }
    }
}