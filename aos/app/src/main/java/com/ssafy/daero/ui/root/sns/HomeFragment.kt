package com.ssafy.daero.ui.root.sns

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentHomeBinding
import com.ssafy.daero.ui.adapter.sns.HomeAdapter
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.view.toast

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter

    override fun init() {
        initAdapter()
        setOnClickListeners()
        observeData()
        getArticles()
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
            onUserClickListener
        )
        binding.recyclerHome.adapter = homeAdapter
    }

    private fun observeData() {
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

    // 좋아요 버튼 클릭
    private val onLikeClickListener: (Int) -> Unit = {

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
    private val onMoreClickListener: (Int) -> Unit = {

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
}