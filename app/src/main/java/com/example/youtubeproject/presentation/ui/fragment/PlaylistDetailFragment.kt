package com.example.youtubeproject.presentation.ui.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.youtubeproject.R
import com.example.youtubeproject.data.model.Playlist
import com.example.youtubeproject.presentation.viewmodel.PlaylistViewModel

class PlaylistDetailFragment : Fragment() {

    private val viewModel: PlaylistViewModel by activityViewModels()

    //TODO: 초기화 방법 잘못됨. 다시 생각해보기
    private val playlist by lazy {
        val id = arguments?.getString(PLAYLIST)!!
        viewModel.getPlaylistDetail(id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("PlaylistDetailFragment", "title: ${playlist.title}")
        return inflater.inflate(R.layout.fragment_playlist_detail, container, false)
    }

    companion object {
        private const val PLAYLIST = "PLAYLIST_ID"

        @JvmStatic
        fun newInstance(playlistId: String) =
            PlaylistDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(PLAYLIST, playlistId)
                }
            }
    }
}