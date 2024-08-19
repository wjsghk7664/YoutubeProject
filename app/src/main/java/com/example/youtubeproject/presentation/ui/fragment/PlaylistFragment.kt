package com.example.youtubeproject.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.youtubeproject.R
import com.example.youtubeproject.data.model.Playlist
import com.example.youtubeproject.databinding.FragmentPlaylistBinding
import com.example.youtubeproject.presentation.ui.MainActivity
import com.example.youtubeproject.presentation.ui.navigation.FragmentTag
import com.example.youtubeproject.presentation.uistate.LoginUiState
import com.example.youtubeproject.presentation.uistate.PlaylistUiState
import com.example.youtubeproject.presentation.viewmodel.LoginViewModel
import com.example.youtubeproject.presentation.viewmodel.PlaylistViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PlaylistFragment : Fragment() {
    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: PlaylistViewModel by viewModels()

    private val playlistsLiveData = MutableLiveData(mutableListOf<Playlist>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("PlaylistFragment", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("PlaylistFragment", "onCreateView")
        _binding = FragmentPlaylistBinding.inflate(inflater)
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

    private fun initView() {
        lifecycleScope.launch {
            viewmodel.uiState.collectLatest {
                when(it) {
                    is PlaylistUiState.Init -> null

                    is PlaylistUiState.GetPlaylistsSuccess ->
                        playlistsLiveData.value?.addAll(it.playlists)

                    is PlaylistUiState.CreatePlaylistSuccess ->
                        Toast.makeText(requireContext(), getString(R.string.create_playlist_success_message), Toast.LENGTH_SHORT).show()

                    is PlaylistUiState.SavePlaylistSuccess ->
                        Toast.makeText(requireContext(), getString(R.string.save_playlist_success_message), Toast.LENGTH_SHORT).show()

                    is PlaylistUiState.Failure ->
                        Toast.makeText(requireContext(), getString(R.string.playlist_failure_message), Toast.LENGTH_SHORT).show()

                    is PlaylistUiState.Loading -> null
                }
            }
        }

        playlistsLiveData.observe(viewLifecycleOwner) {
            //TODO: Update Playlist RecyclerView Adapter
        }

        binding.addPlaylistBtn.setOnClickListener {
            (requireActivity() as MainActivity).pushFragments(PlaylistDetailFragment(), FragmentTag.PlaylistVideoDetailFragment)
        }
    }

}