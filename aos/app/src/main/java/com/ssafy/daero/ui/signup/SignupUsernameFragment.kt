package com.ssafy.daero.ui.signup

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
    private var flag = false

    override fun init() {
        setOnClickListeners()
        addTextChangedListeners()
        observeData()
    }

    private fun setOnClickListeners(){

        binding.apply {
            buttonSignupUsernameNextStep.setOnClickListener{
                if(buttonSignupUsernameNextStep.text.equals("닉네임 중복 검사")){
                    val dto = SignupNicknameRequestDto(editTextSignupUsernameUserName.text.toString())
                    signupUsernameViewModel.verifyNickname(dto)
                    buttonSignupUsernameNextStep.isEnabled = false
                }
                else{
                    if(flag){
                        val dto = SignupRequestDto(App.userId, App.password, editTextSignupUsernameUserName.text.toString())
                        signupUsernameViewModel.signup(dto)
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

    private fun addTextChangedListeners(){
        binding.apply {
            // 비밀번호 확인
            editTextSignupUsernameUserName.addTextChangedListener(object : TextWatcher {
                //입력이 끝났을 때
                override fun afterTextChanged(p0: Editable?) { }

                //입력하기 전
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

                //텍스트 변화가 있을 시
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!App.userName.equals(editTextSignupUsernameUserName.text.toString())) {
                        buttonSignupUsernameNextStep.text = "닉네임 중복 검사"
                        flag = false
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
                    binding.buttonSignupUsernameNextStep.text = "가입하기"
                }
                FAIL -> {
                    binding.textSignupUsernameCheckMessage.text = "중복된 닉네임입니다."
                    binding.buttonSignupUsernameNextStep.isEnabled = true
                }
            }
        }

        signupUsernameViewModel.responseState_signup.observe(viewLifecycleOwner) { state ->
            when(state){
                SUCCESS -> {
                    App.prefs.setString("emailId", App.userId)
                    App.prefs.setString("password", App.password)
                    App.prefs.setString("userName", App.userName)
                    App.prefs.setUserSeq("userSeq", App.userSeq)
                    App.userId = ""
                    App.password = ""
                    App.userName = ""
                    App.userSeq = -1

                    findNavController().navigate(R.id.action_signupUsernameFragment_to_tripPreferenceFragment)
                }
                FAIL ->{
                    toast("회원가입에 실패했습니다. 다시 시도해 주세요.")
                }
            }
        }
    }
}