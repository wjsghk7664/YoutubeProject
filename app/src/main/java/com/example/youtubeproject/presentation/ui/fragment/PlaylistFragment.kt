package com.example.youtubeproject.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.youtubeproject.R
import com.example.youtubeproject.data.model.Playlist
import com.example.youtubeproject.databinding.FragmentPlaylistBinding
import com.example.youtubeproject.presentation.adapter.PlaylistsAdapter
import com.example.youtubeproject.presentation.adapter.deco.PlaylistAdapterDecoration
import com.example.youtubeproject.presentation.ui.MainActivity
import com.example.youtubeproject.presentation.ui.dialog.CreatePlaylistDialog
import com.example.youtubeproject.presentation.ui.dialog.DeletePlaylistDialog
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

    private val viewmodel: PlaylistViewModel by activityViewModels()

    private val playlistsLiveData = MutableLiveData(mutableListOf<Playlist>())

    private val playlistRv = PlaylistsAdapter(
        onItemClick = { playlist ->
            (requireActivity() as MainActivity).pushFragments(
                PlaylistDetailFragment.newInstance(playlist),
                FragmentTag.PlaylistDetailFragment
            )
        },
        onLongItemClick = { playlist ->
            DeletePlaylistDialog {
                viewmodel.deletePlaylist(playlist)
            }.show(requireActivity().supportFragmentManager, CreatePlaylistDialog.TAG)
            true
        }
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initialize()
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

                    is PlaylistUiState.GetPlaylistsSuccess -> {
                        playlistsLiveData.value = it.playlists.toMutableList()
                    }

                    is PlaylistUiState.CreatePlaylistSuccess -> {
                        viewmodel.getPlaylists()
                        Toast.makeText(requireContext(), getString(R.string.create_playlist_success_message), Toast.LENGTH_SHORT).show()
                    }

                    is PlaylistUiState.DeletePlaylistSuccess ->
                        Toast.makeText(requireContext(), getString(R.string.delete_playlist_success_message), Toast.LENGTH_SHORT).show()

                    is PlaylistUiState.SavePlaylistSuccess ->
                        Toast.makeText(requireContext(), getString(R.string.save_playlist_success_message), Toast.LENGTH_SHORT).show()

                    is PlaylistUiState.Failure ->
                        Toast.makeText(requireContext(), getString(R.string.playlist_failure_message), Toast.LENGTH_SHORT).show()

                    is PlaylistUiState.Loading -> null
                }
            }
        }

        //Recycler View
        with(binding.playlistRecyclerView) {
            adapter = playlistRv
            addItemDecoration(PlaylistAdapterDecoration())
        }

        playlistsLiveData.observe(viewLifecycleOwner) {
            playlistRv.submitList(it.toList())
            Log.d("PlaylistFragment", "playlistRv Changed: ${playlistRv.currentList.size}")

            if(playlistRv.currentList.isEmpty()) {
                binding.emptyTv.visibility = View.VISIBLE
            } else {
                binding.emptyTv.visibility = View.INVISIBLE
            }
        }

        binding.addPlaylistBtn.setOnClickListener {
            CreatePlaylistDialog { title ->
                viewmodel.createPlaylist(title)
            }.show(requireActivity().supportFragmentManager, CreatePlaylistDialog.TAG)
        }
    }

    private fun initialize() {
        viewmodel.getPlaylists()
    }
}