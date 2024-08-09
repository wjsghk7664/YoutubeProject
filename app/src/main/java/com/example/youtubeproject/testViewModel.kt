package com.example.youtubeproject

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeproject.data.remote.GetRemoteDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class testViewModel @Inject constructor(val repository: GetRemoteDataRepository):ViewModel() {

    init {
        viewModelScope.launch {
            Log.d("테스트",repository.getPopularResult(null).getOrNull().toString())
        }
    }

    fun inits(){}
}