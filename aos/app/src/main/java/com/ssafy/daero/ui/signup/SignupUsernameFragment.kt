package com.ssafy.daero.ui.signup

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.signup.SignupNicknameRequestDto
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
        var flag = false

        binding.apply {
            buttonSignupUsernameNextStep.setOnClickListener{
                if(buttonSignupUsernameNextStep.text.equals("닉네임 중복 검사")){
                    Log.d("TAG", "setOnClickListeners: " + editTextSignupUsernameUserName.text.toString())
                    val dto = SignupNicknameRequestDto(editTextSignupUsernameUserName.text.toString())
                    signupUsernameViewModel.verifyNickname(dto)
                    binding.buttonSignupUsernameNextStep.isEnabled = false
                }
                else{
                    if(flag){
                        findNavController().navigate(R.id.action_signupUsernameFragment_to_tripPreferenceFragment)
                    }
                }
            }

            textSignupUsernameEnterTerm.setOnClickListener {
                findNavController().navigate(R.id.action_signupUsernameFragment_to_termsFragment)
            }

            checkboxSignupUsernameTermCheck.setOnCheckedChangeListener { box, isChecked ->
                flag = isChecked
            }
        }
    }

    private fun observeData(){
        signupUsernameViewModel.showProgress.observe(viewLifecycleOwner){
            binding.progressBarSignupUsernameLoading.isVisible = it
        }

        signupUsernameViewModel.responseState.observe(viewLifecycleOwner){ state ->
            when(state){
                SUCCESS -> {
                    binding.textSignupUsernameCheckMessage.text = "사용가능한 닉네임입니다."
                    binding.buttonSignupUsernameNextStep.text = "가입하기"
                }
                FAIL -> {
                    binding.textSignupUsernameCheckMessage.text = "중복된 닉네임입니다."
                    binding.buttonSignupUsernameNextStep.isEnabled = true
                }
            }
        }
    }
}