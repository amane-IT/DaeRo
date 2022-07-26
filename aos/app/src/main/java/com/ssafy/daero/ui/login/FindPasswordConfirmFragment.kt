package com.ssafy.daero.ui.login

import android.app.ProgressDialog.show
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.login.FindIDResponseDto
import com.ssafy.daero.data.dto.login.FindPasswordRequestDto
import com.ssafy.daero.databinding.FragmentFindPasswordConfirmBinding
import com.ssafy.daero.ui.setting.LogoutDialogFragment
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast

class FindPasswordConfirmFragment : BaseFragment<FragmentFindPasswordConfirmBinding>(R.layout.fragment_find_password_confirm){

    private val findPasswordConfirmViewModel : FindPasswordConfirmViewModel by viewModels()

    override fun init() {
        setOnClickListeners()
        observeData()
    }
    private fun setOnClickListeners() {
        binding.buttonFindPwConfirmFind.setOnClickListener {
            findPasswordConfirmViewModel.findPassword(FindPasswordRequestDto(binding.editTextFindPwConfirmEmail.text.toString()))
        }
        binding.imgFindPwConfirmBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }


    private fun observeData() {
        findPasswordConfirmViewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBarFindPwConfirmLoading.isVisible = it
        }
        findPasswordConfirmViewModel.responseState.observe(viewLifecycleOwner) { state ->
            when(state) {
                SUCCESS -> {
                    var bundle = Bundle() // 번들을 통해 값 전달
                    bundle.putString("email",binding.editTextFindPwConfirmEmail.text.toString())
                    FindPasswordCheckEmailFragment(checkEmailListener).arguments = bundle
                    FindPasswordCheckEmailFragment(checkEmailListener).show(childFragmentManager, "CHECK_EMAIL_DIALOG")
                }
                FAIL -> {
                    toast("이메일을 다시 입력해주세요.")
                }
            }
        }
    }

    private val checkEmailListener: () -> Unit = {
        toast("이메일 인증이 완료되었습니다.")
    }
}