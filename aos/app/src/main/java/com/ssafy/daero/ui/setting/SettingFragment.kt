package com.ssafy.daero.ui.setting

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentSettingBinding
import com.ssafy.daero.ui.root.RootFragment
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.file.deleteCache
import com.ssafy.daero.utils.permission.checkPermission
import com.ssafy.daero.utils.view.toast

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    private val settingViewModel: SettingViewModel by viewModels()

    override fun init() {
        observeData()
        setOnClickListeners()
        otherListeners()
    }

    private fun observeData() {
        settingViewModel.withdrawalState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    settingViewModel.withdrawalState.value = DEFAULT
                    deleteAllInformation()
                    RootFragment.curFragmentType = FragmentType.HomeFragment
                    findNavController().navigate(R.id.action_settingFragment_to_loginFragment)
                }
                FAIL -> {
                    toast("회원탈퇴를 실패하였습니다.")
                    settingViewModel.withdrawalState.value = DEFAULT
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imgSettingBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.textSettingLabelProfile.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_profileSettingFragment)
        }
        binding.textSettingLabelPassword.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_resetPasswordConfirmFragment)
        }
        binding.textSettingLabelBlock.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_blockUserFragment)
        }
        binding.textSettingLabelLocation.setOnClickListener { navigateToAppSetting() }
        binding.textSettingLabelNotice.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_noticeFragment)
        }
        binding.textSettingLabelFaq.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_FAQFragment)
        }
        binding.textSettingLabelInquiry.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_inquiryFragment)
        }
        binding.textSettingLogout.setOnClickListener {
            showLogoutDialog()
        }
        binding.textSettingWithdrawal.setOnClickListener {
            showWithdrawalDialog()
        }
    }

    private fun otherListeners() {
        binding.switchSettingNotification.setOnCheckedChangeListener { _, isChecked ->
            App.prefs.isNotificationAllow = isChecked
        }
    }

    override fun onStart() {
        super.onStart()

        displayPermission()
    }

    private fun displayPermission() {
        if (checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            binding.textSettingLocation.text = "앱 사용중에만 허용"
        } else {
            binding.textSettingLocation.text = "권한 없음"
        }
    }

    private fun navigateToAppSetting() {
        startActivity(
            Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts("package", requireActivity().packageName, null)
            }
        )
    }

    private fun showLogoutDialog() {
        LogoutDialogFragment(logoutListener).show(childFragmentManager, "LOGOUT_DIALOG")
    }

    private val logoutListener: () -> Unit = {
        deleteAllInformation()
        RootFragment.curFragmentType = FragmentType.HomeFragment
        findNavController().navigate(R.id.action_settingFragment_to_loginFragment)
    }

    private fun showWithdrawalDialog() {
        WithdrawalDialogFragment(withdrawalListener).show(childFragmentManager, "WITHDRAWAL_DIALOG")
    }

    private val withdrawalListener: () -> Unit = {
        settingViewModel.withdrawal()
    }

    private fun deleteAllInformation() {
        // 캐시 디렉토리 전체 삭제
        deleteCache(requireContext())

        // Room 에 저장되어있는 TripStamp, TripFollow 전체 삭제
        settingViewModel.deleteAllTripRecord()

        // Prefs 초기화
        App.prefs.initUser()
        App.prefs.initTrip()
        App.prefs.tripState = TRIP_BEFORE
    }
}