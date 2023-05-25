package com.nyra.storyapp.point.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyra.storyapp.data.remot.ApiResponse
import com.nyra.storyapp.data.remot.autentikasi.BodyLogin
import com.nyra.storyapp.data.remot.autentikasi.ResponseAuth
import com.nyra.storyapp.data.repository.RepositoryAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repositoryAuth: RepositoryAuth): ViewModel() {
    fun userLogin(bodyLogin: BodyLogin): LiveData<ApiResponse<ResponseAuth>> {
        val result = MutableLiveData<ApiResponse<ResponseAuth>>()
        viewModelScope.launch {
            repositoryAuth.userLogin(bodyLogin).collect {
                result.postValue(it)
            }
        }
        return result
    }
}