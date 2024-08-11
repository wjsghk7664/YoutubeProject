package com.example.youtubeproject

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeproject.data.Repository.CacheDataRepository
import com.example.youtubeproject.data.Repository.RemoteDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class testViewModel @Inject constructor(
    private val cacheDataRepository: CacheDataRepository,
    private val remoteDataRepository: RemoteDataRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            cacheDataRepository.deleteCategoryVideo()
            var a = cacheDataRepository.getCategoryVideoResult("26",null).getOrNull()

            if(a==null){
                Log.d("A체크 시작","1체")
                a=remoteDataRepository.getCategoryVideoResult("26",null).getOrNull()
            }
            val nextPage=a?.nextPageToken
            Log.d("A페이지 체크",nextPage.toString())
            val b=remoteDataRepository.getCategoryVideoResult("26",nextPage).getOrNull()
            Log.d("A체크",a?.items.toString())
            Log.d("B체크",b?.items.toString())
            cacheDataRepository.insertCategoryVideo(b!!,"26")
        }
    }

    fun inits() {}
}