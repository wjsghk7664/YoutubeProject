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
import com.example.youtubeproject.data.model.VideoModel
import com.example.youtubeproject.databinding.FragmentPlaylistDetailBinding
import com.example.youtubeproject.presentation.adapter.VideoDetailAdapter
import com.example.youtubeproject.presentation.ui.MainActivity
import com.example.youtubeproject.presentation.ui.dialog.AddVideosDialog
import com.example.youtubeproject.presentation.ui.dialog.DeletePlaylistDialog
import com.example.youtubeproject.presentation.ui.navigation.FragmentTag
import com.example.youtubeproject.presentation.uistate.PlaylistUiState
import com.example.youtubeproject.presentation.uistate.UiState
import com.example.youtubeproject.presentation.viewmodel.LikeVideosViewModel
import com.example.youtubeproject.presentation.viewmodel.PlaylistViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PlaylistDetailFragment : Fragment() {
    private var _binding: FragmentPlaylistDetailBinding? = null
    private val binding get() = _binding!!

    private val playlistViewModel: PlaylistViewModel by activityViewModels()
    private val likeVideosViewModel: LikeVideosViewModel by activityViewModels()

    private val playlistLiveData = MutableLiveData<Playlist?>(null)
    private lateinit var initialPlaylistData: Playlist

    private val userData by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireActivity().intent.getParcelableExtra("userData", User::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            requireActivity().intent.getParcelableExtra("userData")!!
        }
    }

    private val playlistRv = VideoDetailAdapter(
        onItemClick = { videoModel ->
            likeVideosViewModel.videoModel = videoModel
            (requireActivity() as MainActivity).pushFragments(
                VideoDetailFragment(),
                FragmentTag.PlaylistVideoDetailFragment
            )
        },
        onLongItemClick = { videoModel ->
            DeletePlaylistDialog {
                playlistLiveData.value = playlistLiveData.value!!.copy(
                    lists = playlistLiveData.value!!.lists.toMutableList().apply {
                        remove(videoModel)
                    }
                )
            }.show(requireActivity().supportFragmentManager, DeletePlaylistDialog.TAG)
            true
        }
    )

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

        playlistViewModel.savePlaylist(userData.id, initialPlaylistData, playlistLiveData.value!!)

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
            playlistViewModel.uiState.collectLatest {
                when(it) {
                    is PlaylistUiState.Init -> null

                    is PlaylistUiState.GetPlaylistDetailSuccess -> {
                        initialPlaylistData = it.playlist
                        playlistLiveData.value = it.playlist
                        binding.playListTitleTv.text = getString(R.string.playlist_detail_title_text, playlistLiveData.value?.title)
                    }

                    is PlaylistUiState.SavePlaylistSuccess -> {
                        Log.d("PlaylistDetailFragment", "Save Success")
                    }

                    is PlaylistUiState.Failure ->
                        Toast.makeText(requireContext(), getString(R.string.playlist_failure_message), Toast.LENGTH_SHORT).show()

                    is PlaylistUiState.Loading -> null

                    else -> null
                }
            }
        }

        val playlistId = arguments?.getString(PLAYLIST)!!
        playlistViewModel.getPlaylistDetail(userData.id, playlistId)

        with(binding.videoRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            adapter = playlistRv
        }


        playlistLiveData.observe(viewLifecycleOwner) {
            playlistRv.submitList(it?.lists ?: listOf())
            binding.emptyTv.visibility =
                if(it?.lists?.isEmpty() == true) {
                    View.VISIBLE
                } else {
                    View.INVISIBLE
                }
        }

        binding.addVideoBtn.setOnClickListener {
            likeVideosViewModel.getList(userData.id) { result ->
                result?.let {
                    val titles =it.likeList.map { video ->
                        video.snippet.title!!
                    }
                    onGetLikes(it.likeList, titles)
                }

            }
        }
    }

    private fun onGetLikes(likeList:  List<VideoModel>, titles: List<String>) {
        if(titles.isEmpty()) {
            Toast.makeText(requireContext(), "좋아요한 영상이 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        AddVideosDialog.newInstance({ addTo ->
            val added = likeList.filter {
                addTo.contains(it.snippet.title)
            }

            Log.d("PlaylistDetailFragment", "add videos size: ${added.size}, " +
                    "addTo size: ${addTo.size}")
            Log.d("PlaylistDetailFragment", "added: ${playlistLiveData.value!!.lists.joinToString()}, " +
                    "addTo: ${addTo.joinToString()}")

            playlistLiveData.value = playlistLiveData.value!!.copy(
                lists = playlistLiveData.value!!.lists.toMutableList().apply {
                    addAll(added)
                }
            )
        },
            titles
        ).show(
            requireActivity().supportFragmentManager,
            AddVideosDialog.TAG
        )

    }

}