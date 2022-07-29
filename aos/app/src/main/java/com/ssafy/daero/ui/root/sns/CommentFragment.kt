package com.ssafy.daero.ui.root.sns

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.article.CommentAddRequestDto
import com.ssafy.daero.databinding.*
import com.ssafy.daero.ui.adapter.sns.CommentAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class CommentFragment : BaseFragment<FragmentCommentBinding>(R.layout.fragment_comment), CommentListener {

    private val commentViewModel : CommentViewModel by viewModels()
    private lateinit var commentAdapter: CommentAdapter

    private val onItemClickListener: (View, Int, Int, String) -> Unit = { _, id, index, content ->
        //todo 1. 햄버거 2. 유저 사진
        when(index){
            1 -> {
                val commentMenuBottomSheetFragment = CommentMenuBottomSheetFragment(id, commentViewModel, content, this@CommentFragment)
                commentMenuBottomSheetFragment.show(childFragmentManager,commentMenuBottomSheetFragment.tag)
            }
            2 -> {
                findNavController().navigate(
                    R.id.action_commentFragment_to_myPageFragment,
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
        commentViewModel.commentSelect(1,30)
    }

    private fun setOnClickListeners() {
        binding.textCommentWrite.setOnClickListener {
            //todo: article_seq
            commentViewModel.commentAdd(3, CommentAddRequestDto(binding.editTextCommentAddComment.text.toString()))
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
        //todo: article_seq
        commentAdapter = CommentAdapter(3,commentViewModel,this@CommentFragment).apply {
            this.onItemClickListener = this@CommentFragment.onItemClickListener
            this.commentData = commentViewModel.commentData
        }
        binding.recyclerComment.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    override fun commentUpdate(content: String, sequence: Int) {
        binding.editTextCommentAddComment.requestFocus()
        binding.editTextCommentAddComment.setText(content)
        binding.editTextCommentAddComment.setOnClickListener {
            commentViewModel.commentUpdate(sequence, CommentAddRequestDto(binding.editTextCommentAddComment.text.toString()))
        }
    }

    override fun reCommentAdd(sequence: Int) {
        binding.editTextCommentAddComment.requestFocus()
        binding.editTextCommentAddComment.setOnClickListener {
            //todo: article_seq
            commentViewModel.reCommentAdd(3,sequence, CommentAddRequestDto(binding.editTextCommentAddComment.text.toString()))
        }
    }
}