package com.example.youtubeproject.presentation.ui.fragment

import VideoItem
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.youtubeproject.R
import com.example.youtubeproject.data.model.User
import com.example.youtubeproject.databinding.FragmentVideoDetailBinding
import com.example.youtubeproject.presentation.viewmodel.VideoDetailViewModel

class VideoDetailFragment : Fragment() {

    private var _binding : FragmentVideoDetailBinding? = null
    private val binding get() = _binding!!

    private val videoDetailViewModel: VideoDetailViewModel by activityViewModels()

    private val userData by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireActivity().intent.getParcelableExtra("userData", User::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            requireActivity().intent.getParcelableExtra("userData")!!
        }
    }
    private val videoModel by lazy {
        videoDetailViewModel.videoModel!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoDetailBinding.inflate(inflater, container, false)
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
        Glide.with(this)
            .load(videoModel.snippet.thumbnails?.high?.url)
            .error(R.drawable.logo)
            .into(binding.ivThumbnail)

        with(binding) {
            tvVideoTitle.text = videoModel.snippet.title
            tvVideoDescription.text = videoModel.snippet.description

            ibLike.setOnClickListener {
                videoDetailViewModel.addLike(userData.id) { isSuccess ->
                    //TODO: do Something after success
                }
            }
        }
    }


}