package com.example.youtubeproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.youtubeproject.data.model.LikeList
import com.example.youtubeproject.data.model.VideoModel
import com.example.youtubeproject.domain.DeleteLikeListUseCase
import com.example.youtubeproject.domain.GetLikeListUseCase
import com.example.youtubeproject.presentation.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(private val getLikeListUseCase: GetLikeListUseCase, private val deleteLikeListUseCase: DeleteLikeListUseCase):ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<VideoModel>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getList(id:String){
        getLikeListUseCase(id){
            Log.d("좋아요 뷰모델",it?.likeList.toString())
            if(it == null){
                _uiState.value = UiState.Failure("G:fail to getList")
            }else{
                _uiState.value =UiState.Success(it.likeList)
            }
        }
    }
    fun deleteList(id:String, likeLists: LikeList,videoModel: VideoModel){
        deleteLikeListUseCase(id,videoModel){
            if(it){
                val newlist = likeLists.likeList.toMutableList().also { it.remove(videoModel) }
                _uiState.value = UiState.Success(newlist)
            }else{
                _uiState.value = UiState.Failure("D:fail to deleteList")
            }
        }
    }
}