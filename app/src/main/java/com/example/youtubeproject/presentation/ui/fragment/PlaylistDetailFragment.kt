package com.example.youtubeproject.presentation.ui.fragment

import android.os.Build
import android.os.Bundle
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
import com.example.youtubeproject.data.model.User
import com.example.youtubeproject.data.model.VideoModel
import com.example.youtubeproject.databinding.FragmentPlaylistDetailBinding
import com.example.youtubeproject.presentation.adapter.VideoDetailAdapter
import com.example.youtubeproject.presentation.ui.MainActivity
import com.example.youtubeproject.presentation.ui.dialog.AddVideosDialog
import com.example.youtubeproject.presentation.ui.dialog.DeletePlaylistDialog
import com.example.youtubeproject.presentation.ui.navigation.FragmentTag
import com.example.youtubeproject.presentation.uistate.PlaylistUiState
import com.example.youtubeproject.presentation.viewmodel.PlaylistViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PlaylistDetailFragment : Fragment() {
    private var _binding: FragmentPlaylistDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlaylistViewModel by activityViewModels()

    private val playlistLiveData = MutableLiveData<Playlist?>(null)

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
            //TODO: Change PushFragment
            (requireActivity() as MainActivity).pushFragments(
                VideoDetailFragment(),
                FragmentTag.PlaylistVideoDetailFragment
            )
        },
        onLongItemClick = { videoModel ->
            DeletePlaylistDialog {
                //playlist update 후 submitList, getPlaylistDetail
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

        viewModel.savePlaylist(userData.id, playlistLiveData.value!!)

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
                        playlistLiveData.value = it.playlist
                        binding.playListTitleTv.text = getString(R.string.playlist_detail_title_text, playlistLiveData.value?.title)
                    }

                    is PlaylistUiState.Failure ->
                        Toast.makeText(requireContext(), getString(R.string.playlist_failure_message), Toast.LENGTH_SHORT).show()

                    is PlaylistUiState.Loading -> null

                    else -> null
                }
            }
        }

        val playlistId = arguments?.getString(PLAYLIST)!!
        viewModel.getPlaylistDetail(userData.id, playlistId)

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
            val titleList = listOf<String>()        //TODO: change to titles of my favorite videos.
            if(titleList.isEmpty()) {
                Toast.makeText(requireContext(), "좋아요한 영상이 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AddVideosDialog.newInstance({ result ->
                    val added = listOf<VideoModel>().filter {
                        result.contains(it.snippet.title)
                    }
                    playlistLiveData.value = playlistLiveData.value!!.copy(
                        lists = playlistLiveData.value!!.lists.toMutableList().apply {
                            addAll(added)
                        }
                    )
                },
                titleList
            ).show(
                requireActivity().supportFragmentManager,
                AddVideosDialog.TAG
            )
        }
    }


}