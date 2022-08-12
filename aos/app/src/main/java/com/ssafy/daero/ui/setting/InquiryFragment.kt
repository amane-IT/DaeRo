package com.ssafy.daero.ui.setting

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentInquiryBinding
import com.ssafy.daero.ui.adapter.setting.InquiryAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast

class InquiryFragment : BaseFragment<FragmentInquiryBinding>(R.layout.fragment_inquiry) {
    private val inquiryViewModel : InquiryViewModel by viewModels()
    private lateinit var inquiryAdapter: InquiryAdapter

    override fun init() {
        getInquiry()
        setOnClickListeners()
        observeData()
    }

    private fun initAdapter() {
        inquiryAdapter = InquiryAdapter().apply {
            inquiry = inquiryViewModel.inquiryList
        }
        binding.recyclerInquiry.adapter = inquiryAdapter
    }

    private fun setOnClickListeners() {
        binding.imgInquiryBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.buttonInquiry.setOnClickListener {
            findNavController().navigate(R.id.action_inquiryFragment_to_inquiryRegisterFragment)
        }
    }

    private fun observeData() {
        inquiryViewModel.inquiryState.observe(viewLifecycleOwner) {
            when(it) {
                SUCCESS -> {
                    initAdapter()
                }
                FAIL -> {
                    toast("1:1문의 목록을 조회하는데 실패했습니다.")
                    inquiryViewModel.inquiryState.value = DEFAULT
                }
            }
        }
    }

    private fun getInquiry() {
        inquiryViewModel.getInquiry()
    }
}