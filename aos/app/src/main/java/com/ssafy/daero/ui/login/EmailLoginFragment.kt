package com.ssafy.daero.ui.login

import android.graphics.Paint
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentEmailLoginBinding
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class EmailLoginFragment : BaseFragment<FragmentEmailLoginBinding>(R.layout.fragment_email_login) {

    private val emailLoginViewModel : EmailLoginViewModel by viewModels()

    override fun init() {
        binding.textEmailLoginId.paintFlags = Paint.UNDERLINE_TEXT_FLAG;
        initViews()
        binding.progressBarEmailLoginLoading.visibility = View.GONE
        binding.buttonEmailLoginLogin.setOnClickListener {
            binding.progressBarEmailLoginLoading.visibility = View.VISIBLE
            observeData()
        }
    }

    private fun initViews() {

    }

    private fun observeData() {
        emailLoginViewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBarEmailLoginLoading.isVisible = it
        }
        emailLoginViewModel.responseState.observe(viewLifecycleOwner) { state ->
            when(state) {
                SUCCESS -> {
                    //todo : 홈 화면으로 전환
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