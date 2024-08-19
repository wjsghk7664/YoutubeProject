package com.example.youtubeproject.presentation.ui.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.youtubeproject.R
import com.example.youtubeproject.data.model.Playlist

class PlaylistDetailFragment : Fragment() {

    private val playlist by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(PLAYLIST, Playlist::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(PLAYLIST)!!
        }
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
        private const val PLAYLIST = "PLAYLIST_ITEM"

        @JvmStatic
        fun newInstance(playlist: Playlist) =
            PlaylistDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PLAYLIST, playlist)
                }
            }
    }
}