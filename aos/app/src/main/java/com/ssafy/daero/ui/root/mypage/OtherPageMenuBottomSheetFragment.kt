package com.ssafy.daero.ui.root.mypage

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.Nullable
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.application.App.Companion.userSeq
import com.ssafy.daero.data.dto.article.CommentAddRequestDto
import com.ssafy.daero.databinding.BottomsheetCommentBinding
import com.ssafy.daero.databinding.FragmentOtherPageMenuBottomSheetBinding
import com.ssafy.daero.ui.adapter.sns.CommentAdapter
import com.ssafy.daero.ui.adapter.sns.ReCommentAdapter
import com.ssafy.daero.ui.root.sns.*
import com.ssafy.daero.ui.root.trip.TripFollowBottomSheetFragment
import com.ssafy.daero.ui.setting.BlockUserViewModel
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.view.setFullHeight
import com.ssafy.daero.utils.view.toast

class OtherPageMenuBottomSheetFragment(
    var userSeq: Int,
    var listener: OtherPageListener
): BottomSheetDialogFragment() {

    private lateinit var binding: FragmentOtherPageMenuBottomSheetBinding


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.ArticleMenuBottomSheetFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_article_menu_bottom_sheet,
            container,
            false
        )
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
        binding.tvOtherPageMenuReport.setOnClickListener {
            //todo: 신고하기, album_seq
            dismiss()
            ReportBottomSheetFragment(USER, userSeq).show(
                parentFragmentManager,
                REPORT_BOTTOM_SHEET
            )
        }
        binding.tvOtherPageMenuReport.setOnClickListener {
            //차단하기
            listener.blockAdd(userSeq)
            dismiss()
        }
        binding.tvOtherPageMenuCancel.setOnClickListener {
            dismiss()
        }
    }
}