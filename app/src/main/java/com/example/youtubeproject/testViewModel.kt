package com.example.youtubeproject

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeproject.data.Repository.GetLocalDataRepository
import com.example.youtubeproject.data.Repository.GetRemoteDataRepository
import com.example.youtubeproject.data.model.CategoryVideoModel
import com.example.youtubeproject.data.model.CategoryVideoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class testViewModel @Inject constructor(
    private val getLocalDataRepository: GetLocalDataRepository,
    private val getRemoteDataRepository: GetRemoteDataRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            getLocalDataRepository.deleteCategoryVideo()
            var a = getLocalDataRepository.getCategoryVideoResult("26",null).getOrNull()

            if(a==null){
                Log.d("A체크 시작","1체")
                a=getRemoteDataRepository.getCategoryVideoResult("26",null).getOrNull()
            }
            val nextPage=a?.nextPageToken
            Log.d("A페이지 체크",nextPage.toString())
            val b=getRemoteDataRepository.getCategoryVideoResult("26",nextPage).getOrNull()
            Log.d("A체크",a?.items.toString())
            Log.d("B체크",b?.items.toString())
            getLocalDataRepository.insertCategoryVideo(b!!,"26")
        }
    }

    fun inits() {}
}