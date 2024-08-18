package com.example.youtubeproject.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeproject.R
import com.example.youtubeproject.presentation.ui.CategoryAdapter
import com.example.youtubeproject.presentation.ui.CategoryItem
import com.example.youtubeproject.presentation.ui.ChannelCategoryAdapter
import com.example.youtubeproject.presentation.ui.ChannelCategoryItem
import com.example.youtubeproject.presentation.ui.PopularVideosAdapter
import com.example.youtubeproject.presentation.ui.VideoItem
import com.example.youtubeproject.presentation.ui.navigation.FragmentTag

class HomeFragment : Fragment() {

    private lateinit var popularVideosRecyclerView: RecyclerView
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var channelCategoryRecyclerView: RecyclerView

    private lateinit var popularVideosAdapter: PopularVideosAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var channelCategoryAdapter: ChannelCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        popularVideosRecyclerView = view.findViewById(R.id.popular_videos_recycler_view)
        popularVideosRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val popularVideoList = listOf(
            VideoItem(R.drawable.sample_image, R.drawable.sample_image, "가나다라마바사가나다라마바사"),
            VideoItem(R.drawable.sample_image, R.drawable.sample_image, "가나다라마바사가나다라마바사"),
            VideoItem(R.drawable.sample_image, R.drawable.sample_image, "가나다라마바사가나다라마바사")
        )
        popularVideosAdapter = PopularVideosAdapter(popularVideoList)
        popularVideosRecyclerView.adapter = popularVideosAdapter

        categoryRecyclerView = view.findViewById(R.id.category_recycler_view)
        categoryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val categoryList = listOf(
            CategoryItem(R.drawable.sample_image, "가나다라마바사가나다라마바사"),
            CategoryItem(R.drawable.sample_image, "가나다라마바사가나다라마바사"),
            CategoryItem(R.drawable.sample_image, "가나다라마바사가나다라마바사")
        )
        categoryAdapter = CategoryAdapter(categoryList)
        categoryRecyclerView.adapter = categoryAdapter

        channelCategoryRecyclerView = view.findViewById(R.id.channel_category_recycler_view)
        channelCategoryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val channelCategoryList = listOf(
            ChannelCategoryItem(R.drawable.sample_image, "TVN Sports"),
            ChannelCategoryItem(R.drawable.sample_image, "KBS Sports"),
            ChannelCategoryItem(R.drawable.sample_image, "N Sports"),
            ChannelCategoryItem(R.drawable.sample_image, "N Sports"),
            ChannelCategoryItem(R.drawable.sample_image, "N Sports"),
            ChannelCategoryItem(R.drawable.sample_image, "N Sports")

        )
        channelCategoryAdapter = ChannelCategoryAdapter(channelCategoryList)
        channelCategoryRecyclerView.adapter = channelCategoryAdapter

        return view
    }
}
