package com.ssafy.daero.ui.root.mypage

import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentOtherPageBinding
import com.ssafy.daero.ui.adapter.mypage.MyPageViewPagerAdapter
import com.ssafy.daero.ui.adapter.mypage.OtherPageViewPagerAdapter

class OtherPageFragment : BaseFragment<FragmentOtherPageBinding>(R.layout.fragment_other_page) {
    private val tabTitleArray = arrayOf("기록", "여정")

    override fun init() {
        initViews()
        observeData()
        setOnClickListeners()
    }

    private fun initViews() {
        binding.viewPagerOtherPage.adapter = OtherPageViewPagerAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabOtherPage, binding.viewPagerOtherPage) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }

    private fun observeData() {
    }

    private fun setOnClickListeners() {
        binding.textOtherPageLabelFollower.setOnClickListener {
            findNavController().navigate(R.id.action_rootFragment_to_followerFragment)
        }
        binding.textOtherPageLabelFollowing.setOnClickListener {
            findNavController().navigate(R.id.action_rootFragment_to_followingFragment)
        }
    }

    fun disableSlide() {
        binding.viewPagerOtherPage.isUserInputEnabled = false
    }

    fun enableSlide() {
        binding.viewPagerOtherPage.isUserInputEnabled = true
    }
}