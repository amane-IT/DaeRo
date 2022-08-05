package com.ssafy.daero.ui.setting

import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentInquiryBinding
import com.ssafy.daero.ui.adapter.setting.InquiryAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.view.toast

class InquiryFragment : BaseFragment<FragmentInquiryBinding>(R.layout.fragment_inquiry) {
    private val inquiryViewModel : InquiryViewModel by viewModels()
    private lateinit var inquiryAdapter: InquiryAdapter

    override fun init() {
        initAdapter()
        setOnClickListeners()
        observeData()
        getInquiry()
    }

    private fun initAdapter() {
        inquiryAdapter = InquiryAdapter()
        binding.recyclerInquiry.adapter = inquiryAdapter
    }

    private fun setOnClickListeners() {
        binding.imgNoticeBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun observeData() {
        inquiryViewModel.inquiryState.observe(viewLifecycleOwner) {
            when(it) {
                FAIL -> {
                    toast("1:1문의 목록을 조회하는데 실패했습니다.")
                    inquiryViewModel.inquiryState.value = DEFAULT
                }
            }
        }
        inquiryViewModel.inquiry.observe(viewLifecycleOwner) {
            inquiryAdapter.apply {
                inquiry = it
                notifyDataSetChanged()
            }
        }
    }

    private fun getInquiry() {
        inquiryViewModel.getInquiry()
    }
}