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
import com.ssafy.daero.data.dto.trip.TripPopularResponseDto
import com.ssafy.daero.databinding.FragmentTripVerificationBinding
import com.ssafy.daero.ui.adapter.TripUntilNowAdapter
import com.ssafy.daero.ui.root.sns.ArticleMenuBottomSheetFragment
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.tag.TagCollection
import com.ssafy.daero.utils.view.toast
import java.text.SimpleDateFormat
import java.util.*

class TripVerificationFragment : BaseFragment<FragmentTripVerificationBinding>(R.layout.fragment_trip_verification) {

    private val travelingViewModel: TravelingViewModel by viewModels()
    private lateinit var tripUntilNowAdapter: TripUntilNowAdapter
    private lateinit var dateTime: Date

    private val tripUntilNowClickListener: (View, Int) -> Unit = { _, articleSeq ->
        // TODO: 지금까지 여행지 상세 페이지로 이동
    }

    override fun init() {
        initData()
        setOnClickListeners()
    }

    private fun initData() {
        travelingViewModel.getTripStamps()
        binding.textTripVerificationUsername.text = "${App.prefs.nickname}님"

        //todo : Traveling에서 넘어올 때 번들로 trip_place_seq,place_name 넘겨받기

        val dateFormat = SimpleDateFormat("yyyy.MM.dd(E)")
        dateTime = Date(System.currentTimeMillis())
        val str:String = dateFormat.format(dateTime)
        binding.tvTripVerificationAddress.text = str

        val tripUntilNowList = mutableListOf<TripPopularResponseDto>()
        for(i in travelingViewModel.articleTripStampData){
            tripUntilNowList.add(TripPopularResponseDto(i.tripPlaceSeq,i.imageUrl,i.placeName))
        }
        tripUntilNowAdapter = TripUntilNowAdapter().apply {
            onItemClickListener = tripUntilNowClickListener
            tripPlaces = tripUntilNowList
        }
        binding.recyclerTripVerificationTripStampSoFar.apply {
            adapter = tripUntilNowAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun setOnClickListeners(){
        binding.buttonTripVerificationTripStamp.setOnClickListener {
            //todo : trip_place_seq,place_name 매개변수로 넘겨주기
//            TripStampBottomSheetFragment(0,"",dateTime.time).show(childFragmentManager, TRIP_STAMP_BOTTOM_SHEET)
            TripStampBottomSheetFragment(setPhotos).show(
                childFragmentManager,
                TRIP_STAMP_BOTTOM_SHEET
            )
        }
        binding.buttonTripVerificationStop.setOnClickListener {
            //todo : 만약 이전에 했던 여행이 있으면(없어도 트립스탬프 만들었으면) 게시글 추가 페이지로, 없으면(+트립스탬프 안만들었으면) 홈화면으로
        }
    }

    private val setPhotos: (Boolean) -> Unit = {isCamera ->
        startCrop(isCamera)
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
                    "placeName" to "",
                    "dateTime" to dateTime.time,
                    "imagePath" to imagePath
                )
            )
        }
    }

    private fun startCrop(isCamera: Boolean){
        customCropImage.launch(
            options {
                setImageSource(
                    includeGallery = !isCamera,  // 갤러리만 허용
                    includeCamera = isCamera   // 카메라는 허용 X
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