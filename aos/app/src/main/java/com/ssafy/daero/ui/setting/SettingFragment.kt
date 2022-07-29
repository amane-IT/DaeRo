package com.ssafy.daero.ui.setting

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentSettingBinding
import com.ssafy.daero.ui.root.RootFragment
import com.ssafy.daero.utils.constant.FragmentType
import com.ssafy.daero.utils.permission.checkPermission
import com.ssafy.daero.utils.permission.checkPermissions

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    override fun init() {
        initViews()
        setOnClickListeners()
        otherListeners()
    }

    private fun initViews() {

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
        binding.textSettingLabelCamera.setOnClickListener { navigateToAppSetting() }
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
            if (isChecked) {
                // Todo : 알림 설정
            } else {
                // Todo : 알림 해제
            }
        }
        binding.switchSettingShake.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Todo : 흔들기 설정
            } else {
                // Todo : 흔들기 해제
            }
        }
    }

    override fun onStart() {
        super.onStart()

        displayPermission()
    }

    private fun displayPermission() {
        if (checkPermissions(
                listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        ) {
            binding.textSettingLocation.text = "앱 사용중에만 허용"
        } else {
            binding.textSettingLocation.text = "권한 없음"
        }

        if (checkPermission(Manifest.permission.CAMERA)) {
            binding.textSettingCamera.text = "앱 사용중에만 허용"
        } else {
            binding.textSettingCamera.text = "권한 없음"
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
        App.prefs.init()
        RootFragment.curFragmentType = FragmentType.HomeFragment
        findNavController().navigate(R.id.action_settingFragment_to_loginFragment)
    }

    private fun showWithdrawalDialog() {
        WithdrawalDialogFragment(withdrawalListener).show(childFragmentManager, "WITHDRAWAL_DIALOG")
    }

    private val withdrawalListener: () -> Unit = {
        // todo: 회원탈퇴 기능
    }
}