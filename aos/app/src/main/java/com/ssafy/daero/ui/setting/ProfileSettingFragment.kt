package com.ssafy.daero.ui.setting

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.user.UserProfileResponseDto
import com.ssafy.daero.databinding.FragmentProfileSettingBinding
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.PROFILE_REMOVE_BOTTOM_SHEET
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast

class ProfileSettingFragment :
    BaseFragment<FragmentProfileSettingBinding>(R.layout.fragment_profile_setting) {
    private val profileSettingViewModel: ProfileSettingViewModel by viewModels()
    private var imagePath: String? = null
    private var imageUrl: String = ""

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
                    profileSettingViewModel.getProfileState.value = DEFAULT
                }
            }
        }
        profileSettingViewModel.userProfile.observe(viewLifecycleOwner) {
            displayUserProfile(it)
        }
    }

    private fun displayUserProfile(userProfile: UserProfileResponseDto) {
        imageUrl = userProfile.profile_url
        binding.editTextProfileSettingNickname.setText(userProfile.nickname)
        Glide.with(requireContext())
            .load(userProfile.profile_url)
            .apply(RequestOptions().centerCrop().circleCrop())
            .error(R.drawable.img_user)
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
                imagePath = imagePath,
                imageUrl = imageUrl,
                nickname = binding.editTextProfileSettingNickname.text.toString()
            )
        }
        binding.imageProfileSettingProfileImage.setOnClickListener {
            startCrop()
        }
        binding.imageProfileSettingProfileImage.setOnLongClickListener {
            ProfileRemoveBottomSheetFragment(removeProfile).show(
                childFragmentManager,
                PROFILE_REMOVE_BOTTOM_SHEET
            )
            true
        }
    }

    private val removeProfile: () -> Unit = {
        imageUrl = ""
        Glide.with(requireContext()).load(R.drawable.img_user)
            .into(binding.imageProfileSettingProfileImage)
    }

    private val customCropImage = registerForActivityResult(CropImageContract()) { it ->
        it.uriContent?.let { uri ->
            Glide.with(requireContext())
                .load(uri)
                .placeholder(R.drawable.img_user)
                .apply(RequestOptions().centerCrop().circleCrop())
                .error(R.drawable.img_user)
                .into(binding.imageProfileSettingProfileImage)
        }
        imagePath = it.getUriFilePath(requireContext().applicationContext, true)
    }

    private fun startCrop() {
        customCropImage.launch(
            options {
                setImageSource(
                    includeGallery = true,  // 갤러리만 허용
                    includeCamera = false   // 카메라는 허용 X
                )
                // Normal Settings
                setScaleType(CropImageView.ScaleType.FIT_CENTER)
                setCropShape(CropImageView.CropShape.RECTANGLE)
                setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                setAspectRatio(1, 1)
                setMaxZoom(4)
                setAutoZoomEnabled(true)
                setMultiTouchEnabled(true)
                setCenterMoveEnabled(true)
                setShowCropOverlay(true)
                setAllowFlipping(true)
                setSnapRadius(3f)
                setTouchRadius(48f)
                setInitialCropWindowPaddingRatio(0.1f)
                setBorderLineThickness(3f)
                setBorderLineColor(Color.argb(170, 255, 255, 255))
                setBorderCornerThickness(2f)
                setBorderCornerOffset(5f)
                setBorderCornerLength(14f)
                setBorderCornerColor(Color.WHITE)
                setGuidelinesThickness(1f)
                setGuidelinesColor(R.color.black)
                setBackgroundColor(Color.argb(119, 0, 0, 0))
                setMinCropWindowSize(24, 24)
                setMinCropResultSize(20, 20)
                setMaxCropResultSize(99999, 99999)
                setActivityTitle("")
                setActivityMenuIconColor(requireActivity().getColor(R.color.black))
                setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                setOutputCompressQuality(70)
                setRequestedSize(0, 0)
                setRequestedSize(0, 0, CropImageView.RequestSizeOptions.RESIZE_INSIDE)
                setInitialCropWindowRectangle(null)
                setInitialRotation(0)
                setAllowCounterRotation(false)
                setFlipHorizontally(false)
                setFlipVertically(false)
                setCropMenuCropButtonTitle(null)
                setCropMenuCropButtonIcon(requireActivity().getColor(R.color.black))
                setAllowRotation(true)
                setNoOutputImage(false)
                setFixAspectRatio(true)
            }
        )
    }
}