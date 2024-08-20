package com.example.youtubeproject.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.youtubeproject.data.model.LikeList
import com.example.youtubeproject.data.model.User
import com.example.youtubeproject.data.model.VideoModel
import com.example.youtubeproject.databinding.FragmentMyPageBinding
import com.example.youtubeproject.presentation.adapter.LikeListAdapter
import com.example.youtubeproject.presentation.ui.MainActivity
import com.example.youtubeproject.presentation.ui.navigation.FragmentTag
import com.example.youtubeproject.presentation.uistate.UiState
import com.example.youtubeproject.presentation.viewmodel.LikeVideosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageFragment : Fragment() {

    private var _binding :FragmentMyPageBinding? = null
    private val binding get() = _binding!!

    val viewModel:LikeVideosViewModel by activityViewModels()

    private lateinit var user: User

    private lateinit var likeListAdapter: LikeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyPageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUiState()
        initView()

    }
    fun getUiState() = with(binding){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest {
                when(it){
                    is UiState.Loading -> mypageProgressbar.visibility = View.VISIBLE
                    is UiState.Failure -> null
                    is UiState.Init -> null
                    is UiState.Success -> {
                        Log.d("좋아요 체크",it.data.toString())
                        mypageProgressbar.visibility = View.GONE
                        Log.d("MyPageFragment", "sublist: ${it.data.size}")
                        Log.d("MyPageFragment", "sublist: ${it.data.map {
                            it.snippet.title
                        }.joinToString()}")
                        likeListAdapter.submitList(it.data)
                    }
                }
            }
        }
    }

    fun initView() = with(binding){
        user = requireActivity().intent.getParcelableExtra("userData")?:User()

        viewModel.getList(user.id)
        Log.d("좋아요 아이디",user.id)

        likeListAdapter = LikeListAdapter(onClickOpen,onClickDelete)
        rvFavoriteVideos.adapter = likeListAdapter
        rvFavoriteVideos.layoutManager = GridLayoutManager(requireActivity(), 2)

        Glide.with(requireActivity())
            .load(user.profile)
            .apply{
                into(ivProfile)
                into(circleIv)
            }

        tvUserName.setText(user.name)
        tvUserDescription.setText(user.intro)
    }

    private val onClickOpen:(VideoModel) -> Unit = { videoModel ->
        Log.d("MyPageFragment", "Title: ${videoModel.snippet.title}")
        viewModel.videoModel = videoModel
        Log.d("MyPageFragment", "ViewModel.video: ${viewModel.videoModel!!.snippet.title}")
        (requireActivity() as MainActivity).pushFragments(
            VideoDetailFragment(),
            FragmentTag.MyPageVideoDetailFragment
        )
    }

    private val onClickDelete:(LikeList,VideoModel) -> Unit = { likeList,videoModel ->
        viewModel.deleteList(user.id,likeList,videoModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyPageFragment()
    }
}