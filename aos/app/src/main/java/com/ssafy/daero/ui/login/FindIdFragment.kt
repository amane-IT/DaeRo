package com.ssafy.daero.ui.login

import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentFindIdBinding
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast

class FindIdFragment : BaseFragment<FragmentFindIdBinding>(R.layout.fragment_find_id){

    private val findIdViewModel : FindIdViewModel by viewModels()

    override fun init() {
        setOnClickListeners()
        observeData()
    }
    private fun setOnClickListeners() {
        binding.buttonFindIDLogin.setOnClickListener {
            findIdViewModel.findID(binding.editTextFindIDId.text.toString())
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