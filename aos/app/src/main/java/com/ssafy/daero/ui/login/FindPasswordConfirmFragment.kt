package com.ssafy.daero.ui.login

import android.graphics.Paint
import android.os.Bundle
import android.util.Patterns
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.login.FindPasswordRequestDto
import com.ssafy.daero.databinding.FragmentFindPasswordConfirmBinding
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast
import java.util.regex.Pattern

class FindPasswordConfirmFragment : BaseFragment<FragmentFindPasswordConfirmBinding>(R.layout.fragment_find_password_confirm){

    private val findPasswordConfirmViewModel : FindPasswordConfirmViewModel by viewModels()
    private var resetSeq: Int = 0

    override fun init() {
        initViews()
        setOnClickListeners()
        observeData()
    }

    private fun initViews() {
        binding.buttonFindPwConfirmFind.isEnabled = false
        binding.buttonFindPwConfirmFind.setBackgroundResource(R.drawable.button_disabled)
    }

    private fun setOnClickListeners() {
        binding.buttonFindPwConfirmCheckEmail.setOnClickListener {
            val pattern: Pattern = Patterns.EMAIL_ADDRESS
            if(pattern.matcher(binding.editTextFindPwConfirmEmail.text.toString()).matches()) {
                findPasswordConfirmViewModel.findPassword(FindPasswordRequestDto(binding.editTextFindPwConfirmEmail.text.toString()))
            }else{
                toast("이메일 양식에 따라 입력해주세요.")
            }
        }
        binding.buttonFindPwConfirmFind.setOnClickListener {
            var bundle = Bundle() // 번들을 통해 값 전달
            bundle.putString("reset_seq",resetSeq.toString())
            findNavController().navigate(R.id.action_findPasswordConfirmFragment_to_findPasswordFragment, bundle)
        }
        binding.imgFindPwConfirmBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }


    private fun observeData() {
        findPasswordConfirmViewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBarFindPwConfirmLoading.isVisible = it
        }
        findPasswordConfirmViewModel.confirmResponseState.observe(viewLifecycleOwner) { state ->
            when(state) {
                SUCCESS -> {
                    var bundle = Bundle() // 번들을 통해 값 전달
                    bundle.putString("email",binding.editTextFindPwConfirmEmail.text.toString())
                    FindPasswordCheckEmailDialogFragment(checkEmailListener).arguments = bundle
                    FindPasswordCheckEmailDialogFragment(checkEmailListener).show(childFragmentManager, "CHECK_EMAIL_DIALOG")
                }
                FAIL -> {
                    toast("이메일을 다시 입력해주세요.")
                }
            }
        }
    }

    private val checkEmailListener: (reset_seq: Int) -> Unit = {
        toast("이메일 인증이 완료되었습니다.")
        resetSeq = findPasswordConfirmViewModel.password_reset_seq
        binding.buttonFindPwConfirmFind.isEnabled = true
        binding.buttonFindPwConfirmFind.setBackgroundResource(R.drawable.button_regular)
    }
}