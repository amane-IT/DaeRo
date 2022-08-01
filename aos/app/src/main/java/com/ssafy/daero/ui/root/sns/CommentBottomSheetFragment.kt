package com.ssafy.daero.ui.root.sns

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.daero.R
import com.ssafy.daero.data.dto.article.CommentAddRequestDto
import com.ssafy.daero.databinding.BottomsheetCommentBinding
import com.ssafy.daero.ui.adapter.sns.CommentAdapter
import com.ssafy.daero.ui.adapter.sns.ReCommentAdapter
import com.ssafy.daero.utils.view.setFullHeight
import com.ssafy.daero.utils.view.toast

class CommentBottomSheetFragment(private val articleSeq: Int, private val comments: Int) :
    BottomSheetDialogFragment(), CommentListener {
    private val articleViewModel: ArticleViewModel by viewModels({ requireParentFragment() })
    private lateinit var commentAdapter: CommentAdapter

    private var _binding: BottomsheetCommentBinding? = null
    private val binding get() = _binding!!

    private val commentItemClickListener: (View, Int, Int, String) -> Unit =
        { _, id, index, content ->
            //todo 1. 햄버거 2. 유저 사진
            when (index) {
                1 -> {
                    val commentMenuBottomSheetFragment = CommentMenuBottomSheetFragment(
                        id,
                        articleViewModel,
                        content,
                        this@CommentBottomSheetFragment
                    )
                    commentMenuBottomSheetFragment.show(
                        childFragmentManager,
                        commentMenuBottomSheetFragment.tag
                    )
                }
                2 -> {
                    findNavController().navigate(
                        R.id.action_articleFragment_to_otherPageFragment,
                        bundleOf("UserSeq" to id)
                    )
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return setFullHeight()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        getComment()
        observeData()
        setOnClickListeners()
    }

    private fun initView() {
        binding.textCommentCount.text = "$comments"
    }

    private fun getComment() {
        articleViewModel.commentSelect(articleSeq)
    }

    private val userProfileClickListener: (Int) -> Unit = { userSeq ->
        // todo: OtherPageFragment 로 이동, userSeq 번들로 전달
        findNavController().navigate(R.id.action_articleFragment_to_otherPageFragment)
    }


    private fun observeData() {
        articleViewModel.comment.observe(viewLifecycleOwner) {
            commentAdapter = CommentAdapter(3, this@CommentBottomSheetFragment).apply {
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
            articleViewModel.commentUpdate(
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
        articleViewModel.commentDelete(sequence)
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
            articleViewModel.reCommentAdd(
                3,
                sequence,
                CommentAddRequestDto(binding.editTextCommentAddComment.text.toString())
            )
            articleViewModel.commentUpdate(
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

    private fun setOnClickListeners() {
        binding.imageCommentClose.setOnClickListener { dismiss() }
        binding.textCommentWrite.setOnClickListener {
            //todo: article_seq
            articleViewModel.commentAdd(
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

    override fun reCommentSelect(
        articleSeq: Int,
        replySeq: Int,
        reCommentAdapter: ReCommentAdapter
    ): ReCommentAdapter {
        articleViewModel.reCommentSelect(articleSeq, replySeq)
        articleViewModel.reComment.observe(viewLifecycleOwner) {
            reCommentAdapter.submitData(lifecycle, it)
        }.run {
            return reCommentAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


