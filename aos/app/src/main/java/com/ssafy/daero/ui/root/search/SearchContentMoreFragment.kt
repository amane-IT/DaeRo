package com.ssafy.daero.ui.root.search

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.application.App.Companion.keyword
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.*
import com.ssafy.daero.ui.adapter.search.SearchArticleMoreAdapter
import com.ssafy.daero.ui.adapter.search.SearchUserNameAdapter
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.pagingUser
import com.ssafy.daero.utils.searchedArticleContentMore

class SearchContentMoreFragment() : BaseFragment<FragmentSearchContentMoreBinding>(R.layout.fragment_search_content_more){
    private val TAG = "SearchContentMore_DaeRo"
    private val searchContentMoreViewModel : SearchContentMoreViewModel by viewModels()
    private lateinit var searchArticleMoreAdapter: SearchArticleMoreAdapter

    override fun init() {
        initView()
        initData()
        initAdapter()
        observeData()
        setOnClickListeners()
    }

    private fun initView(){
        binding.toolbarTitle.text = "\"$keyword\" 검색 결과"
    }

    private fun initData(){
        searchContentMoreViewModel.searchContentMore(keyword)
    }

    private fun initAdapter(){
        searchArticleMoreAdapter = SearchArticleMoreAdapter().apply {
            onItemClickListener = searchArticleItemClickListener
        }

        binding.recyclerSearchContentMore.adapter = searchArticleMoreAdapter
    }

    private fun observeData(){
        searchContentMoreViewModel.resultContentSearch.observe(viewLifecycleOwner){
            Log.d(TAG, "observeData: 여기")
//            TODO: 내용 검색 API 완성되면 살리기
            searchArticleMoreAdapter.submitData(lifecycle, it)
//            searchArticleMoreAdapter.submitData(lifecycle, searchedArticleContentMore)
        }

        searchContentMoreViewModel.responseState.observe(viewLifecycleOwner){ state ->
            when(state){
                FAIL -> binding.textSearchContentMoreNoData.visibility = View.VISIBLE
            }
        }
    }

    private fun setOnClickListeners(){
        binding.imgSearchContentMoreBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private val searchArticleItemClickListener: (
        View, Int) -> Unit = { _, articleSeq ->
    }
}