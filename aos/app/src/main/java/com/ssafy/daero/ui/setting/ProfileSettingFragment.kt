package com.ssafy.daero.ui.setting

import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentProfileSettingBinding

class ProfileSettingFragment : BaseFragment<FragmentProfileSettingBinding>(R.layout.fragment_profile_setting) {
    override fun init() {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imgProfileSettingBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}