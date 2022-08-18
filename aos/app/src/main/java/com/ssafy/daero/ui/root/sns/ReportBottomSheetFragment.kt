package com.ssafy.daero.ui.root.sns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.daero.databinding.BottomsheetReportBinding
import com.ssafy.daero.ui.adapter.sns.ReportAdapter
import com.ssafy.daero.ui.root.mypage.ReportListener
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.view.toast

class ReportBottomSheetFragment(val type: Int, val seq: Int, val reportListener: ReportListener, var position: Int = 0) : BottomSheetDialogFragment() {
    private var _binding: BottomsheetReportBinding? = null
    private val binding get() = _binding!!

    private val reportViewModel: ReportViewModel by viewModels()
    private lateinit var reportAdapter: ReportAdapter
    private var commentSeq: Int = 0


    constructor(type: Int, seq: Int, reportListener: ReportListener, commentSeq:Int, position: Int) : this(type, seq, reportListener, position = position) {
        this.commentSeq = commentSeq
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expandFullHeight()
        initAdapter()
        observeData()
        setOnClickListeners()
    }

    private fun initAdapter() {
        reportAdapter = ReportAdapter(reportItemClickListener)
        binding.recyclerReport.adapter = reportAdapter
    }

    private val reportItemClickListener: (View, Int) -> Unit = { _, reportSeq ->
        when (type) {
            ARTICLE -> {
                reportViewModel.reportArticle(seq, reportSeq)
            }
            COMMENT -> {
                reportViewModel.reportComment(commentSeq, reportSeq)
            }
            USER -> {
                reportViewModel.reportUser(seq, reportSeq)
            }
        }
    }

    private fun observeData() {
        reportViewModel.reportState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    toast("신고가 완료되었습니다.")
                    reportListener.block(seq, position)
                    reportViewModel.reportState.value = DEFAULT
                    dismiss()
                }
                FAIL -> {
                    reportViewModel.reportState.value = DEFAULT
                    toast("신고를 실패했습니다.")
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageReportClose.setOnClickListener {
            dismiss()
        }
    }

    private fun expandFullHeight() {
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


