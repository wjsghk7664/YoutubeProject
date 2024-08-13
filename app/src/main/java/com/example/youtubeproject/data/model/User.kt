package com.example.youtubeproject.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name:String ="",
    val id:String="",
    val password:String="",
    val intro:String=""
):Parcelable