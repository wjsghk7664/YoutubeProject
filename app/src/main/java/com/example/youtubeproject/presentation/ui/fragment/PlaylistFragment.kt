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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtubeproject.R
import com.example.youtubeproject.data.model.Playlist
import com.example.youtubeproject.data.model.User
import com.example.youtubeproject.databinding.FragmentPlaylistBinding
import com.example.youtubeproject.presentation.adapter.PlaylistsAdapter
import com.example.youtubeproject.presentation.adapter.deco.PlaylistAdapterDecoration
import com.example.youtubeproject.presentation.ui.MainActivity
import com.example.youtubeproject.presentation.ui.dialog.CreatePlaylistDialog
import com.example.youtubeproject.presentation.ui.dialog.DeletePlaylistDialog
import com.example.youtubeproject.presentation.ui.navigation.FragmentTag
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
    private val userData by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireActivity().intent.getParcelableExtra("userData", User::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            requireActivity().intent.getParcelableExtra("userData")!!
        }
    }

    private val playlistRv = PlaylistsAdapter(
        onItemClick = { playlist ->
            (requireActivity() as MainActivity).pushFragments(
                PlaylistDetailFragment.newInstance(playlist.id),
                FragmentTag.PlaylistDetailFragment
            )
        },
        onLongItemClick = { playlist ->
            DeletePlaylistDialog {
                viewmodel.deletePlaylist(userData.id, playlist)
            }.show(requireActivity().supportFragmentManager, DeletePlaylistDialog.TAG)
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
                        Log.d("PlaylistFragment", "GetPlaylistsSuccess: ${it.playlists.size}")
                        playlistsLiveData.value = it.playlists.toMutableList()
                    }

                    is PlaylistUiState.CreatePlaylistSuccess -> {
                        viewmodel.getPlaylists(userData.id)
                        Toast.makeText(requireContext(), getString(R.string.create_playlist_success_message), Toast.LENGTH_SHORT).show()
                    }

                    is PlaylistUiState.DeletePlaylistSuccess -> {
                        viewmodel.getPlaylists(userData.id)
                        Toast.makeText(requireContext(), getString(R.string.delete_playlist_success_message), Toast.LENGTH_SHORT).show()
                    }

                    is PlaylistUiState.SavePlaylistSuccess ->
                        Toast.makeText(requireContext(), getString(R.string.save_playlist_success_message), Toast.LENGTH_SHORT).show()

                    is PlaylistUiState.Failure ->
                        Toast.makeText(requireContext(), getString(R.string.playlist_failure_message), Toast.LENGTH_SHORT).show()

                    is PlaylistUiState.Loading -> null

                    else -> null
                }
            }
        }

        //Recycler View
        with(binding.playlistRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            adapter = playlistRv
            addItemDecoration(PlaylistAdapterDecoration())
        }

        playlistsLiveData.observe(viewLifecycleOwner) {
            playlistRv.submitList(it.toList())

            binding.emptyTv.visibility =
                if(it.isEmpty()) {
                    View.VISIBLE
                } else {
                    View.INVISIBLE
                }
        }

        binding.addPlaylistBtn.setOnClickListener {
            CreatePlaylistDialog { title ->
                viewmodel.createPlaylist(userData.id, title)
            }.show(requireActivity().supportFragmentManager, CreatePlaylistDialog.TAG)
        }
    }

    private fun initialize() {
        viewmodel.getPlaylists(userData.id)
    }
}