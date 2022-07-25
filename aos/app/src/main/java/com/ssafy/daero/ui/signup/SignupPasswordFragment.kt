package com.ssafy.daero.ui.signup

import android.graphics.Color
import android.graphics.Paint
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentSignupPasswordBinding

class SignupPasswordFragment : BaseFragment<FragmentSignupPasswordBinding>(R.layout.fragment_signup_password){
    override fun init() {
        initView()
        setOnClickListeners()
        addTextChangedListeners()
    }

    private fun initView(){
        binding.textSignupPasswordPassword.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    private fun setOnClickListeners(){
        binding.buttonSignupPasswordNextStep.setOnClickListener {
            findNavController().navigate(R.id.action_signupPasswordFragment_to_signupUsernameFragment)
        }
    }

    private fun addTextChangedListeners(){
        binding.apply {
            editTextSignupEmailPasswordVerification.addTextChangedListener(object :TextWatcher{
                //입력이 끝났을 때
                //4. 비밀번호 일치하는지 확인
                override fun afterTextChanged(p0: Editable?) {
                    if(editTextSignupPasswordPassword.getText().toString().equals(editTextSignupEmailPasswordVerification.getText().toString())){
                        textSignupPasswordMessage.setText("비밀번호가 일치합니다.")
                        textSignupPasswordMessage.setTextColor(R.color.primaryTextColor)

                        // 가입하기 버튼 활성화
                        buttonSignupPasswordNextStep.isEnabled=true
                    }
                    else{
                        textSignupPasswordMessage.setText("비밀번호가 일치하지 않습니다.")
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
                    if(editTextSignupPasswordPassword.getText().toString().equals(editTextSignupEmailPasswordVerification.getText().toString())){
                        textSignupPasswordMessage.setText("비밀번호가 일치합니다.")
                        textSignupPasswordMessage.setTextColor(R.color.primaryTextColor)
                        // 가입하기 버튼 활성화
                        buttonSignupPasswordNextStep.isEnabled=true
                    }
                    else{
                        textSignupPasswordMessage.setText("비밀번호가 일치하지 않습니다.")
                        textSignupPasswordMessage.setTextColor(Color.RED)
                        // 가입하기 버튼 비활성화
                        buttonSignupPasswordNextStep.isEnabled=false
                    }
                }
            })
        }
    }


}