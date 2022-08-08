package com.ssafy.daero.ui.setting

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.service.InquiryRequestDto
import com.ssafy.daero.databinding.FragmentInquiryRegisterBinding
import com.ssafy.daero.ui.adapter.setting.InquiryAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast

class InquiryRegisterFragment : BaseFragment<FragmentInquiryRegisterBinding>(R.layout.fragment_inquiry_register) {
    private val inquiryViewModel : InquiryViewModel by viewModels()
    private lateinit var inquiryAdapter: InquiryAdapter

    override fun init() {
        setOnClickListeners()
        observeData()
    }


    private fun setOnClickListeners() {
        binding.imgInquiryRegisterBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.buttonInquiryRegister.setOnClickListener {
            //유저시퀀스 해결하기
            if(binding.editTextInquiryRegisterTitle.text.toString()!=""&&binding.editTextInquiryRegisterContent.text.toString()!=""){
                inquiryViewModel.insertInquiry(
                    App.prefs.userSeq, InquiryRequestDto(
                    binding.editTextInquiryRegisterTitle.text.toString(),
                    binding.editTextInquiryRegisterContent.text.toString()
                ))
            }else{
                toast("제목 또는 내용을 입력해주세요.")
            }
        }
    }

    private fun observeData() {
        inquiryViewModel.inquiryState.observe(viewLifecycleOwner) {
            when(it) {
                SUCCESS -> {
                    toast("해당 문의를 등록했습니다.")
                    requireActivity().onBackPressed()
                }
                FAIL -> {
                    toast("1:1문의 등록에 실패했습니다.")
                    inquiryViewModel.inquiryState.value = DEFAULT
                }
            }
        }
    }
}