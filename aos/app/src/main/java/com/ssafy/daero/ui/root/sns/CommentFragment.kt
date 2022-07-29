package com.ssafy.daero.ui.root.sns

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.article.CommentAddRequestDto
import com.ssafy.daero.data.dto.article.ReCommentResponseDto
import com.ssafy.daero.databinding.*
import com.ssafy.daero.ui.adapter.MyPageAlbumAdapter
import com.ssafy.daero.ui.adapter.sns.CommentAdapter
import com.ssafy.daero.ui.adapter.sns.ReCommentAdapter
import com.ssafy.daero.utils.view.toast
import kotlinx.coroutines.flow.collectLatest


class CommentFragment : BaseFragment<FragmentCommentBinding>(R.layout.fragment_comment),
    CommentListener {

    private val commentViewModel: CommentViewModel by viewModels()
    private lateinit var commentAdapter: CommentAdapter

    private val commentItemClickListener: (View, Int, Int, String) -> Unit =
        { _, id, index, content ->
            //todo 1. 햄버거 2. 유저 사진
            when (index) {
                1 -> {
                    val commentMenuBottomSheetFragment = CommentMenuBottomSheetFragment(
                        id,
                        commentViewModel,
                        content,
                        this@CommentFragment
                    )
                    commentMenuBottomSheetFragment.show(
                        childFragmentManager,
                        commentMenuBottomSheetFragment.tag
                    )
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
        getComment()
        observeData()
        setOnClickListeners()
    }

    private fun initData() {
        //todo: article_seq
        commentAdapter = CommentAdapter(3, this@CommentFragment).apply {
            this.onItemClickListener = commentItemClickListener
        }
        binding.recyclerComment.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        binding.imgArticleBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun getComment() {
        //todo: article_seq
        commentViewModel.commentSelect(3)
    }

    private fun setOnClickListeners() {
        binding.textCommentWrite.setOnClickListener {
            //todo: article_seq
            commentViewModel.commentAdd(
                3,
                CommentAddRequestDto(binding.editTextCommentAddComment.text.toString())
            )
            binding.editTextCommentAddComment.setText("")
            binding.editTextCommentAddComment.postDelayed({
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(
                    binding.editTextCommentAddComment.windowToken,
                    0
                )
            }, 0)
            toast("댓글이 추가되었습니다.")
            getComment()
        }
    }

    private fun observeData() {
        commentViewModel.comment.observe(viewLifecycleOwner) {
            commentAdapter = CommentAdapter(3, this@CommentFragment).apply {
                this.onItemClickListener = commentItemClickListener
            }
            commentAdapter.submitData(lifecycle, it)
            binding.recyclerComment.apply {
                adapter = commentAdapter
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            }
        }
    }

    override fun commentUpdate(content: String, sequence: Int) {
        binding.editTextCommentAddComment.requestFocus()
        binding.editTextCommentAddComment.postDelayed({
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(
                binding.editTextCommentAddComment,
                InputMethodManager.SHOW_FORCED
            )
        }, 150)
        binding.editTextCommentAddComment.setText(content)
        binding.textCommentWrite.setOnClickListener {
            commentViewModel.commentUpdate(
                sequence,
                CommentAddRequestDto(binding.editTextCommentAddComment.text.toString())
            )
            binding.editTextCommentAddComment.postDelayed({
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(
                    binding.editTextCommentAddComment.windowToken,
                    0
                )
            }, 0)
            binding.editTextCommentAddComment.setText("")
            toast("댓글이 수정되었습니다.")
            getComment()
        }
    }

    override fun commentDelete(sequence: Int) {
        commentViewModel.commentDelete(sequence)
        getComment()
    }

    override fun reCommentAdd(sequence: Int) {
        binding.editTextCommentAddComment.requestFocus()
        binding.editTextCommentAddComment.postDelayed({
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(
                binding.editTextCommentAddComment,
                InputMethodManager.SHOW_FORCED
            )
        }, 150)
        binding.textCommentWrite.setOnClickListener {
            //todo: article_seq
            commentViewModel.reCommentAdd(
                3,
                sequence,
                CommentAddRequestDto(binding.editTextCommentAddComment.text.toString())
            )
            commentViewModel.commentUpdate(
                sequence,
                CommentAddRequestDto(binding.editTextCommentAddComment.text.toString())
            )
            binding.editTextCommentAddComment.postDelayed({
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(
                    binding.editTextCommentAddComment.windowToken,
                    0
                )
            }, 0)
            binding.editTextCommentAddComment.setText("")
            toast("답글이 추가되었습니다.")
            getComment()
        }
    }

    override fun reCommentSelect(
        articleSeq: Int,
        replySeq: Int,
        reCommentAdapter: ReCommentAdapter
    ): ReCommentAdapter {
        commentViewModel.reCommentSelect(articleSeq, replySeq)
        commentViewModel.reComment.observe(viewLifecycleOwner) {
            reCommentAdapter.submitData(lifecycle, it)
        }.run {
            return reCommentAdapter
        }
    }
}
