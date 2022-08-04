package com.ssafy.daero.ui.setting

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.resetPassword.ResetPasswordRequestDto
import com.ssafy.daero.databinding.FragmentResetPasswordBinding
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast

class ResetPasswordFragment : BaseFragment<FragmentResetPasswordBinding>(R.layout.fragment_reset_password_confirm) {

    private val resetPasswordViewModel : ResetPasswordViewModel by viewModels()

    override fun init() {
        setOnClickListener()
        addTextChangedListeners()
        observeData()
    }

    private fun setOnClickListener(){
        binding.apply {
            buttonResetPasswordNextStep.setOnClickListener {
                val dto = ResetPasswordRequestDto(editTextResetPasswordPasswordVerification.text.toString())
                resetPasswordViewModel.updatePassword(dto)
            }

            imgResetPasswordBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun addTextChangedListeners(){
        var flag = false
        binding.apply {
            // 비밀번호 확인
            editTextResetPasswordPasswordVerification.addTextChangedListener(object : TextWatcher {
                //입력이 끝났을 때 비밀번호 일치하는지 확인
                override fun afterTextChanged(p0: Editable?) {
                    if(editTextResetPasswordPasswordVerification.text.toString().equals(editTextResetPasswordPassword.text.toString())){
                        textResetPasswordMessage.text = "비밀번호가 일치합니다."
                        textResetPasswordMessage.setTextColor(R.color.primaryTextColor)

                        // 가입하기 버튼 활성화
                        if(flag)
                            buttonResetPasswordNextStep.isEnabled=true
                    }
                    else{
                        textResetPasswordMessage.text = "비밀번호가 일치하지 않습니다."
                        textResetPasswordMessage.setTextColor(Color.RED)
                        // 가입하기 버튼 비활성화
                        buttonResetPasswordNextStep.isEnabled=false
                    }
                }
                //입력하기 전
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }
                //텍스트 변화가 있을 시
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(editTextResetPasswordPassword.text.toString().equals(editTextResetPasswordPasswordVerification.text.toString())){
                        textResetPasswordMessage.text = "비밀번호가 일치합니다."
                        textResetPasswordMessage.setTextColor(R.color.primaryTextColor)
                        // 가입하기 버튼 활성화
                        buttonResetPasswordNextStep.isEnabled=true
                    }
                    else{
                        textResetPasswordMessage.text = "비밀번호가 일치하지 않습니다."
                        textResetPasswordMessage.setTextColor(Color.RED)
                        // 가입하기 버튼 비활성화
                        buttonResetPasswordNextStep.isEnabled=false
                    }
                }
            })

            editTextResetPasswordPassword.addTextChangedListener(object : TextWatcher {
                //입력이 끝났을 때 비밀번호 유효성 검사
                override fun afterTextChanged(p0: Editable?) {
                    if(editTextResetPasswordPassword.text.matches("^(?=.*[a-zA-Z0-9!@#$%^&*]).{8,20}$".toRegex())){
                        textResetPasswordMessage.text = "사용가능한 비밀번호입니다."
                        textResetPasswordMessage.setTextColor(R.color.primaryTextColor)

                        // 가입하기 버튼 활성화
                        flag = true
                    }
                    else{
                        textResetPasswordMessage.text = "비밀번호 형식을 지켜주세요. \n" +
                                "• 영어 대소문자, 숫자, 일부 특수문자(! @ # \$ % ^ & *) 포함 \n" +
                                "• 8 ~ 20자 이내                                         "
                        textResetPasswordMessage.setTextColor(Color.RED)
                        // 가입하기 버튼 비활성화
                        buttonResetPasswordNextStep.isEnabled=false
                    }
                }
                //입력하기 전
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }
                //텍스트 변화가 있을 시
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(editTextResetPasswordPassword.text.matches("^(?=.*[a-zA-Z0-9!@#$%^&*]).{8,20}$".toRegex())){
                        textResetPasswordMessage.text = "사용가능한 비밀번호입니다."
                        textResetPasswordMessage.setTextColor(R.color.primaryTextColor)
                        // 가입하기 버튼 활성화
                        flag = true
                    }
                    else{
                        textResetPasswordMessage.text = "비밀번호 형식을 지켜주세요. \n" +
                                "• 영어 대소문자, 숫자, 일부 특수문자(! @ # \$ % ^ & *) 포함 \n" +
                                "• 8 ~ 20자 이내                                         "
                        textResetPasswordMessage.setTextColor(Color.RED)
                        // 가입하기 버튼 비활성화
                        buttonResetPasswordNextStep.isEnabled=false
                    }
                }
            })
        }
    }

    private fun observeData(){
        resetPasswordViewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBarResetPasswordLoading.isVisible = it
        }

        resetPasswordViewModel.responseState.observe(viewLifecycleOwner) { state ->
            when(state){
                SUCCESS -> {
                    toast("비밀번호 변경이 완료되었습니다.")
                }
                FAIL -> {
                    toast("비밀번호 변경에 실패했습니다. 다시 시도해 주세요.")
                }
            }
        }
    }
}