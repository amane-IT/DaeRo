package com.ssafy.daero.ui.root.sns

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.*
import com.ssafy.daero.ui.adapter.sns.CommentAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class CommentFragment : BaseFragment<FragmentCommentBinding>(R.layout.fragment_comment) {

    private val commentViewModel : CommentViewModel by viewModels()
    private lateinit var commentAdapter: CommentAdapter

    private val onItemClickListener: (View, Int, Int) -> Unit = { _, id, index ->
        //todo 1. 햄버거 2. 유저 사진
        when(index){
            1 -> {
                val commentMenuBottomSheetFragment = CommentMenuBottomSheetFragment()
                commentMenuBottomSheetFragment.show(childFragmentManager,commentMenuBottomSheetFragment.tag)
            }
            2 -> {
                requireParentFragment().findNavController().navigate(
                    R.id.action_articleFragment_to_myPageFragment,
                    bundleOf("UserSeq" to id)
                )
            }
        }
    }

    override fun init() {
        initData()
        setOnClickListeners()
        observeData()
    }

    private fun initData() {
        commentViewModel.commentSelect(1,10)
    }

    private fun setOnClickListeners() {
        commentAdapter = CommentAdapter(1, commentViewModel).apply {
            this.onItemClickListener = this@CommentFragment.onItemClickListener
            this.commentData = commentViewModel.commentData
        }
        binding.recyclerComment.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun observeData() {
        commentViewModel.responseState.observe(viewLifecycleOwner) { state ->
            when(state) {
                SUCCESS -> {
                    setBinding()
                    commentViewModel.responseState.value = DEFAULT
                }
                FAIL -> {

                    commentViewModel.responseState.value = DEFAULT
                }
            }
        }
    }

    private fun setBinding() {
        commentAdapter = CommentAdapter(1,commentViewModel).apply {
            this.onItemClickListener = this@CommentFragment.onItemClickListener
            this.commentData = commentViewModel.commentData
        }
        binding.recyclerComment.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }
}