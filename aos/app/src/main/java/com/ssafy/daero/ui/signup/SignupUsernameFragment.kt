package com.ssafy.daero.ui.signup

import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.nickname.SignupNicknameRequestDto
import com.ssafy.daero.databinding.FragmentSignupEmailBinding
import com.ssafy.daero.databinding.FragmentSignupUsernameBinding
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class SignupUsernameFragment : BaseFragment<FragmentSignupUsernameBinding>(R.layout.fragment_signup_username){

    private val signupUsernameViewModel : SignupUsernameViewModel by viewModels()

    override fun init() {
        setOnClickListeners()
        observeData()
    }

    private fun setOnClickListeners(){
        binding.apply {
            buttonSignupUsernameNextStep.setOnClickListener{
                val dto = SignupNicknameRequestDto(editTextSignupUsernameUserName.text.toString())
                signupUsernameViewModel.verifyNickname(dto)
            }


        }
    }

    private fun observeData(){
        signupUsernameViewModel.responseState.observe(viewLifecycleOwner){ state ->
            when(state){
                SUCCESS -> {
                    binding.textSignupUsernameCheckMessage.text = "사용가능한 닉네임입니다."
                }
                FAIL -> {
                    binding.textSignupUsernameCheckMessage.text = "중복된 닉네임입니다."
                }
            }

        }
    }
}