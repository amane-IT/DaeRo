package com.ssafy.daero.ui.root.search

import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentSearchBinding
import com.ssafy.daero.ui.adapter.search.SearchViewPagerAdapter
import com.ssafy.daero.utils.view.toast

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val searchViewModel: SearchViewModel by viewModels()
    private val tabTitleArray = arrayOf("닉네임", "게시글")

    override fun init() {
        initViews()
        setOnClickListeners()
        setOnEditorActionListeners()
        observeData()
    }

    private fun initViews() {
        binding.viewPagerSearch.adapter = SearchViewPagerAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabSearch, binding.viewPagerSearch) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }

    private fun setOnClickListeners() {
        binding.imageViewSearchSearchIcon.setOnClickListener {
            val keyword = binding.editTextSearchSearchBar.text.toString()

            if (keyword.isBlank()) {
                toast("검색어를 한 글자 이상 입력해주세요")
                return@setOnClickListener
            }

            searchViewModel.searchUserName(keyword)
            searchViewModel.searchArticle(keyword)
            App.keyword = keyword
        }
        binding.imageSearchNotification.setOnClickListener {
            findNavController().navigate(R.id.action_rootFragment_to_notificationFragment)
        }
    }

    private fun setOnEditorActionListeners() {
        binding.editTextSearchSearchBar.setOnEditorActionListener { textView, actionId, keyEvent ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    val keyword = binding.editTextSearchSearchBar.text.toString()

                    if (keyword.isBlank()) {
                        toast("검색어를 한 글자 이상 입력해주세요")
                    } else {
                        searchViewModel.searchUserName(keyword)
                        searchViewModel.searchArticle(keyword)

                        App.keyword = keyword
                    }
                    false
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun observeData() {
        searchViewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBarSearchLoading.isVisible = it
        }
    }
}