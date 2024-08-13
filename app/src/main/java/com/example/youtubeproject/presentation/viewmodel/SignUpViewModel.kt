package com.example.youtubeproject.presentation.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeproject.data.model.User
import com.example.youtubeproject.domain.CacheLoginDataUseCase
import com.example.youtubeproject.domain.CheckSignUpUseCase
import com.example.youtubeproject.domain.RegisterOrModifyUserDataUseCase
import com.example.youtubeproject.domain.UploadProfileUseCase
import com.example.youtubeproject.presentation.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val checkSignUpUseCase: CheckSignUpUseCase,
    private val registerOrModifyUserDataUseCase: RegisterOrModifyUserDataUseCase,
    private val uploadProfileUseCase: UploadProfileUseCase
):ViewModel() {
    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Init)
    val uiState = _uiState.asStateFlow()

    fun signUp(id:String, password:String, name:String, intro:String, profile:Bitmap?){
        checkSignUpUseCase(id,password,name){ notify,isEnable ->
            if(isEnable){
                var user = User(null,name, id, password, intro)
                if(profile!=null){
                    uploadProfileUseCase(profile,id){ uri ->
                        if(uri==null){
                            _uiState.value = SignUpUiState.FailureRegister(notify)
                        }else{
                            registerOrModifyUserDataUseCase(user.copy(profile = uri)){
                                if(it){
                                    _uiState.value = SignUpUiState.Success
                                }else{
                                    _uiState.value = SignUpUiState.FailureRegister(notify)
                                }
                            }
                        }
                    }
                }
                registerOrModifyUserDataUseCase(user){
                    if(it){
                        _uiState.value = SignUpUiState.Success
                    }else{
                        _uiState.value = SignUpUiState.FailureRegister(notify)
                    }
                }
            }else{
                _uiState.value = SignUpUiState.Failure(notify)
            }
        }
    }
}