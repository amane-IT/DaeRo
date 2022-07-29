package com.ssafy.daero.ui.root.sns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.daero.R
import com.ssafy.daero.data.dto.article.CommentAddRequestDto
import com.ssafy.daero.databinding.FragmentCommentMenuBottomSheetBinding


class CommentMenuBottomSheetFragment(
    val replySeq: Int,
    val commentViewModel: CommentViewModel,
    val content: String,
    listener: CommentListener
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCommentMenuBottomSheetBinding
    var mCallback = listener

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.ArticleMenuBottomSheetFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment_menu_bottom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        init()
    }

    fun init() {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.tvCommentMenuModify.setOnClickListener {
            //todo: 수정하기
            mCallback.commentUpdate(content, replySeq)
        }
        binding.tvCommentMenuDelete.setOnClickListener {
            //삭제하기
            commentViewModel.commentDelete(replySeq)
        }
        binding.tvCommentMenuReport.setOnClickListener {
            //todo: 신고하기 bundle
            requireParentFragment().findNavController().navigate(
                R.id.action_commentFragment_to_reportFragment
            )
        }
        binding.tvCommentMenuBlock.setOnClickListener {
            //todo: 차단하기
        }
        binding.tvCommentMenuCancel.setOnClickListener {
            dismiss()
        }
    }
}