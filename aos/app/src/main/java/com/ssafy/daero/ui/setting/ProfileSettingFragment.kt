package com.ssafy.daero.ui.setting

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.bumptech.glide.request.RequestOptions
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.user.ProfileEditRequestDto
import com.ssafy.daero.data.dto.user.UserProfileResponseDto
import com.ssafy.daero.databinding.FragmentProfileSettingBinding
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast

class ProfileSettingFragment :
    BaseFragment<FragmentProfileSettingBinding>(R.layout.fragment_profile_setting) {
    private val profileSettingViewModel: ProfileSettingViewModel by viewModels()

    override fun init() {
        observeData()
        setOnClickListeners()
        getUserProfile()
    }

    private fun getUserProfile() {
        profileSettingViewModel.getUserProfile()
    }

    private fun observeData() {
        profileSettingViewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBarProfileSettingLoading.isVisible = it
        }
        profileSettingViewModel.editState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    toast("프로필 수정을 완료했습니다.")
                    requireActivity().onBackPressed()
                    profileSettingViewModel.editState.value = DEFAULT
                }
                FAIL -> {
                    toast("프로필 수정을 실패했습니다.")
                    profileSettingViewModel.editState.value = DEFAULT
                }
            }
        }
        profileSettingViewModel.getProfileState.observe(viewLifecycleOwner) {
            when (it) {
                FAIL -> {
                    toast("프로필정보를 가져오는데 실패했습니다.")
                    profileSettingViewModel.editState.value = DEFAULT
                }
            }
        }
        profileSettingViewModel.userProfile.observe(viewLifecycleOwner) {
            displayUserProfile(it)
        }
    }

    private fun displayUserProfile(userProfile: UserProfileResponseDto) {
        binding.editTextProfileSettingNickname.setText(userProfile.nickname)
        Glide.with(requireContext()).load(userProfile.profile_url)
            .apply(RequestOptions().centerCrop().circleCrop())
            .into(binding.imageProfileSettingProfileImage)
    }

    private fun setOnClickListeners() {
        binding.imgProfileSettingBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.buttonProfileSettingEdit.setOnClickListener {
            if (binding.editTextProfileSettingNickname.text.toString().isNullOrBlank()) {
                toast("닉네임이 올바르지 않습니다.")
                return@setOnClickListener
            }

            profileSettingViewModel.editProfile(
                ProfileEditRequestDto(binding.editTextProfileSettingNickname.text.toString())
            )
        }
    }
}