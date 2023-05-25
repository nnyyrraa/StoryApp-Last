package com.nyra.storyapp.point.registrasi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyra.storyapp.data.remot.ApiResponse
import com.nyra.storyapp.data.remot.autentikasi.BodyAuth
import com.nyra.storyapp.data.remot.autentikasi.ResponseAuth
import com.nyra.storyapp.data.repository.RepositoryAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repositoryAuth: RepositoryAuth): ViewModel() {
    fun userRegister(bodyAuth: BodyAuth): LiveData<ApiResponse<Response<ResponseAuth>>> {
        val result = MutableLiveData<ApiResponse<Response<ResponseAuth>>>()
        viewModelScope.launch {
            repositoryAuth.userRegister(bodyAuth).collect {
                result.postValue(it)
            }
        }
        return result
    }
}