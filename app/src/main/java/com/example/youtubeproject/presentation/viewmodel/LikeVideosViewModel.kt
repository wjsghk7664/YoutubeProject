package com.example.youtubeproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.youtubeproject.data.model.LikeList
import com.example.youtubeproject.data.model.VideoModel
import com.example.youtubeproject.domain.AddLikeUseCase
import com.example.youtubeproject.domain.DeleteLikeListUseCase
import com.example.youtubeproject.domain.GetLikeListUseCase
import com.example.youtubeproject.domain.LogoutUseCase
import com.example.youtubeproject.presentation.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LikeVideosViewModel @Inject constructor(
    private val addLikeUseCase: AddLikeUseCase,
    private val getLikeListUseCase: GetLikeListUseCase,
    private val deleteLikeListUseCase: DeleteLikeListUseCase,
    private val logoutUseCase: LogoutUseCase
):ViewModel() {

    var videoModel: VideoModel? = null

    private val _uiState = MutableStateFlow<UiState<List<VideoModel>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun addLike(userId: String, callback: (Boolean) -> (Unit)) {
        runCatching {
            addLikeUseCase.invoke(userId, videoModel!!, callback)
        }.onFailure {
            Log.d("VideoDetailViewModel", "addLike onFailure")
        }
    }

    fun getList(id: String, callback: (LikeList?) -> Unit) {
        getLikeListUseCase(id, callback)
    }

    fun getList(id:String){
        getLikeListUseCase(id){
            Log.d("좋아요 뷰모델",it?.likeList.toString())
            if(it == null){
                Log.d("좋아요 뷰모델","실패")
                _uiState.value = UiState.Failure("G:fail to getList")
            }else{
                Log.d("좋아요 뷰모델","성공")
                _uiState.value = UiState.Success(it.likeList)
            }
        }
    }
    fun deleteList(id:String,videoModel: VideoModel){
        deleteLikeListUseCase(id,videoModel){
            if(it) {
                getList(id)
            }
        }
    }

    fun logout():Boolean{
        return logoutUseCase()
    }
}