package com.ssafy.daero.ui.root.search

import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.application.App.Companion.keyword
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.*
import com.ssafy.daero.ui.adapter.search.SearchArticleMoreAdapter
import com.ssafy.daero.ui.adapter.search.SearchUserNameAdapter
import com.ssafy.daero.ui.root.sns.CommentBottomSheetFragment
import com.ssafy.daero.utils.constant.ARTICLE_SEQ
import com.ssafy.daero.utils.constant.COMMENT_BOTTOM_SHEET
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.USER_SEQ
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
        searchArticleMoreAdapter = SearchArticleMoreAdapter(
            onArticleClickListener,
            onUserClickListener,
            onCommentClickListener,
            onLikeClickListener,
            onMenuClickListener
        )

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

    private val onArticleClickListener: (Int) -> Unit = { articleSeq ->
        findNavController().navigate(
            R.id.action_searchContentMoreFragment_to_articleFragment,
            bundleOf(ARTICLE_SEQ to articleSeq)
        )

    }

    private val onUserClickListener: (Int) -> Unit = { userSeq ->
        findNavController().navigate(
            R.id.action_searchContentMoreFragment_to_otherPageFragment,
            bundleOf(USER_SEQ to userSeq)
        )
    }

    private val onCommentClickListener: (Int, Int) -> Unit = { articleSeq, comments ->
        CommentBottomSheetFragment(articleSeq, comments, onUserClickListener)
            .show(childFragmentManager, COMMENT_BOTTOM_SHEET)
    }
    private val onLikeClickListener: (Int, Int) -> Unit = { articleSeq, likes ->

    }
    private val onMenuClickListener: (Int) -> Unit = {

    }
}