package com.ssafy.daero.ui.root.search

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.*
import com.ssafy.daero.ui.adapter.search.SearchArticleMoreAdapter
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.searchedArticleContentMore

class SearchPlaceNameMoreFragment : BaseFragment<FragmentSearchPlaceNameMoreBinding>(R.layout.fragment_search_place_name_more){
    private val searchPlaceMoreViewModel : SearchPlaceNameMoreViewModel by viewModels()
    private lateinit var searchArticleMoreAdapter: SearchArticleMoreAdapter

    override fun init() {
        initView()
        initData()
        initAdapter()
        observeData()
        setOnClickListeners()
    }

    private fun initView(){
        binding.toolbarTitle.text = "\"${App.keyword}\" 검색 결과"
    }

    private fun initData(){
        searchPlaceMoreViewModel.searchPlaceNameMore(App.keyword)
    }

    private fun initAdapter(){
        searchArticleMoreAdapter = SearchArticleMoreAdapter().apply {
            onItemClickListener = searchArticleItemClickListener
        }

        binding.recyclerSearchPlaceMore.adapter = searchArticleMoreAdapter
    }

    private fun observeData(){
        searchPlaceMoreViewModel.resultPlaceNameSearch.observe(viewLifecycleOwner){
            Log.d("TAG", "observeData: 여기")
//            TODO: 여행지 검색 API 완성되면 살리기
//            searchArticleMoreAdapter.submitData(lifecycle, it)
            searchArticleMoreAdapter.submitData(lifecycle, searchedArticleContentMore)
        }

        searchPlaceMoreViewModel.responseState.observe(viewLifecycleOwner){ state ->
            when(state){
                FAIL -> binding.textSearchPlaceMoreNoData.visibility = View.VISIBLE
            }
        }
    }

    private fun setOnClickListeners(){
        binding.imgSearchPlaceMoreBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private val searchArticleItemClickListener: (
        View, Int) -> Unit = { _, articleSeq ->
    }
}