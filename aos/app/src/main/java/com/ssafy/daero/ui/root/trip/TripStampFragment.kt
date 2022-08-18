package com.ssafy.daero.ui.root.trip

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.databinding.FragmentTripStampBinding
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.time.toDate
import com.ssafy.daero.utils.view.toast

class TripStampFragment : BaseFragment<FragmentTripStampBinding>(R.layout.fragment_trip_stamp) {
    private val tripStampViewModel: TripStampViewModel by viewModels()
    private var isGood = 'T'

    private var placeSeq = 0
    private var imagePath: String? = null
    private var placeName: String? = null
    private var isUpdate: Boolean = false
    private var tripStampId: Int = 0

    override fun init() {
        initData()
        initView()
        setOnClickListeners()
        observeData()
        getTripStamp()
    }

    private fun initData() {
        placeSeq = App.prefs.curPlaceSeq
        imagePath = arguments?.getString(IMAGE_PATH)
        placeName = arguments?.getString(PLACE_NAME)
        isUpdate = arguments?.getBoolean(IS_TRIP_STAMP_UPDATE, false) ?: false
        tripStampId = arguments?.getInt(TRIP_STAMP_ID, 0) ?: 0
    }

    private fun initView() {
        if (!isUpdate) {
            binding.apply {
                textItemTripStampTitle.text = placeName ?: ""
                textItemTripStampDate.text = App.prefs.verificationTime.toDate()

                Glide.with(requireContext())
                    .load(imagePath)
                    .placeholder(R.drawable.placeholder_trip_album)
                    .apply(RequestOptions().centerCrop())
                    .error(R.drawable.placeholder_trip_album)
                    .into(binding.imageTripStampStamp)
            }
        }
    }

    private fun setOnClickListeners() {
        binding.apply {
            imageTripStampThumbup.setOnClickListener {
                isGood = 'Y'
                imageTripStampThumbup.setColorFilter(
                    ContextCompat.getColor(
                        requireActivity().applicationContext,
                        R.color.primaryColor
                    )
                )
                imageTripStampThumbDown.setColorFilter(
                    ContextCompat.getColor(
                        requireActivity().applicationContext,
                        R.color.lightGray
                    )
                )
            }

            imageTripStampThumbDown.setOnClickListener {
                isGood = 'N'
                imageTripStampThumbDown.setColorFilter(
                    ContextCompat.getColor(
                        requireActivity().applicationContext,
                        R.color.primaryColor
                    )
                )
                imageTripStampThumbup.setColorFilter(
                    ContextCompat.getColor(
                        requireActivity().applicationContext,
                        R.color.lightGray
                    )
                )
            }

            buttonTripStampSave.setOnClickListener {
                if (isGood == 'T') {
                    toast("만족도를 선택해 주세요")
                    return@setOnClickListener
                }

                if (isUpdate) {
                    // 트립스탬프 수정
                    tripStampViewModel.updateTripStamp(
                        tripStampId,
                        imagePath ?: tripStampViewModel.tripStamp.value?.imageUrl ?: "",
                        isGood
                    )

                } else {
                    //  트립스탬프 새로 등록
                    tripStampViewModel.insertTripStamp(
                        TripStamp(
                            placeName = placeName ?: "",
                            tripPlaceSeq = placeSeq,
                            dateTime = App.prefs.verificationTime,
                            imageUrl = imagePath ?: "",
                            satisfaction = isGood
                        )
                    )
                }
            }

            buttonTripStampRetry.setOnClickListener {
                TripStampBottomSheetFragment(setPhotos).show(
                    childFragmentManager,
                    "TripStampBottomSheetFragment"
                )
            }

            buttonTripStampShare.setOnClickListener {
                // TODO: 트립스탬프 카카오톡 공유하기
                // 참고: https://youngest-programming.tistory.com/530
            }

            imgTripStampBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun observeData() {
        tripStampViewModel.responseState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SUCCESS -> {
                    // 트립스탬프 완료 처리
                    if(!isUpdate) {
                        App.prefs.isTripStampComplete = true
                    }
                    requireActivity().onBackPressed()
                    tripStampViewModel.responseState.value = DEFAULT
                }
                FAIL -> {
                    tripStampViewModel.responseState.value = DEFAULT
                    toast("저장에 실패했습니다. 다시 시도해 주세요.")
                }
            }
        }
        tripStampViewModel.tripStampState.observe(viewLifecycleOwner) {
            when(it) {
                FAIL -> {
                    toast("트립스탬프 정보를 불러오는데 실패했습니다.")
                    tripStampViewModel.tripStampState.value = DEFAULT
                }
            }
        }
        tripStampViewModel.tripStamp.observe(viewLifecycleOwner) {
            binding.apply {
                textItemTripStampTitle.text = it.placeName
                textItemTripStampDate.text = it.dateTime.toDate()
                if(it.satisfaction == 'Y') {
                    isGood = 'Y'
                    imageTripStampThumbup.setColorFilter(
                        ContextCompat.getColor(
                            requireActivity().applicationContext,
                            R.color.primaryColor
                        )
                    )
                    imageTripStampThumbDown.setColorFilter(
                        ContextCompat.getColor(
                            requireActivity().applicationContext,
                            R.color.lightGray
                        )
                    )
                } else {
                    isGood = 'N'
                    imageTripStampThumbup.setColorFilter(
                        ContextCompat.getColor(
                            requireActivity().applicationContext,
                            R.color.lightGray
                        )
                    )
                    imageTripStampThumbDown.setColorFilter(
                        ContextCompat.getColor(
                            requireActivity().applicationContext,
                            R.color.primaryColor
                        )
                    )
                }

                Glide.with(requireContext())
                    .load(it.imageUrl)
                    .override(1200, 1200)
                    .placeholder(R.drawable.placeholder_trip_album)
                    .apply(RequestOptions().centerCrop())
                    .error(R.drawable.placeholder_trip_album)
                    .into(binding.imageTripStampStamp)
            }
        }
    }

    private fun getTripStamp() {
        if(isUpdate) {
            tripStampViewModel.getTripStamp(tripStampId)
        }
    }

    private val setPhotos: (Boolean, Boolean) -> Unit = { gallery, camera ->
        startCrop(gallery, camera)
    }

    private val customCropImage = registerForActivityResult(CropImageContract()) { it ->
        it.uriContent?.let { uri ->
            Glide.with(requireContext())
                .load(uri)
                .placeholder(R.drawable.img_user)
                .apply(RequestOptions().centerCrop())
                .error(R.drawable.img_user)
                .into(binding.imageTripStampStamp)
        }

        it.getUriFilePath(requireContext().applicationContext, true)?.let { _imagePath ->
            imagePath = _imagePath
        }
    }

    private fun startCrop(gallery: Boolean, camera: Boolean) {
        customCropImage.launch(
            options {
                setImageSource(
                    includeGallery = gallery,  // 갤러리만 허용
                    includeCamera = camera   // 카메라는 허용 X
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
                setOutputCompressQuality(50)
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