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
import com.example.youtubeproject.databinding.FragmentPlaylistDetailBinding
import com.example.youtubeproject.presentation.viewmodel.PlaylistViewModel

class PlaylistDetailFragment : Fragment() {
    private var _binding: FragmentPlaylistDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlaylistViewModel by activityViewModels()

    //TODO: 초기화 방법 위험할수도?
    private val playlist by lazy {
        val playlistId = arguments?.getString(PLAYLIST)!!
        val userId = requireActivity().intent.getStringExtra("userData")!!
        viewModel.getPlaylistDetail(userId, playlistId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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