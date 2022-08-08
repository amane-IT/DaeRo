package com.ssafy.daero.ui.root.sns

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentHomeBinding
import com.ssafy.daero.ui.adapter.sns.HomeAdapter
import com.ssafy.daero.ui.setting.BlockUserViewModel
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.view.toast

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), ArticleListener {
    private val homeViewModel: HomeViewModel by viewModels()
    private val blockUserViewModel: BlockUserViewModel by viewModels()
    private val articleViewModel: ArticleViewModel by viewModels()
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
            onUserClickListener,
            requireContext(),
            requireActivity()
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
    private val onMoreClickListener: (Int, Int) -> Unit = { articleSeq, userSeq->
        ArticleMenuBottomSheetFragment(articleSeq, userSeq,this@HomeFragment).show(
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
                    homeAdapter.refresh()
                    articleViewModel.deleteState.value = DEFAULT
                }
                FAIL -> {
                    toast("게시글 삭제를 실패했습니다.")
                    articleViewModel.deleteState.value = DEFAULT
                }
            }
        }
    }

    override fun blockAdd(userSeq: Int) {
        blockUserViewModel.blockAdd(userSeq)
        blockUserViewModel.responseState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    toast("해당 유저를 차단했습니다.")
                    blockUserViewModel.responseState.value = DEFAULT
                }
                FAIL -> {
                    toast("유저 차단을 실패했습니다.")
                    blockUserViewModel.responseState.value = DEFAULT
                }
            }
        }
    }

    override fun setPublic() {
        TODO("Not yet implemented")
    }
}