package com.ssafy.daero.ui.root.mypage

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.user.UserProfileResponseDto
import com.ssafy.daero.databinding.FragmentOtherPageBinding
import com.ssafy.daero.ui.adapter.mypage.OtherPageViewPagerAdapter
import com.ssafy.daero.ui.root.sns.ArticleMenuBottomSheetFragment
import com.ssafy.daero.ui.setting.BlockUserViewModel
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.view.toast

class OtherPageFragment : BaseFragment<FragmentOtherPageBinding>(R.layout.fragment_other_page), OtherPageListener, ReportListener {
    private val otherPageViewModel: OtherPageViewModel by viewModels()
    private val blockUserViewModel: BlockUserViewModel by viewModels()
    private val tabTitleArray = arrayOf("기록", "여정")
    var userSeq = 0
    private var followState = false

    override fun init() {
        initData()
        initViews()
        observeData()
        setOnClickListeners()
        getUserProfile()
    }

    private fun initData() {
        userSeq = arguments?.getInt(USER_SEQ, 0) ?: 0
    }

    private fun initViews() {
        binding.viewPagerOtherPage.adapter =
            OtherPageViewPagerAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabOtherPage, binding.viewPagerOtherPage) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()

        if(userSeq == App.prefs.userSeq) {
            binding.buttonOtherPageFollow.visibility = View.GONE
        }
    }

    private fun observeData() {
        otherPageViewModel.getProfileState.observe(viewLifecycleOwner) {
            when (it) {
                FAIL -> {
                    toast("프로필정보를 가져오는데 실패했습니다.")
                    otherPageViewModel.getProfileState.value = DEFAULT
                }
            }
        }
        otherPageViewModel.userProfile.observe(viewLifecycleOwner) {
            displayUserProfile(it)
        }
        otherPageViewModel.followState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    followState = true
                    binding.buttonOtherPageFollow.text = "언팔로우"
                    binding.buttonOtherPageFollow.setBackgroundResource(R.drawable.button_unfollow)
                    otherPageViewModel.followState.value = DEFAULT
                }
                FAIL -> {
                    toast("팔로우를 실패했습니다.")
                    otherPageViewModel.followState.value = DEFAULT
                }
            }
        }
        otherPageViewModel.unFollowState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    followState = false
                    binding.buttonOtherPageFollow.text = "팔로우"
                    binding.buttonOtherPageFollow.setBackgroundResource(R.drawable.button_follow)
                    otherPageViewModel.unFollowState.value = DEFAULT
                }
                FAIL -> {
                    toast("언팔로우를 실패했습니다.")
                    otherPageViewModel.unFollowState.value = DEFAULT
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.textOtherPageLabelFollower.setOnClickListener {
            findNavController().navigate(R.id.action_otherPageFragment_to_followerFragment)
        }
        binding.textOtherPageLabelFollowing.setOnClickListener {
            findNavController().navigate(R.id.action_otherPageFragment_to_followingFragment)
        }
        binding.buttonOtherPageFollow.setOnClickListener {
            if (followState) {
                otherPageViewModel.unFollow(userSeq)
            } else {
                otherPageViewModel.follow(userSeq)
            }
        }
        binding.imgOtherPageBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.imageOtherPageBadge.setOnClickListener {
            findNavController().navigate(R.id.action_otherPageFragment_to_stampFragment,
            bundleOf(USER_SEQ to userSeq))
        }
        binding.imgOtherPageMenu.setOnClickListener {
            OtherPageMenuBottomSheetFragment(
                userSeq,
                this@OtherPageFragment,
                this@OtherPageFragment
            ).show(
                childFragmentManager,
                OTHER_PAGE_MENU_BOTTOM_SHEET
            )
        }
    }

    private fun getUserProfile() {
        otherPageViewModel.getUserProfile(userSeq)
    }

    private fun displayUserProfile(userProfile: UserProfileResponseDto) {
        binding.apply {
            textOtherPageName.text = userProfile.nickname
            textOtherPageFollower.text = userProfile.follower.toString()
            textOtherPageFollowing.text = userProfile.following.toString()
            textOtherPageTitle.text = userProfile.nickname
            if (userProfile.followYn == 'y') {
                followState = true
                buttonOtherPageFollow.text = "언팔로우"
                buttonOtherPageFollow.setBackgroundResource(R.drawable.button_unfollow)
//                buttonOtherPageFollow.setBackgroundColor(
//                    requireContext().resources.getColor(
//                        R.color.gray,
//                        requireActivity().theme
//                    )
//                )
            } else {
                followState = false
                buttonOtherPageFollow.text = "팔로우"
                buttonOtherPageFollow.setBackgroundResource(R.drawable.button_follow)
//                buttonOtherPageFollow.setBackgroundColor(
//                    requireContext().resources.getColor(
//                        R.color.primaryColor,
//                        requireActivity().theme
//                    )
//                )
            }
        }

        Glide.with(requireContext())
            .load(userProfile.profile_url)
            .placeholder(R.drawable.img_user)
            .apply(RequestOptions().centerCrop().circleCrop())
            .error(R.drawable.img_user)
            .into(binding.imageOtherPageProfile)
    }

    fun disableSlide() {
        binding.viewPagerOtherPage.isUserInputEnabled = false
    }

    fun enableSlide() {
        binding.viewPagerOtherPage.isUserInputEnabled = true
    }

    override fun blockAdd(userSeq: Int) {
        blockUserViewModel.blockAdd(userSeq)
        blockUserViewModel.blockState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    toast("해당 유저를 차단했습니다.")
                    requireActivity().onBackPressed()
                    blockUserViewModel.blockState.value = DEFAULT
                }
                FAIL -> {
                    toast("유저 차단을 실패했습니다.")
                    blockUserViewModel.blockState.value = DEFAULT
                }
            }
        }
    }

    override fun block(seq: Int) {
        blockUserViewModel.blockAdd(seq)
        blockUserViewModel.blockState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    requireActivity().onBackPressed()
                    blockUserViewModel.blockState.value = DEFAULT
                }
                FAIL -> {
                    toast("유저 차단을 실패했습니다.")
                    blockUserViewModel.blockState.value = DEFAULT
                }
            }
        }
    }

}