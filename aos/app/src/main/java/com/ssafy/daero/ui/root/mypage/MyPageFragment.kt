package com.ssafy.daero.ui.root.mypage

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.user.UserProfileResponseDto
import com.ssafy.daero.databinding.FragmentMyPageBinding
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.view.toast

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val myPageViewModel : MyPageViewModel by viewModels()
    private val tabTitleArray = arrayOf("기록", "여정")

    override fun init() {
        initViews()
        observeData()
        setOnClickListeners()
        getUserProfile()
    }

    private fun initViews() {
        binding.viewPagerMyPage.adapter = MyPageViewPagerAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabMyPage, binding.viewPagerMyPage) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }

    private fun observeData() {
        myPageViewModel.getProfileState.observe(viewLifecycleOwner) {
            when (it) {
                FAIL -> {
                    toast("프로필정보를 가져오는데 실패했습니다.")
                    myPageViewModel.getProfileState.value = DEFAULT
                }
            }
        }
        myPageViewModel.userProfile.observe(viewLifecycleOwner) {
            displayUserProfile(it)
        }
    }

    private fun setOnClickListeners() {
        binding.imageMyPageSetting.setOnClickListener {
            findNavController().navigate(R.id.action_rootFragment_to_settingFragment)
        }
        binding.imageMyPageProfile.setOnClickListener {
            findNavController().navigate(R.id.action_rootFragment_to_profileSettingFragment)
        }
        binding.textMyPageLabelFollower.setOnClickListener {
            findNavController().navigate(R.id.action_rootFragment_to_followerFragment)
        }
        binding.textMyPageLabelFollowing.setOnClickListener {
            findNavController().navigate(R.id.action_rootFragment_to_followingFragment)
        }
    }

    private fun getUserProfile() {
        myPageViewModel.getUserProfile()
    }

    private fun displayUserProfile(userProfile: UserProfileResponseDto) {
        binding.apply {
            textMyPageName.text = userProfile.nickname
            textMyPageFollower.text = userProfile.follower.toString()
            textMyPageFollowing.text = userProfile.following.toString()
        }

        Glide.with(requireContext())
            .load(userProfile.profile_url)
            .placeholder(R.drawable.img_user)
            .apply(RequestOptions().centerCrop().circleCrop())
            .error(R.drawable.img_user)
            .into(binding.imageMyPageProfile)
    }
}