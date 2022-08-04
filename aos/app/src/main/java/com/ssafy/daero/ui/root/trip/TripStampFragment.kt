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
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentTripStampBinding
import java.text.SimpleDateFormat

class TripStampFragment : BaseFragment<FragmentTripStampBinding>(R.layout.fragment_trip_stamp) {
    private val TAG = "TripStampFragment_DaeRo"
    private val tripStampViewModel: TripStampViewModel by viewModels()
    private var isGood = true

    private var imagePath: String? = null


    override fun init() {
        initView()
        setOnClickListeners()
    }

    private fun initView(){
        binding.apply {
            // TODO: 이미지뷰에 사진 & 장소명 & 날짜 뿌리기
            // arguments?.getInt("tripSeq")
            textItemTripStampTitle.text = arguments?.getString("placeName")

            val format = SimpleDateFormat("yyyy.MM.dd(EE) a hh:mm")
            Log.d(TAG, "initView: ${format.format(arguments?.getLong("dateTime"))}")
            textItemTripStampDate.text = format.format(arguments?.getLong("dateTime"))

            arguments?.getString("imagePath")?.let {
                Glide.with(requireContext())
                    .load(it)
                    .placeholder(R.drawable.img_user)
                    .apply(RequestOptions().centerCrop())
                    .error(R.drawable.img_user)
                    .into(binding.imageTripStampStamp)
            }
        }
    }

    private fun setOnClickListeners(){
        binding.apply {
            imageTripStampThumbup.setOnClickListener {
                isGood = true
                imageTripStampThumbup.setColorFilter(ContextCompat.getColor(requireActivity().applicationContext, R.color.primaryColor))
                imageTripStampThumbDown.setColorFilter(ContextCompat.getColor(requireActivity().applicationContext, R.color.lightGray))
            }

            imageTripStampThumbDown.setOnClickListener {
                isGood = false
                imageTripStampThumbDown.setColorFilter(ContextCompat.getColor(requireActivity().applicationContext, R.color.primaryColor))
                imageTripStampThumbup.setColorFilter(ContextCompat.getColor(requireActivity().applicationContext, R.color.lightGray))
            }

            buttonTripStampSave.setOnClickListener {
                // TODO: 트립스탬프 저장 기능 구현
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

    private val setPhotos: (Boolean) -> Unit = {isCamera ->
        startCrop(isCamera)
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

        imagePath = it.getUriFilePath(requireContext().applicationContext, true)
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