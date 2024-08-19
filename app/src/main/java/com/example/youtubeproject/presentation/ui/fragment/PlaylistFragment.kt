package com.example.youtubeproject.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.youtubeproject.R
import com.example.youtubeproject.databinding.FragmentPlaylistBinding
import com.example.youtubeproject.presentation.ui.MainActivity
import com.example.youtubeproject.presentation.ui.navigation.FragmentTag


class PlaylistFragment : Fragment() {
    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("PlaylistFragment", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        Log.d("PlaylistFragment", "onCreateView")
        _binding = FragmentPlaylistBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addPlaylistBtn.setOnClickListener {
            (requireActivity() as MainActivity).pushFragments(PlaylistDetailFragment(), FragmentTag.PlaylistVideoDetailFragment)
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("PlaylistFragment", "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d("PlaylistFragment", "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("PlaylistFragment", "onStop")
    }


}