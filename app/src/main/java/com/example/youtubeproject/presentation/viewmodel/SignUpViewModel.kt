package com.example.youtubeproject.presentation.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.youtubeproject.data.model.User
import com.example.youtubeproject.domain.CheckSignUpUseCase
import com.example.youtubeproject.domain.CreateLikeListUseCase
import com.example.youtubeproject.domain.RegisterOrModifyUserDataUseCase
import com.example.youtubeproject.domain.UploadProfileUseCase
import com.example.youtubeproject.presentation.uistate.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val checkSignUpUseCase: CheckSignUpUseCase,
    private val registerOrModifyUserDataUseCase: RegisterOrModifyUserDataUseCase,
    private val uploadProfileUseCase: UploadProfileUseCase,
    private val createLikeListUseCase: CreateLikeListUseCase
):ViewModel() {
    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Init)
    val uiState = _uiState.asStateFlow()

    fun signUp(id:String, password:String, name:String, intro:String, profile:Bitmap?){
        checkSignUpUseCase(id,password,name){ notify,isEnable ->
            if(isEnable){
                createLikeListUseCase(id){ likelist->
                    if(likelist){
                        var user = User(null,name, id, password, intro)
                        if(profile!=null){
                            uploadProfileUseCase(profile,id){ url ->
                                if(url==null){
                                    _uiState.value = SignUpUiState.FailureRegister(notify)
                                }else{
                                    registerOrModifyUserDataUseCase(user.copy(profile = url)){
                                        if(it){
                                            _uiState.value = SignUpUiState.Success
                                        }else{
                                            _uiState.value = SignUpUiState.FailureRegister(notify)
                                        }
                                    }
                                }
                            }
                        }else{
                            registerOrModifyUserDataUseCase(user){
                                if(it){
                                    _uiState.value = SignUpUiState.Success
                                }else{
                                    _uiState.value = SignUpUiState.FailureRegister(notify)
                                }
                            }
                        }
                    }else{
                        _uiState.value = SignUpUiState.FailureRegister("fail to make likelist")
                    }
                }
            }else{
                _uiState.value = SignUpUiState.Failure(notify)
            }
        }
    }
}