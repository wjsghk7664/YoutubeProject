package com.example.youtubeproject.presentation.ui.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.youtubeproject.presentation.ui.fragment.HomeFragment
import com.example.youtubeproject.presentation.ui.fragment.MyPageFragment
import com.example.youtubeproject.presentation.ui.fragment.PlaylistFragment
import com.example.youtubeproject.presentation.ui.fragment.SearchFragment

class MainViewPager(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    private lateinit var viewPagerAdapter: MainViewPager
    private val fragments = listOf(HomeFragment(), SearchFragment(), PlaylistFragment(), MyPageFragment())

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int) = fragments[position]
}