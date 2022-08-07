package com.ssafy.daero.ui.root.search

import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.application.App.Companion.userSeq
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.*
import com.ssafy.daero.ui.adapter.search.SearchArticleMoreAdapter
import com.ssafy.daero.ui.root.sns.CommentBottomSheetFragment
import com.ssafy.daero.ui.root.sns.LikeBottomSheetFragment
import com.ssafy.daero.utils.constant.ARTICLE_SEQ
import com.ssafy.daero.utils.constant.COMMENT_BOTTOM_SHEET
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.USER_SEQ
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
        searchArticleMoreAdapter = SearchArticleMoreAdapter(
            onArticleClickListener,
            onUserClickListener,
            onCommentClickListener,
            onLikeClickListener,
            onMenuClickListener
        )
        binding.recyclerSearchPlaceMore.adapter = searchArticleMoreAdapter
    }

    private fun observeData(){
        searchPlaceMoreViewModel.resultPlaceNameSearch.observe(viewLifecycleOwner){
            Log.d("TAG", "observeData: 여기")
            searchArticleMoreAdapter.submitData(lifecycle, it)
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