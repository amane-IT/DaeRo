package com.ssafy.daero.ui.setting

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.resetPassword.ResetPasswordConfirmRequestDto
import com.ssafy.daero.data.dto.resetPassword.ResetPasswordRequestDto
import com.ssafy.daero.databinding.FragmentResetPasswordConfirmBinding
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast

class ResetPasswordConfirmFragment :
    BaseFragment<FragmentResetPasswordConfirmBinding>(R.layout.fragment_reset_password_confirm)
{
    private val resetPasswordConfirmViewModel : ResetPasswordConfirmViewModel by viewModels()

    override fun init() {
        observeData()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.apply {
            buttonResetPasswordConfirmNextStep.setOnClickListener {
                val dto =
                    ResetPasswordConfirmRequestDto(editTextResetPasswordConfirmPasswordVerification.text.toString())
                resetPasswordConfirmViewModel.confirmPassword(dto)
            }

            imgResetPasswordConfirmBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun observeData(){
        resetPasswordConfirmViewModel.showProgress.observe(viewLifecycleOwner){
            binding.progressBarResetPasswordConfirmLoading.isVisible = it
        }

        resetPasswordConfirmViewModel.responseState.observe(viewLifecycleOwner){ state ->
            when(state){
                SUCCESS -> {
                    findNavController().navigate(R.id.action_resetPasswordConfirmFragment_to_resetPasswordFragment)
                }
                FAIL ->{
                    toast("비밀번호가 다릅니다.")
                }
            }
        }
    }
}