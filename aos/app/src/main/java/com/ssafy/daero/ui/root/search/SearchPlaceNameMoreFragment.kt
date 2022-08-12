package com.ssafy.daero.ui.root.search

import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.*
import com.ssafy.daero.ui.adapter.search.SearchArticleMoreAdapter
import com.ssafy.daero.ui.root.mypage.ReportListener
import com.ssafy.daero.ui.root.sns.*
import com.ssafy.daero.ui.setting.BlockUserViewModel
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.view.toast

class SearchPlaceNameMoreFragment : BaseFragment<FragmentSearchPlaceNameMoreBinding>(R.layout.fragment_search_place_name_more),
    ArticleListener, ReportListener {
    private val searchPlaceMoreViewModel : SearchPlaceNameMoreViewModel by viewModels()
    private val articleViewModel: ArticleViewModel by viewModels()
    private val blockUserViewModel: BlockUserViewModel by viewModels()
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
        searchPlaceMoreViewModel.searchPlaceNameMore(App.keyword!!)
    }

    private fun initAdapter(){
        searchArticleMoreAdapter = SearchArticleMoreAdapter(
            onArticleClickListener,
            onUserClickListener,
            onCommentClickListener,
            onLikeClickListener,
            onLikeTextClickListener,
            onMenuClickListener
        )
        binding.recyclerSearchPlaceMore.adapter = searchArticleMoreAdapter
    }

    private fun observeData(){
        searchPlaceMoreViewModel.resultPlaceNameSearch.observe(viewLifecycleOwner){
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

    private val onLikeClickListener: (Int, Boolean) -> Unit = { articleSeq, likeYn ->
        when(likeYn){
            true -> articleViewModel.likeDelete(App.prefs.userSeq, articleSeq)
            false -> articleViewModel.likeAdd(App.prefs.userSeq, articleSeq)
        }
    }

    private val onLikeTextClickListener: (Int, Int) -> Unit = { articleSeq, likes ->
        LikeBottomSheetFragment(articleSeq, likes, onUserClickListener)
            .show(childFragmentManager, LIKE_BOTTOM_SHEET)
    }

    private val onMenuClickListener: (Int, Int, Int) -> Unit = { articleSeq, userSeq, position ->
        ArticleMenuBottomSheetFragment(articleSeq, userSeq, 1,
            this@SearchPlaceNameMoreFragment, this@SearchPlaceNameMoreFragment, position = position).show(
            childFragmentManager,
            ARTICLE_MENU_BOTTOM_SHEET
        )
    }

    override fun articleDelete(articleSeq: Int){
        articleViewModel.articleDelete(articleSeq)
        articleViewModel.responseState.observe(viewLifecycleOwner){
            when(it){
                SUCCESS -> {
                    toast("해당 게시글을 삭제했습니다.")
                    searchPlaceMoreViewModel.searchPlaceNameMore(App.keyword!!)
                    articleViewModel.deleteState.value = DEFAULT
                }
                FAIL -> {
                    toast("게시글을 삭제했습니다.")
                    articleViewModel.deleteState.value = DEFAULT
                }
            }
        }
    }

    override fun blockArticle(articleSeq: Int,position: Int) {
        blockUserViewModel.blockArticle(articleSeq)
        blockUserViewModel.blockState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    toast("해당 여행기록을 차단했습니다.")
                    searchArticleMoreAdapter.refresh()
                    binding.recyclerSearchPlaceMore.scrollToPosition(position)
                    blockUserViewModel.blockState.value = DEFAULT
                }
                FAIL -> {
                    toast("여행기록 차단을 실패했습니다.")
                    blockUserViewModel.blockState.value = DEFAULT
                }
            }
        }
    }

    override fun setPublic() {
        // TODO("Not yet implemented")
    }

    override fun articleOpen(articleSeq: Int) {
        // TODO("Not yet implemented")
    }

    override fun articleClose(articleSeq: Int) {
        articleViewModel.articleClose(articleSeq)
        articleViewModel.exposeState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    toast("해당 게시글을 비공개하였습니다.")
                    articleViewModel.exposeState.value = DEFAULT
                }
                FAIL -> {
                    toast("게시글 공개 처리를 실패했습니다.")
                    articleViewModel.exposeState.value = DEFAULT
                }
            }
        }
    }

    override fun block(seq: Int, position: Int) {
        blockUserViewModel.blockArticle(seq)
        blockUserViewModel.blockState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    searchArticleMoreAdapter.refresh()
                    binding.recyclerSearchPlaceMore.scrollToPosition(position)
                    blockUserViewModel.blockState.value = DEFAULT
                }
                FAIL -> {
                    toast("여행기록 차단을 실패했습니다.")
                    blockUserViewModel.blockState.value = DEFAULT
                }
            }
        }
    }
}