package com.ssafy.daero.ui.login

import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.login.FindPasswordRequestDto
import com.ssafy.daero.databinding.FragmentFindIdBinding
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast
import java.util.regex.Pattern

class FindIdFragment : BaseFragment<FragmentFindIdBinding>(R.layout.fragment_find_id){

    private val findIdViewModel : FindIdViewModel by viewModels()

    override fun init() {
        setOnClickListeners()
        observeData()
    }
    private fun setOnClickListeners() {
        binding.buttonFindIDLogin.setOnClickListener {
            val pattern: Pattern = Patterns.EMAIL_ADDRESS
            if(pattern.matcher(binding.editTextFindIDId.text.toString()).matches()) {
                findIdViewModel.findID(binding.editTextFindIDId.text.toString())
            }else{
                toast("이메일 양식에 따라 입력해주세요.")
            }
        }
        binding.imgFindIDBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun observeData() {
        findIdViewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBarFindIDLoading.isVisible = it
        }
        findIdViewModel.responseState.observe(viewLifecycleOwner) { state ->
            when(state) {
                SUCCESS -> {
                    toast("가입된 이메일입니다.")
                }
                FAIL -> {
                    toast("가입한 적이 없는 이메일입니다.")
                }
            }
        }
    }
}