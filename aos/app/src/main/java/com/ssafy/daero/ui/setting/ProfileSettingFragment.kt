package com.ssafy.daero.ui.setting

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.login.ProfileEditRequest
import com.ssafy.daero.databinding.FragmentProfileSettingBinding
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast

class ProfileSettingFragment : BaseFragment<FragmentProfileSettingBinding>(R.layout.fragment_profile_setting) {
    private val profileSettingViewModel : ProfileSettingViewModel by viewModels()

    override fun init() {
        observeData()
        setOnClickListeners()
    }

    private fun observeData() {
        profileSettingViewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBarProfileSettingLoading.isVisible = it
        }
        profileSettingViewModel.responseState.observe(viewLifecycleOwner) {
            when(it) {
                SUCCESS -> {
                    toast("프로필 수정을 완료했습니다.")
                    requireActivity().onBackPressed()
                    profileSettingViewModel.responseState.value = DEFAULT
                }
                FAIL -> {
                    toast("프로필 수정을 실패했습니다.")
                    profileSettingViewModel.responseState.value = DEFAULT
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imgProfileSettingBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.buttonProfileSettingEdit.setOnClickListener {
            if(binding.editTextProfileSettingNickname.text.toString().isNullOrBlank()) {
                toast("닉네임이 올바르지 않습니다.")
                return@setOnClickListener
            }

            profileSettingViewModel.editProfile(
                ProfileEditRequest(binding.editTextProfileSettingNickname.text.toString())
            )
        }
    }
}