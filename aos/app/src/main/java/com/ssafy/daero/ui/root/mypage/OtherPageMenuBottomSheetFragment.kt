package com.ssafy.daero.ui.root.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.daero.R
import com.ssafy.daero.databinding.FragmentOtherPageMenuBottomSheetBinding
import com.ssafy.daero.ui.root.sns.*
import com.ssafy.daero.utils.constant.*

class OtherPageMenuBottomSheetFragment(
    var userSeq: Int,
    var listener: OtherPageListener,
    var reportListener: ReportListener
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
            R.layout.fragment_other_page_menu_bottom_sheet,
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
            ReportBottomSheetFragment(USER, userSeq, reportListener).show(
                parentFragmentManager,
                OTHER_PAGE_MENU_BOTTOM_SHEET
            )
            dismiss()
        }
        binding.tvOtherPageMenuBlock.setOnClickListener {
            //차단하기
            listener.blockAdd(userSeq)
            dismiss()
        }
        binding.tvOtherPageMenuCancel.setOnClickListener {
            dismiss()
        }
    }
}