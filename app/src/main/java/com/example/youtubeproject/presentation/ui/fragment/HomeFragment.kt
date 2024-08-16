package com.example.youtubeproject.presentation.ui.fragment

import PopularVideosAdapter
import VideoItem
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeproject.R
import com.example.youtubeproject.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var popularVideosRecyclerView: RecyclerView
    private lateinit var popularVideosAdapter: PopularVideosAdapter

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        popularVideosRecyclerView = view.findViewById(R.id.popular_videos_recycler_view)
        popularVideosRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        popularVideosAdapter = PopularVideosAdapter()
        popularVideosRecyclerView.adapter = popularVideosAdapter

        homeViewModel.popularVideos.observe(viewLifecycleOwner) { categoryVideoModel ->
            val videoItems = categoryVideoModel.items.map { videoResponse ->
                val mainImageUrl = videoResponse.snippet?.thumbnails?.high?.url
                val profileImageUrl = videoResponse.snippet?.thumbnails?.high?.url

                VideoItem(
                    mainImageUrl = mainImageUrl,
                    profileImageUrl = profileImageUrl,
                    description = videoResponse.snippet?.title ?: ""
                )
            }

            popularVideosAdapter.submitList(videoItems)
        }

        homeViewModel.loadPopularVideos()

        return view
    }
}
