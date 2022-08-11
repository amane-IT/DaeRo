package com.ssafy.daero.ui.root.sns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.databinding.FragmentCommentMenuBottomSheetBinding
import com.ssafy.daero.ui.root.mypage.ReportListener
import com.ssafy.daero.utils.constant.COMMENT
import com.ssafy.daero.utils.constant.REPORT_BOTTOM_SHEET


class CommentMenuBottomSheetFragment(
    val replySeq: Int,
    val content: String,
    val userSeq: Int,
    private val reportListener: ReportListener,
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
        initView()
        setOnClickListeners()
    }

    private fun initView() {
        if (userSeq == App.prefs.userSeq) {
            binding.tvCommentMenuReport.visibility = View.GONE
            binding.viewReport.visibility = View.GONE
            binding.tvCommentMenuBlock.visibility = View.GONE
            binding.viewDelete.visibility = View.GONE
        } else {
            binding.tvCommentMenuModify.visibility = View.GONE
            binding.viewModify.visibility = View.GONE
            binding.tvCommentMenuDelete.visibility = View.GONE
            binding.viewDelete.visibility = View.GONE
        }
    }

    private fun setOnClickListeners() {
        binding.tvCommentMenuModify.setOnClickListener {
            //todo: 수정하기
            mCallback.commentUpdate(content, replySeq)
            dismiss()
        }
        binding.tvCommentMenuDelete.setOnClickListener {
            //삭제하기
            mCallback.commentDelete(replySeq)
            dismiss()
        }
        binding.tvCommentMenuReport.setOnClickListener {
            dismiss()
            ReportBottomSheetFragment(COMMENT, replySeq, reportListener).show(parentFragmentManager, REPORT_BOTTOM_SHEET)
        }
        binding.tvCommentMenuBlock.setOnClickListener {
            //todo: 차단하기
            mCallback.blockAdd(userSeq)
            dismiss()
        }
        binding.tvCommentMenuCancel.setOnClickListener {
            dismiss()
        }
    }
}