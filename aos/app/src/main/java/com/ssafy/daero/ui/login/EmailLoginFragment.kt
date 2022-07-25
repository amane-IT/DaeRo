package com.ssafy.daero.ui.login

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentEmailLoginBinding
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class EmailLoginFragment : BaseFragment<FragmentEmailLoginBinding>(R.layout.fragment_email_login) {

    private val emailLoginViewModel : EmailLoginViewModel by viewModels()

    override fun init() {
        initViews()
        observeData()
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
                }
                FAIL -> {
                    //todo : 로그인에 실패하였습니다 토스트
                }
            }
            emailLoginViewModel.responseState.value = DEFAULT
        }
    }
}