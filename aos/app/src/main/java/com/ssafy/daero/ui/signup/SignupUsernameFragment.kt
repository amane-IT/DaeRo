package com.ssafy.daero.ui.signup

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.signup.SignupNicknameRequestDto
import com.ssafy.daero.data.dto.signup.SignupRequestDto
import com.ssafy.daero.databinding.FragmentSignupUsernameBinding
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast

class SignupUsernameFragment : BaseFragment<FragmentSignupUsernameBinding>(R.layout.fragment_signup_username){

    private val signupUsernameViewModel : SignupUsernameViewModel by viewModels()

    override fun init() {
        setOnClickListeners()
        addTextChangedListeners()
        observeData()
    }

    private fun setOnClickListeners(){

        binding.apply {
            buttonSignupUsernameConfirm.setOnClickListener {
                if(editTextSignupUsernameUserName.text.isNotEmpty()) {
                    val dto =
                        SignupNicknameRequestDto(editTextSignupUsernameUserName.text.toString())
                    signupUsernameViewModel.verifyNickname(dto)
                } else {
                    toast("닉네임을 입력해주세요.")
                }
            }

            buttonSignupUsernameNextStep.setOnClickListener {
                val dto = SignupRequestDto(
                    App.userId,
                    App.password,
                    editTextSignupUsernameUserName.text.toString()
                )
                signupUsernameViewModel.signup(dto)
            }

            imgLoginBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

            textSignupUsernameEnterTerm.setOnClickListener {
                findNavController().navigate(R.id.action_signupUsernameFragment_to_termsFragment)
            }

            checkboxSignupUsernameTermCheck.setOnCheckedChangeListener { box, isChecked ->
                signupUsernameViewModel.checkOption(isChecked)
            }
        }
    }

    private fun addTextChangedListeners(){
        binding.apply {
            // 닉네임 확인
            editTextSignupUsernameUserName.addTextChangedListener(object : TextWatcher {
                //입력이 끝났을 때
                override fun afterTextChanged(p0: Editable?) { }

                //입력하기 전
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

                //텍스트 변화가 있을 시
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (App.prefs.nickname != editTextSignupUsernameUserName.text.toString()) {
                        buttonSignupUsernameConfirm.visibility = View.VISIBLE
                        buttonSignupUsernameNextStep.visibility = View.GONE
                    }
                }
            })
        }
    }

    private fun observeData(){
        signupUsernameViewModel.showProgress.observe(viewLifecycleOwner){
            binding.progressBarSignupUsernameLoading.isVisible = it
        }

        signupUsernameViewModel.responseState_nickname.observe(viewLifecycleOwner){ state ->
            when(state){
                SUCCESS -> {
                    binding.textSignupUsernameCheckMessage.text = "사용가능한 닉네임입니다."
                    binding.buttonSignupUsernameConfirm.visibility = View.GONE
                    binding.buttonSignupUsernameNextStep.visibility = View.VISIBLE
                    App.prefs.nickname = binding.editTextSignupUsernameUserName.text.toString()
                }
                FAIL -> {
                    binding.textSignupUsernameCheckMessage.text = "중복된 닉네임입니다."
                }
            }
        }

        signupUsernameViewModel.isChecked.observe(viewLifecycleOwner) { state ->
            when(state){
                true -> {
                    binding.buttonSignupUsernameNextStep.isEnabled = true
                }
                false -> {
                    binding.buttonSignupUsernameNextStep.isEnabled = false
                }
            }
        }

        signupUsernameViewModel.responseState_signup.observe(viewLifecycleOwner) { state ->
            when(state){
                SUCCESS -> {
                    App.userId = ""
                    App.password = ""
                    findNavController().navigate(R.id.action_signupUsernameFragment_to_tripPreferenceFragment)
                }
                FAIL ->{
                    toast("회원가입에 실패했습니다. 다시 시도해 주세요.")
                }
            }
        }
    }
}