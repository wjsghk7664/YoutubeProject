package com.example.youtubeproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeproject.domain.CacheLoginDataUseCase
import com.example.youtubeproject.domain.CheckLoginUseCase
import com.example.youtubeproject.domain.GetCacheLoginDataUseCase
import com.example.youtubeproject.presentation.uistate.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val checkLoginUseCase: CheckLoginUseCase, private val cacheLoginDataUseCase: CacheLoginDataUseCase, private val getCacheLoginDataUseCase: GetCacheLoginDataUseCase):ViewModel(){
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Init)
    val uiState = _uiState.asStateFlow()

    fun loginCheck(id:String, password:String, isCache:Boolean){
        checkLoginUseCase(id,password){ user ->
            viewModelScope.launch {
                if(user!=null){
                    var result=true
                    if(isCache){
                        result = cacheLoginDataUseCase(id,password)?:false
                    }
                    if(result){
                        _uiState.emit(LoginUiState.Success(user))
                    }else{
                        _uiState.emit(LoginUiState.FailureCache)
                    }
                }else{
                    _uiState.emit(LoginUiState.Failure)
                }
            }
        }
    }

    fun autoLogin(){
        val result = getCacheLoginDataUseCase()
        if(result!=null){
            result.let {
                val id = it.first
                val password = it.second
                if(id!=null&&password!=null){
                    loginCheck(id,password,true)
                }else{
                    viewModelScope.launch {
                        _uiState.emit(LoginUiState.AutoLoginFailure)
                    }
                }
            }
        }else{
            viewModelScope.launch {
                _uiState.emit(LoginUiState.AutoLoginFailure)
            }
        }


    }
}