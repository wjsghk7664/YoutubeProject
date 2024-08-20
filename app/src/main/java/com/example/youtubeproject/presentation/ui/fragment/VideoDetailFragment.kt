package com.example.youtubeproject.presentation.ui.fragment

import VideoItem
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.youtubeproject.databinding.FragmentVideoDetailBinding

class VideoDetailFragment : Fragment() {

    private var _binding : FragmentVideoDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item: VideoItem? = arguments?.getParcelable(ARG_ITEM)

        Glide.with(requireContext()).load(item?.mainImageUrl).into(binding.ivThumbnail)
        binding.tvVideoTitle.text = item?.title
        binding.tvVideoDescription.text = item?.description

        binding.ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        private const val ARG_ITEM = "arg_item"

        fun newInstance(item:VideoItem) : VideoDetailFragment {
            val frag = VideoDetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_ITEM, item)
            frag.arguments = args
            return frag
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}