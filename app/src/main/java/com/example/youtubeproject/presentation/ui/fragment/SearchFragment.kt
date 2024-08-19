package com.example.youtubeproject.presentation.ui.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtubeproject.databinding.FragmentSearchBinding
import com.example.youtubeproject.presentation.adapter.SearchCategoryAdapter
import com.example.youtubeproject.presentation.adapter.deco.SearchCategoryItemDecoration
import com.example.youtubeproject.presentation.ui.navigation.FragmentTag

class SearchFragment : Fragment() {
    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val categoryList = listOf(
            "영화/애니메이션", "자동차", "음악", "반려동물/동물", "스포츠", "여행/이벤트", "게임",
            "인물/블로그", "코미디", "엔터테인먼트", "뉴스/정치", "노하우/스타일", "교육", "과학기술"
        )
        val categoryRecyclerView = binding.searchCategoryRv
        val searchCategoryAdapter = SearchCategoryAdapter {
            binding.searchingEt.setText(it)
        }
        searchCategoryAdapter.submitList(categoryList)

        categoryRecyclerView.adapter = searchCategoryAdapter
        categoryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.addItemDecoration(SearchCategoryItemDecoration(16))

        binding.searchingEt.initEditText()
        binding.searchIconIv.setOnClickListener {
            if (binding.searchingEt.text.isNotEmpty()) {
                // todo :: viewModel search logic
            }
        }

        return binding.root
    }

    private fun EditText.initEditText() {
        requestFocus()

        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == IME_ACTION_SEARCH) {
                if (text.toString().isNotEmpty()) {
                    // todo :: viewModel search logic
                }
                val inputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                clearFocus()
            }
            true
        }
    }
}