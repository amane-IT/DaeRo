package com.ssafy.daero.ui.signup

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.signup.SignupEmailRequestDto
import com.ssafy.daero.databinding.FragmentSignupEmailBinding
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import kotlin.math.sign

class SignupEmailFragment : BaseFragment<FragmentSignupEmailBinding>(R.layout.fragment_signup_email){

    private val signupEmailViewModel : SignupEmailViewModel by viewModels()

    override fun init() {
        setOnClickListeners()
        observeData()
    }

    private fun setOnClickListeners(){
        binding.apply {
            buttonSignupEmailEmailLogin.setOnClickListener{
                val dto = SignupEmailRequestDto(editTextSignupEmailEmailId.text.toString())
                signupEmailViewModel.verifyEmail(dto)
            }
            
            buttonSignupEmailVerification.setOnClickListener { 
                signupEmailViewModel.verifyUserEmail()
            }

            imgSignupEmailBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun observeData(){
        signupEmailViewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBarSignupEmailLoading.isVisible = it
        }

        signupEmailViewModel.responseState_verifyEmail.observe(viewLifecycleOwner){ state ->
            when(state){
                SUCCESS -> {
                    binding.textSignupEmailCheckMessage.text = "이메일을 전송했습니다. 메일함을 확인해주세요."
                    binding.editTextSignupEmailEmailId.isEnabled = false
                    signupEmailViewModel.responseState_verifyEmail.value = DEFAULT
                }
                FAIL -> {
                    binding.textSignupEmailCheckMessage.text = "이메일 전송에 실패했습니다. 다시 시도해 주세요."
                    signupEmailViewModel.responseState_verifyEmail.value = DEFAULT
                }
            }
        }

        signupEmailViewModel.responseState_verifyUserEmail.observe(viewLifecycleOwner) { state ->
            when(state){
                SUCCESS -> {
                    App.userId = binding.editTextSignupEmailEmailId.text.toString()
                    signupEmailViewModel.responseState_verifyUserEmail.value = DEFAULT
                    findNavController().navigate(R.id.action_signupEmailFragment_to_signupPasswordFragment)
                }
                FAIL -> {
                    binding.textSignupEmailCheckMessage.text = "인증에 실패했습니다."
                    binding.editTextSignupEmailEmailId.isEnabled = true
                    signupEmailViewModel.responseState_verifyUserEmail.value = DEFAULT
                }
            }
        }
    }
}