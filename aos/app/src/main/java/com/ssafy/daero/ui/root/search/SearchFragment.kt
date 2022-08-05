package com.ssafy.daero.ui.root.search

import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentSearchBinding
import com.ssafy.daero.ui.adapter.search.SearchViewPagerAdapter
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.view.toast

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search){
    private val searchViewModel : SearchViewModel by viewModels()
    private val tabTitleArray = arrayOf("닉네임", "게시글")

    override fun init() {
        initViews()
        setOnClickListeners()
        setOnEditorActionListeners()
    }

    private fun initViews(){
        binding.viewPagerSearch.adapter = SearchViewPagerAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabSearch, binding.viewPagerSearch){ tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }

    private fun setOnClickListeners(){
        binding.imageViewSearchSearchIcon.setOnClickListener {
            val keyword = binding.editTextSearchSearchBar.text.toString()
            Log.d("TAG", "setOnClickListeners: $keyword")
            searchViewModel.searchUserName(keyword)
            searchViewModel.searchArticle(keyword)
            App.keyword = keyword
        }
    }

    private fun setOnEditorActionListeners(){
        binding.editTextSearchSearchBar.setOnEditorActionListener { textView, actionId, keyEvent ->
            when(actionId){
                EditorInfo.IME_ACTION_SEARCH ->  {
                    val keyword = binding.editTextSearchSearchBar.text.toString()
                    searchViewModel.searchUserName(keyword)
                    searchViewModel.searchArticle(keyword)
                    App.keyword = keyword
                    toast(keyword)
                    false
                }
                else -> {
                    false
                }
            }
        }
    }
}