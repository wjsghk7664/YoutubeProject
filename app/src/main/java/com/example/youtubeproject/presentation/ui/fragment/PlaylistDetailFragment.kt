package com.example.youtubeproject.presentation.ui.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.youtubeproject.R
import com.example.youtubeproject.data.model.Playlist
import com.example.youtubeproject.databinding.FragmentPlaylistDetailBinding
import com.example.youtubeproject.presentation.uistate.PlaylistUiState
import com.example.youtubeproject.presentation.viewmodel.PlaylistViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PlaylistDetailFragment : Fragment() {
    private var _binding: FragmentPlaylistDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlaylistViewModel by activityViewModels()

    private lateinit var playlist: Playlist

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
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

    private fun initView() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest {
                when(it) {
                    is PlaylistUiState.Init -> null

                    is PlaylistUiState.GetPlaylistDetailSuccess -> {
                        playlist = it.playlist
                        binding.playListTitleTv.text = getString(R.string.playlist_detail_title_text, playlist.title)
                    }

                    is PlaylistUiState.Failure ->
                        Toast.makeText(requireContext(), getString(R.string.playlist_failure_message), Toast.LENGTH_SHORT).show()

                    is PlaylistUiState.Loading -> null

                    else -> null
                }
            }
        }

        val playlistId = arguments?.getString(PLAYLIST)!!
        val userId = requireActivity().intent.getStringExtra("userData")!!
        viewModel.getPlaylistDetail(userId, playlistId)


    }
}