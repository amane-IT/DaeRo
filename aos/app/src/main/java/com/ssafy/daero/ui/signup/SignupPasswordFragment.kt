package com.ssafy.daero.ui.signup

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentSignupPasswordBinding
import com.ssafy.daero.utils.view.toast

class SignupPasswordFragment : BaseFragment<FragmentSignupPasswordBinding>(R.layout.fragment_signup_password){
    override fun init() {
        setOnClickListeners()
        addTextChangedListeners()
    }

    private fun setOnClickListeners(){
        binding.apply {
            buttonSignupPasswordNextStep.setOnClickListener {
                val pwd = editTextSignupPasswordPassword.text.toString()
                val pwdVerification = editTextSignupEmailPasswordVerification.text.toString()

                if(pwd != "" && pwdVerification != "" && pwd == pwdVerification) {
                    App.password = binding.editTextSignupPasswordPassword.text.toString()
                    findNavController().navigate(R.id.action_signupPasswordFragment_to_signupUsernameFragment)
                } else if(pwd == "" || pwdVerification == "")
                    toast("비밀번호를 입력해 주세요.")
                else
                    toast("비밀번호가 일치하지 않습니다.")
            }

            imgLoginBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }

    }

    private fun addTextChangedListeners(){
        var flag = false
        binding.apply {
            // 비밀번호 확인
            editTextSignupEmailPasswordVerification.addTextChangedListener(object :TextWatcher{
                //입력이 끝났을 때 비밀번호 일치하는지 확인
                override fun afterTextChanged(p0: Editable?) {
                    if(editTextSignupPasswordPassword.text.toString() == editTextSignupEmailPasswordVerification.text.toString()){
                        textSignupPasswordMessage.text = "비밀번호가 일치합니다."
                        textSignupPasswordMessage.setTextColor(R.color.primaryTextColor)
                        buttonSignupPasswordNextStep.isEnabled=true
                    }
                    else{
                        textSignupPasswordMessage.text = "비밀번호가 일치하지 않습니다."
                        textSignupPasswordMessage.setTextColor(Color.RED)
                        // 가입하기 버튼 비활성화
                        buttonSignupPasswordNextStep.isEnabled=false
                    }
                }
                //입력하기 전
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }
                //텍스트 변화가 있을 시
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(editTextSignupPasswordPassword.text.toString().equals(editTextSignupEmailPasswordVerification.text.toString())){
                        textSignupPasswordMessage.text = "비밀번호가 일치합니다."
                        textSignupPasswordMessage.setTextColor(R.color.primaryTextColor)
                        // 가입하기 버튼 활성화
                        buttonSignupPasswordNextStep.isEnabled=true
                    }
                    else{
                        textSignupPasswordMessage.text = "비밀번호가 일치하지 않습니다."
                        textSignupPasswordMessage.setTextColor(Color.RED)
                        // 가입하기 버튼 비활성화
                        buttonSignupPasswordNextStep.isEnabled=false
                    }
                }
            })

            editTextSignupPasswordPassword.addTextChangedListener(object :TextWatcher{
                //입력이 끝났을 때 비밀번호 유효성 검사
                override fun afterTextChanged(p0: Editable?) {
                    if(editTextSignupPasswordPassword.text.matches("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#\$%^&*])(?=.*[0-9!@#\$%^&*]).{8,20}\$".toRegex())){
                        textSignupPasswordMessage.text = "사용가능한 비밀번호입니다."
                        textSignupPasswordMessage.setTextColor(R.color.primaryTextColor)
                    }
                    else{
                        textSignupPasswordMessage.text = "비밀번호 형식을 지켜주세요. \n" +
                                "• 영어 대소문자, 숫자, 일부 특수문자(! @ # \$ % ^ & *) 포함 \n" +
                                "• 8 ~ 20자 이내                                         "
                        textSignupPasswordMessage.setTextColor(Color.RED)
                        // 가입하기 버튼 비활성화
                        buttonSignupPasswordNextStep.isEnabled=false
                    }
                }
                //입력하기 전
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }
                //텍스트 변화가 있을 시
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(editTextSignupPasswordPassword.text.matches("^(?=.*[a-zA-Z0-9!@#$%^&*]).{8,20}$".toRegex())){
                        textSignupPasswordMessage.text = "사용가능한 비밀번호입니다."
                        textSignupPasswordMessage.setTextColor(R.color.primaryTextColor)
                        buttonSignupPasswordNextStep.isEnabled=false
                    }
                    else{
                        textSignupPasswordMessage.text = "비밀번호 형식을 지켜주세요. \n" +
                                "• 영어 대소문자, 숫자, 일부 특수문자(! @ # \$ % ^ & *) 포함 \n" +
                                "• 8 ~ 20자 이내                                         "
                        textSignupPasswordMessage.setTextColor(Color.RED)
                        // 가입하기 버튼 비활성화
                        buttonSignupPasswordNextStep.isEnabled=false
                    }

                    if(editTextSignupPasswordPassword.text == editTextSignupEmailPasswordVerification.text){
                        textSignupPasswordMessage.text = "비밀번호가 일치합니다."
                        buttonSignupPasswordNextStep.isEnabled = true
                    }
                }
            })
        }
    }
}