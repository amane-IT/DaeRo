package com.ssafy.daero.ui.login

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.login.FindPasswordModifyRequestDto
import com.ssafy.daero.databinding.FragmentFindPasswordBinding
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast

class FindPasswordFragment :
    BaseFragment<FragmentFindPasswordBinding>(R.layout.fragment_find_password) {

    private val findPasswordViewModel: FindPasswordViewModel by viewModels()
    private var passwordSeq: Int = 0

    override fun init() {
        setOnClickListeners()
        observeData()
    }

    private fun setOnClickListeners() {
        binding.buttonFindPwEditPassword.setOnClickListener {
            if (arguments != null) {
                passwordSeq = arguments!!.getString("reset_seq").toString().toInt()
                if (binding.editTextFindPwPw.text.toString() == binding.editTextFindPwPwCheck.text.toString()) {
                    findPasswordViewModel.findPasswordModify(
                        FindPasswordModifyRequestDto(binding.editTextFindPwPwCheck.text.toString()),
                        passwordSeq.toString()
                    )
                } else {
                    toast("비밀번호가 일치하지 않습니다.")
                }
            }
        }
        binding.imgFindPwBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }


    private fun observeData() {
        findPasswordViewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBarFindPwLoading.isVisible = it
        }
        findPasswordViewModel.responseState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SUCCESS -> {
                    findNavController().navigate(R.id.action_findPasswordFragment_to_loginFragment)
                }
                FAIL -> {
                    toast("오류로 인해 다시 진행해 주세요.")
                }
            }
        }
    }

}