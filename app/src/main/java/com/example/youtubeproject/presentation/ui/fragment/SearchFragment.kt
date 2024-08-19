package com.example.youtubeproject.presentation.ui.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeproject.R
import com.example.youtubeproject.databinding.FragmentSearchBinding
import com.example.youtubeproject.presentation.adapter.SearchCategoryAdapter
import com.example.youtubeproject.presentation.adapter.SearchResultAdapter
import com.example.youtubeproject.presentation.adapter.deco.SearchCategoryItemDecoration
import com.example.youtubeproject.presentation.uistate.SearchUiState
import com.example.youtubeproject.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private val searchViewModel by viewModels<SearchViewModel>()
    private val searchResultAdapter by lazy { SearchResultAdapter() }
    private var nextPage: String? = null
    private var selectedCategory: String? = null
    private var isMoreLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val categoryList = mapOf(
            null to "전체", "1" to "영화/애니메이션", "2" to "자동차", "10" to "음악", "15" to "반려동물/동물", "17" to "스포츠",
            "19" to "여행/이벤트", "20" to "게임", "22" to "인물/블로그", "23" to "코미디", "24" to "엔터테인먼트",
            "25" to "뉴스/정치", "26" to "노하우/스타일", "27" to "교육", "28" to "과학기술"
        )
        val categoryRecyclerView = binding.searchCategoryRv
        val searchResultRecyclerView = binding.searchResultRv
        val searchCategoryAdapter = SearchCategoryAdapter {
            val categoryNumber = categoryList.filterValues { value -> it == value }.keys.first()
            val query = binding.searchingEt.text.toString()

            if (query.isNotEmpty()) {
                selectedCategory = categoryNumber
                searchViewModel.searchVideo(query, null, categoryNumber)
            }
        }
        searchCategoryAdapter.submitList(categoryList.values.toList())

        categoryRecyclerView.adapter = searchCategoryAdapter
        categoryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.addItemDecoration(SearchCategoryItemDecoration(16))

        searchResultRecyclerView.adapter = searchResultAdapter
        searchResultRecyclerView.layoutManager = LinearLayoutManager(context)
        searchResultRecyclerViewScrollListener()

        binding.searchingEt.initEditText()
        binding.searchIconIv.setOnClickListener {
            val query = binding.searchingEt.text.toString()
            if (query.isNotEmpty()) {
                searchViewModel.searchVideo(query, null, null)

                val inputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.searchingEt.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            } else {
                Toast.makeText(context, "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeSearchState()
        observeSearchResult()

        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.isLoadMore.collectLatest {
                isMoreLoading = it
            }
        }
    }

    private fun EditText.initEditText() {
        requestFocus()

        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == IME_ACTION_SEARCH) {
                if (text.toString().isNotEmpty()) {
                    searchViewModel.searchVideo(text.toString(), null, null)
                }
                val inputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                clearFocus()
            }
            true
        }
    }

    private fun observeSearchState() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.uiState.collectLatest {
                when (it) {
                    is SearchUiState.Success -> {
                        searchCategoryRvNestedScrollHost.visibility = View.VISIBLE
                        searchingLoadingIndicator.visibility = View.GONE
                        searchResultStatusText.visibility = View.GONE
                        searchResultRv.visibility = View.VISIBLE
                        binding.searchMoreLoadingIndicator.visibility = View.GONE

                        nextPage = it.searchResultModel.nextPageToken.toString()
                    }
                    is SearchUiState.Empty -> {
                        searchCategoryRvNestedScrollHost.visibility = View.GONE
                        searchingLoadingIndicator.visibility = View.GONE
                        searchResultStatusText.visibility = View.VISIBLE
                        searchResultRv.visibility = View.GONE

                        searchResultStatusText.text = context?.getString(R.string.search_result_empty_text)
                    }
                    is SearchUiState.Init -> {
                        searchCategoryRvNestedScrollHost.visibility = View.GONE
                        searchingLoadingIndicator.visibility = View.GONE
                        searchResultStatusText.visibility = View.GONE
                        searchResultRv.visibility = View.GONE
                    }
                    is SearchUiState.Loading -> {
                        searchCategoryRvNestedScrollHost.visibility = View.GONE
                        searchingLoadingIndicator.visibility = View.VISIBLE
                        searchResultStatusText.visibility = View.GONE
                        searchResultRv.visibility = View.GONE
                    }
                    is SearchUiState.LoadingMore -> {
                        binding.searchMoreLoadingIndicator.visibility = View.VISIBLE
                        searchResultRv.smoothScrollToPosition(searchResultAdapter.itemCount - 1)
                    }
                    is SearchUiState.Failure -> {
                        searchCategoryRvNestedScrollHost.visibility = View.GONE
                        searchingLoadingIndicator.visibility = View.GONE
                        searchResultStatusText.visibility = View.VISIBLE
                        searchResultRv.visibility = View.GONE

                        searchResultStatusText.text = context?.getString(R.string.search_result_fail_text)
                    }
                }
            }
        }
    }

    private fun observeSearchResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.searchResult.collectLatest {
                val currentList = it.toMutableList()
                searchResultAdapter.submitList(currentList)
            }
        }
    }

    private fun searchResultRecyclerViewScrollListener() {
        binding.searchResultRv.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!isMoreLoading && nextPage != null) {
                        val query = binding.searchingEt.text.toString()
                        searchViewModel.searchResultMore(query, nextPage!!, selectedCategory)
                    }
                }
            }
        })
    }
}