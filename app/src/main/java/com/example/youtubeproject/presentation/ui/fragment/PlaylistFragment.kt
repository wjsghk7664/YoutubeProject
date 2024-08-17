package com.example.youtubeproject.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.youtubeproject.R


class PlaylistFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("PlaylistFragment", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("PlaylistFragment", "onCreateView")
        return inflater.inflate(R.layout.fragment_playlist, container, false)
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