package com.example.youtubeproject.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.youtubeproject.R
import com.example.youtubeproject.databinding.ActivityMainBinding
import com.example.youtubeproject.presentation.ui.fragment.HomeFragment
import com.example.youtubeproject.presentation.ui.fragment.PlaylistFragment
import com.example.youtubeproject.presentation.ui.fragment.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        binding.bottomMenuBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_menu_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.bottom_menu_search -> {
                    replaceFragment(SearchFragment())
                    true
                }
                R.id.bottom_playlist_home -> {
                    replaceFragment(PlaylistFragment())
                    true
                }
                R.id.bottom_menu_my_page -> {
                  //  replaceFragment(MyPageFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }
}
