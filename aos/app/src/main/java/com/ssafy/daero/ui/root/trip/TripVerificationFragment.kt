package com.ssafy.daero.ui.root.trip


import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.application.App.Companion.userSeq
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentTripVerificationBinding
import com.ssafy.daero.ui.adapter.TripUntilNowAdapter
import com.ssafy.daero.ui.root.RootFragment
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.TRIP_BEFORE
import com.ssafy.daero.utils.constant.TRIP_STAMP_BOTTOM_SHEET
import com.ssafy.daero.utils.time.toDate
import com.ssafy.daero.utils.view.toast
import java.util.*

class TripVerificationFragment :
    BaseFragment<FragmentTripVerificationBinding>(R.layout.fragment_trip_verification) {

    private val tripVerificationViewModel: TripVerificationViewModel by viewModels()
    private lateinit var tripUntilNowAdapter: TripUntilNowAdapter
    private var placeSeq = 0

    private val tripUntilNowClickListener: (View, Int) -> Unit = { _, tripStampId ->
        // TODO: 트립스탬프 상세로 이동
    }

    override fun init() {
        initData()
        initView()
        initAdapter()
        observeData()
        setOnClickListeners()
        getTripInformation()
        getTripStamps()
    }

    private fun initData() {
        placeSeq = App.prefs.curPlaceSeq
    }

    private fun initView() {
        binding.tvTripVerificationAddress.text = App.prefs.verificationTime.toDate()
    }

    private fun initAdapter() {
        tripUntilNowAdapter = TripUntilNowAdapter().apply {
            onItemClickListener = tripUntilNowClickListener
        }
        binding.recyclerTripVerificationTripStampSoFar.adapter = tripUntilNowAdapter
    }

    private fun observeData() {
        tripVerificationViewModel.tripInformationState.observe(viewLifecycleOwner) {
            when (it) {
                FAIL -> {
                    toast("여행지 정보를 불러오는데 실패했습니다.")
                    tripVerificationViewModel.tripInformationState.value = DEFAULT
                }
            }
        }
        tripVerificationViewModel.tripInformation.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it.image_url)
                .skipMemoryCache(false)
                .placeholder(R.drawable.placeholder_trip_album)
                .apply(RequestOptions().centerCrop())
                .error(R.drawable.placeholder_trip_album)
                .into(binding.imgTripVerificationTripStamp)

            binding.tvTripVerificationTripName.text = it.place_name
        }
        tripVerificationViewModel.tripStamps.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.textTripVerificationNoContent.visibility = View.VISIBLE
                return@observe
            }
            tripUntilNowAdapter.tripStamps = it
            tripUntilNowAdapter.notifyDataSetChanged()
        }
    }

    private val selectCameraGallery : (Boolean, Boolean) -> Unit = { gallery, camera ->
        startCrop(gallery, camera)
    }

    private fun setOnClickListeners() {
        binding.buttonTripVerificationTripStamp.setOnClickListener {
            //todo : trip_place_seq,place_name 매개변수로 넘겨주기
            TripStampBottomSheetFragment(selectCameraGallery).show(
                childFragmentManager,
                TRIP_STAMP_BOTTOM_SHEET
            )
        }
        binding.buttonTripVerificationStop.setOnClickListener {
            // 이전 여행기록이 없다면
            if (tripVerificationViewModel.tripStamps.value?.isEmpty() != false) {
                // todo : 홈화면으로 이동, 캐시 디렉토리 삭제, room tripStamp 삭제, prefs 초기화
                tripVerificationViewModel.deleteTripStamps()
                App.prefs.initTrip()
                (requireParentFragment() as RootFragment).changeTripState(TRIP_BEFORE)
            } else {
                // todo 게시글 추가 화면으로 이동
            }
        }
    }

    private fun getTripStamps() {
        tripVerificationViewModel.getTripStamps()
    }

    private fun getTripInformation() {
        if (placeSeq > 0) {
            tripVerificationViewModel.getTripInformation(placeSeq)
        } else {
            toast("여행지 정보를 불러오는데 실패했습니다.")
        }
    }

    private val customCropImage = registerForActivityResult(CropImageContract()) { it ->
        var imagePath: String? = null

        it.uriContent?.let { _ ->
            imagePath = it.getUriFilePath(requireContext().applicationContext, true)
            Log.d("TAG", ": $imagePath")
        }

        imagePath?.let {
            findNavController().navigate(R.id.action_tripVerificationFragment_to_tripStampFragment,
                bundleOf(
                    "tripSeq" to 0,
                    "placeName" to "test",
                    "dateTime" to dateTime.time,
                    "imagePath" to imagePath
                )
            )
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