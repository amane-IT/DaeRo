package com.ssafy.daero.ui.login

import android.graphics.Paint
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.login.LoginRequestDto
import com.ssafy.daero.databinding.FragmentEmailLoginBinding
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class EmailLoginFragment : BaseFragment<FragmentEmailLoginBinding>(R.layout.fragment_email_login) {

    private val emailLoginViewModel : EmailLoginViewModel by viewModels()

    override fun init() {
        initViews()
        observeData()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.buttonEmailLoginLogin.setOnClickListener {
            if(binding.editTextEmailLoginId.text==null||binding.editTextEmailLoginPw.text==null){
                //todo : 토스트
            }else{
                emailLoginViewModel.emailLogin(
                    LoginRequestDto(
                        binding.editTextEmailLoginId.text.toString(),
                        binding.editTextEmailLoginPw.text.toString()
                    )
                )
            }
        }
        binding.imgEmailLoginBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.textEmailLoginFindID.setOnClickListener {
            findNavController().navigate(R.id.action_emailLoginFragment_to_findIdFragment)
        }
        binding.textEmailLoginFindID.setOnClickListener {
            findNavController().navigate(R.id.action_emailLoginFragment_to_findPasswordFragment)
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
                SUCCESS -> {
                    findNavController().navigate(R.id.action_emailLoginFragment_to_rootFragment)
                }
                FAIL -> {
                    //todo : 로그인에 실패하였습니다 토스트
                }
            }
            emailLoginViewModel.responseState.value = DEFAULT
        }
    }
}