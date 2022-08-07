package com.ssafy.daero.ui.root.search

import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentSearchArticleBinding
import com.ssafy.daero.ui.adapter.search.SearchArticleAdapter
import com.ssafy.daero.ui.adapter.search.SearchUserNameAdapter
import com.ssafy.daero.utils.constant.ARTICLE_SEQ
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.pagingUser
import com.ssafy.daero.utils.searchedArticleContent
import com.ssafy.daero.utils.searchedArticlePlace
import com.ssafy.daero.utils.view.toast

class SearchArticleFragment : BaseFragment<FragmentSearchArticleBinding>(R.layout.fragment_search_article){
    private val TAG = "SearchArticleFragment_DaeRo"
    private val searchViewModel : SearchViewModel by viewModels({ requireParentFragment() })
    private lateinit var searchArticleContentAdapter: SearchArticleAdapter
    private lateinit var searchArticlePlaceAdapter: SearchArticleAdapter

    override fun init() {
        initAdapter()
        observeData()
        setOnClickListeners()
    }

    private fun initAdapter(){
        // 내용 검색
        searchArticleContentAdapter = SearchArticleAdapter().apply {
            onItemClickListener = searchArticleItemClickListener
        }
        binding.recyclerSearchArticleContent.adapter = searchArticleContentAdapter

        // 여행지 검색
        searchArticlePlaceAdapter = SearchArticleAdapter().apply {
            onItemClickListener = searchArticleItemClickListener
        }
        binding.recyclerSearchArticlePlace.adapter = searchArticlePlaceAdapter
    }

    private fun observeData(){
        searchViewModel.resultArticleSearch.observe(viewLifecycleOwner){
            Log.d(TAG, "observeData: 여기")
            searchArticleContentAdapter.resultList = it.content
            searchArticlePlaceAdapter.resultList = it.place
            searchArticleContentAdapter.notifyDataSetChanged()
            searchArticlePlaceAdapter.notifyDataSetChanged()
        }

        searchViewModel.responseState_userName.observe(viewLifecycleOwner){ state ->
            when(state){
                FAIL -> binding.textSearchUserNoData.visibility = View.VISIBLE
            }
        }
    }

    private fun setOnClickListeners(){
        binding.apply {
            textSearchArticleContentMoreData.setOnClickListener {
                if(App.keyword != "")
                    findNavController().navigate(R.id.action_rootFragment_to_searchContentMoreFragment)
                else
                    toast("검색 결과가 없습니다.")
            }

            textSearchArticlePlaceMoreData.setOnClickListener {
                if(App.keyword != "")
                    findNavController().navigate(R.id.action_rootFragment_to_searchPlaceNameMoreFragment)
                else
                    toast("검색 결과가 없습니다.")
            }
        }
    }

    private val searchArticleItemClickListener: (
        View, Int) -> Unit = { _, article_seq ->
        findNavController().navigate(
            R.id.action_rootFragment_to_articleFragment,
            bundleOf(ARTICLE_SEQ to article_seq)
        )
    }
}