package com.ssafy.daero.ui.root.sns

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentHomeBinding
import com.ssafy.daero.ui.adapter.sns.HomeAdapter
import com.ssafy.daero.ui.root.mypage.ReportListener
import com.ssafy.daero.ui.setting.BlockUserViewModel
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.view.toast
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), ArticleListener, ReportListener {
    private val homeViewModel: HomeViewModel by viewModels()
    private val blockUserViewModel: BlockUserViewModel by viewModels()
    private val articleViewModel: ArticleViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter

    override fun init() {
        initAdapter()
        setOnClickListeners()
        observeData()
        getArticles()
        initSwipeToRefresh()
    }

    private fun setOnClickListeners() {
        binding.imageHomeNotification.setOnClickListener {
            findNavController().navigate(R.id.action_rootFragment_to_notificationFragment)
        }
    }

    private fun initAdapter() {
        homeAdapter = HomeAdapter(
            onLikeClickListener,
            onLikesClickListener,
            onCommentClickListener,
            onMoreClickListener,
            onArticleClickListener,
            onUserClickListener,
            requireContext(),
            requireActivity()
        )
        binding.recyclerHome.adapter = homeAdapter
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            homeAdapter.loadStateFlow.collect { loadStates ->
                binding.swipeRefresh.isRefreshing = loadStates.mediator?.refresh is LoadState.Loading
            }
        }
        homeViewModel.articleState.observe(viewLifecycleOwner) {
            when (it) {
                FAIL -> {
                    toast("게시글 목록을 불러오는데 실패했습니다.")
                    homeViewModel.articleState.value = DEFAULT
                }
            }
        }
        homeViewModel.articles.observe(viewLifecycleOwner) {
            homeAdapter.submitData(lifecycle, it)
        }
    }

    private fun getArticles() {
        homeViewModel.getArticles()
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            homeViewModel.getArticles()
            binding.recyclerHome.apply {
                layoutManager = null
                adapter = null
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = homeAdapter
            }
        }
    }

    // 좋아요 버튼 클릭
    private val onLikeClickListener: (Int, Boolean) -> Unit = { articleSeq, likeYn->
        when(likeYn){
            true -> articleViewModel.likeDelete(App.prefs.userSeq, articleSeq)
            false -> articleViewModel.likeAdd(App.prefs.userSeq, articleSeq)
        }
    }

    // 좋아요 갯수 클릭 (좋아요 리스트 보여주기)
    private val onLikesClickListener: (Int, Int) -> Unit = { articleSeq, likes ->
        LikeBottomSheetFragment(articleSeq, likes, userProfileClickListener)
            .show(childFragmentManager, LIKE_BOTTOM_SHEET)
    }

    // 댓글 버튼 클릭 (댓글 리스트 보여주기)
    private val onCommentClickListener: (Int, Int) -> Unit = { articleSeq, comments ->
        CommentBottomSheetFragment(articleSeq, comments, userProfileClickListener)
            .show(childFragmentManager, COMMENT_BOTTOM_SHEET)
    }

    private val userProfileClickListener: (Int) -> Unit = { userSeq ->
        findNavController().navigate(R.id.action_rootFragment_to_otherPageFragment, bundleOf(
            USER_SEQ to userSeq))
    }

    // 더보기 버튼 클릭
    private val onMoreClickListener: (Int, Int, Int) -> Unit = { articleSeq, userSeq, position->
        ArticleMenuBottomSheetFragment(articleSeq, userSeq,1,
            this@HomeFragment, this@HomeFragment, position = position).show(
            childFragmentManager,
            ARTICLE_MENU_BOTTOM_SHEET
        )
    }

    // 게시글 클릭 (게시글 상세로 이동)
    private val onArticleClickListener: (Int) -> Unit = { articleSeq ->
        findNavController().navigate(
            R.id.action_rootFragment_to_articleFragment,
            bundleOf(ARTICLE_SEQ to articleSeq)
        )
    }

    // 유저 클릭
    private val onUserClickListener: (Int) -> Unit = { userSeq ->
        findNavController().navigate(
            R.id.action_rootFragment_to_otherPageFragment, bundleOf(
                USER_SEQ to userSeq
            )
        )
    }

    override fun articleDelete(articleSeq: Int) {
        articleViewModel.articleDelete(articleSeq)
        articleViewModel.deleteState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    toast("해당 게시글을 삭제했습니다.")
                    homeViewModel.invalidatePageSource()
                    var idx = homeAdapter.itemCount
                    homeAdapter.refresh()
                    binding.recyclerHome.scrollToPosition(idx)
                    articleViewModel.deleteState.value = DEFAULT
                }
                FAIL -> {
                    toast("게시글 삭제를 실패했습니다.")
                    articleViewModel.deleteState.value = DEFAULT
                }
            }
        }
    }

    override fun blockArticle(articleSeq: Int, position: Int) {
        blockUserViewModel.blockArticle(articleSeq)
        blockUserViewModel.blockState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    toast("해당 여행기록을 차단했습니다.")
                    homeViewModel.invalidatePageSource()
                    homeAdapter.refresh()
                    binding.recyclerHome.scrollToPosition(position)
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
        TODO("Not yet implemented")
    }

    override fun articleOpen(articleSeq: Int) {
        TODO("Not yet implemented")
    }

    override fun articleClose(articleSeq: Int) {
        TODO("Not yet implemented")
    }

    override fun block(seq: Int, position: Int) {
        blockUserViewModel.blockArticle(seq)
        blockUserViewModel.blockState.observe(viewLifecycleOwner) { response ->
            when (response) {
                SUCCESS -> {
                    homeViewModel.invalidatePageSource()
                    homeAdapter.refresh()
                    binding.recyclerHome.scrollToPosition(position)
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