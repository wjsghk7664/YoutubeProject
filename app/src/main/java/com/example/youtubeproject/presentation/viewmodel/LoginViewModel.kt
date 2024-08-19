package com.example.youtubeproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeproject.data.model.User
import com.example.youtubeproject.domain.CacheLoginDataUseCase
import com.example.youtubeproject.domain.CheckLoginUseCase
import com.example.youtubeproject.domain.GetCacheLoginDataUseCase
import com.example.youtubeproject.presentation.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val checkLoginUseCase: CheckLoginUseCase, private val cacheLoginDataUseCase: CacheLoginDataUseCase, private val getCacheLoginDataUseCase: GetCacheLoginDataUseCase):ViewModel(){
    private val _uiState = MutableStateFlow<UiState<User>>(UiState.Init)
    val uiState = _uiState.asStateFlow()

    fun loginCheck(id:String, password:String, isCache:Boolean){
        checkLoginUseCase(id,password){ user ->
            if(user!=null){
                var result=true
                if(isCache){
                    result = cacheLoginDataUseCase(id,password)?:false
                }
                if(result){
                    _uiState.value = UiState.Success(user)
                }else{
                    _uiState.value = UiState.Failure("cachefail")
                }
            }else{
                _uiState.value = UiState.Failure("fail")
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
                    _uiState.value = UiState.Failure("failAutoLogin")
                }
            }
        }else{
            _uiState.value = UiState.Failure("failAutoLogin")
        }


    }
}