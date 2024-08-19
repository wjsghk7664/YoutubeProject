package com.example.youtubeproject

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.youtubeproject.data.Repository.RemoteUserPlayListRepository
import com.example.youtubeproject.data.model.Playlist
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class testActivity : AppCompatActivity() {

    @Inject lateinit var remoteUserPlayListRepository: RemoteUserPlayListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val testInstance = Playlist(1L,"aaa", listOf())
        remoteUserPlayListRepository.CreatePlayLists("test"){
            if(it){
                remoteUserPlayListRepository.AddPlayList("test", testInstance){
                    if(it){
                        remoteUserPlayListRepository.getAllPlayLists("test"){
                            if(it!=null){
                                Log.d("테스트", it.toString())
                                remoteUserPlayListRepository.deletePlayList("test",testInstance){
                                    if(it){
                                        remoteUserPlayListRepository.DeletePlayLists("test"){
                                            Log.d("테스트 최종",it.toString())
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}