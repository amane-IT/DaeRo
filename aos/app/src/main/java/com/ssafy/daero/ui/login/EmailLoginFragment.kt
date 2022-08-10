package com.ssafy.daero.ui.login

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.application.MainActivity
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.login.LoginRequestDto
import com.ssafy.daero.databinding.FragmentEmailLoginBinding
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast
import io.github.muddz.styleabletoast.StyleableToast
import java.util.regex.Pattern

class EmailLoginFragment : BaseFragment<FragmentEmailLoginBinding>(R.layout.fragment_email_login) {

    private val emailLoginViewModel : EmailLoginViewModel by viewModels()

    override fun init() {
        initViews()
        observeData()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.buttonEmailLoginLogin.setOnClickListener {
            if(binding.editTextEmailLoginId.text.toString()==""||binding.editTextEmailLoginPw.text.toString()==""){

                toast("이메일 또는 비밀번호를 입력하세요.")
            }else{
                val pattern: Pattern = Patterns.EMAIL_ADDRESS
                if(pattern.matcher(binding.editTextEmailLoginId.text.toString()).matches()) {
                    emailLoginViewModel.emailLogin(
                        LoginRequestDto(
                            binding.editTextEmailLoginId.text.toString(),
                            binding.editTextEmailLoginPw.text.toString()
                        )
                    )
                }else{
                    toast("이메일 양식에 따라 입력해주세요.")
                }
            }
        }
        binding.imgEmailLoginBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.textEmailLoginFindID.setOnClickListener {
            findNavController().navigate(R.id.action_emailLoginFragment_to_findIdFragment)
        }
        binding.textEmailLoginFindPW.setOnClickListener {
            findNavController().navigate(R.id.action_emailLoginFragment_to_findPasswordConfirmFragment)
        }
    }

    private fun initViews() {
        binding.textEmailLoginFindID.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.textEmailLoginFindPW.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    private fun observeData() {
        emailLoginViewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBarEmailLoginLoading.isVisible = it
        }
        emailLoginViewModel.responseState.observe(viewLifecycleOwner) { state ->
            when(state) {
                200 -> {
                    findNavController().navigate(R.id.action_emailLoginFragment_to_rootFragment)
                    emailLoginViewModel.responseState.value = DEFAULT
                }
                202 -> {
                    findNavController().navigate(R.id.action_emailLoginFragment_to_tripPreferenceFragment)
                    emailLoginViewModel.responseState.value = DEFAULT
                }
                403 -> {
                    // todo: 정지된 유저 다이얼로그 띄우기
                    emailLoginViewModel.responseState.value = DEFAULT
                }
                FAIL -> {
                    toast("이메일 또는 비밀번호가 틀립니다.")
                    emailLoginViewModel.responseState.value = DEFAULT
                }
            }
        }
    }
}