package com.ssafy.daero.ui.signup

import android.graphics.Color
import android.graphics.Paint
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentSignupPasswordBinding
import com.ssafy.daero.utils.view.toast
import java.util.regex.Pattern

class SignupPasswordFragment : BaseFragment<FragmentSignupPasswordBinding>(R.layout.fragment_signup_password){
    override fun init() {
        setOnClickListeners()
        addTextChangedListeners()
    }

    private fun setOnClickListeners(){
        binding.apply {
            buttonSignupPasswordNextStep.setOnClickListener {
                val pwd = editTextSignupPasswordPassword.text.toString()
                val pwd_verification = editTextSignupEmailPasswordVerification.text.toString()

                if(pwd != "" && pwd_verification != "" && pwd == pwd_verification) {
                    App.password = binding.editTextSignupPasswordPassword.text.toString()
                    findNavController().navigate(R.id.action_signupPasswordFragment_to_signupUsernameFragment)
                } else if(pwd == "" || pwd_verification == "")
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
                    if(editTextSignupPasswordPassword.text.toString().equals(editTextSignupEmailPasswordVerification.text.toString())){
                        textSignupPasswordMessage.text = "비밀번호가 일치합니다."
                        textSignupPasswordMessage.setTextColor(R.color.primaryTextColor)

                        // 가입하기 버튼 활성화
                        if(flag)
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
                    if(editTextSignupPasswordPassword.text.matches("^(?=.*[a-zA-Z0-9!@#$%^&*]).{8,20}$".toRegex())){
                        textSignupPasswordMessage.text = "사용가능한 비밀번호입니다."
                        textSignupPasswordMessage.setTextColor(R.color.primaryTextColor)

                        // 가입하기 버튼 활성화
                        flag = true
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
                        // 가입하기 버튼 활성화
                        flag = true
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
            })
        }
    }


}