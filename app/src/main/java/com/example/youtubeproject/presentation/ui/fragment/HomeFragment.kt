package com.example.youtubeproject.presentation.ui.fragment

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
import com.example.youtubeproject.R
import com.example.youtubeproject.databinding.FragmentHomeBinding
import com.example.youtubeproject.presentation.adapter.CustomSpinnerAdapter
import com.example.youtubeproject.presentation.ui.CategoryAdapter
import com.example.youtubeproject.presentation.ui.ChannelCategoryAdapter
import com.example.youtubeproject.presentation.ui.ChannelCategoryItem
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

        // 인기 비디오
        homeViewModel.popularVideos.observe(viewLifecycleOwner) { videoItems ->
            popularVideosAdapter.submitList(videoItems)
        }

        // 카테고리 비디오
        homeViewModel.categoryVideos.observe(viewLifecycleOwner) { videoItems ->
            categoryAdapter.submitList(videoItems)
        }

        // 카테고리 채널
        homeViewModel.categoryChannels.observe(viewLifecycleOwner) { channelItems ->
            channelCategoryAdapter.submitList(channelItems)
        }

        // 초기 가져오기 인기 비디오
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

        val categoryNames = categories.map { it.second }

        val adapter = CustomSpinnerAdapter(requireContext(), categoryNames)
        binding.categorySpinner.adapter = adapter

        val defaultPosition = categories.indexOfFirst { it.first == "10" }
        if (defaultPosition != -1) {
            binding.categorySpinner.setSelection(defaultPosition)
        }

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
