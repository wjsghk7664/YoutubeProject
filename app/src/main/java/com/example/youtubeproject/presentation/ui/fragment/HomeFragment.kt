package com.example.youtubeproject.presentation.ui.fragment
//
import PopularVideosAdapter
import VideoItem
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtubeproject.databinding.FragmentHomeBinding
import com.example.youtubeproject.presentation.ui.CategoryAdapter
import com.example.youtubeproject.presentation.ui.ChannelCategoryAdapter
import com.example.youtubeproject.presentation.ui.ChannelCategoryItem
import com.example.youtubeproject.presentation.ui.navigation.FragmentTag
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeproject.R
import com.example.youtubeproject.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var popularVideosAdapter: PopularVideosAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var channelCategoryAdapter: ChannelCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPopularVideosRecyclerView()
        setupCategoryRecyclerView()
        setupChannelCategoryRecyclerView()
        setupCategorySpinner()

        homeViewModel.popularVideos.observe(viewLifecycleOwner) { categoryVideoModel ->
            val videoItems = categoryVideoModel.items.map { videoResponse ->
                VideoItem(
                    mainImageUrl = videoResponse.snippet?.thumbnails?.high?.url,
                    profileImageUrl = videoResponse.snippet?.thumbnails?.high?.url,
                    description = videoResponse.snippet?.title ?: ""
                )
            }
            popularVideosAdapter.submitList(videoItems)
        }

        homeViewModel.categoryVideos.observe(viewLifecycleOwner) { categoryVideoModel ->
            val videoItems = categoryVideoModel.items.map { videoResponse ->
                VideoItem(
                    mainImageUrl = videoResponse.snippet?.thumbnails?.high?.url,
                    profileImageUrl = videoResponse.snippet?.thumbnails?.high?.url,
                    description = videoResponse.snippet?.title ?: ""
                )
            }
            categoryAdapter.submitList(videoItems)
        }

        homeViewModel.categoryChannels.observe(viewLifecycleOwner) { categoryChannelModel ->
            if (categoryChannelModel != null && categoryChannelModel.items != null) {
                val channelItems = categoryChannelModel.items.mapNotNull { channelResponse ->
                    ChannelCategoryItem(
                        imageResId = channelResponse.snippet?.thumbnails?.high?.url,
                        channelName = channelResponse.snippet?.title ?: ""
                    )
                }
                channelCategoryAdapter.submitList(channelItems)
            } else {
                Log.e("홈프라그먼트", "null")
            }
        }


        homeViewModel.loadPopularVideos()
    }

    private fun setupPopularVideosRecyclerView() {
        popularVideosAdapter = PopularVideosAdapter()
        binding.popularVideosRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularVideosAdapter
        }
    }

    private fun setupCategoryRecyclerView() {
        categoryAdapter = CategoryAdapter()
        binding.categoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }

    private fun setupChannelCategoryRecyclerView() {
        channelCategoryAdapter = ChannelCategoryAdapter(emptyList())
        binding.channelCategoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = channelCategoryAdapter
        }
    }

    private fun setupCategorySpinner() {
        val categories = listOf(
            "1" to "Film & Animation",
            "2" to "Autos & Vehicles",
            "10" to "Music",
            "15" to "Pets & Animals",
            "17" to "Sports",
        )

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories.map { it.second })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = adapter

        binding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val categoryId = categories[position].first
                homeViewModel.loadCategoryVideos(categoryId)

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
