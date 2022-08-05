package com.ssafy.daero.ui.root.trip

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast
import java.text.SimpleDateFormat

class TripStampFragment : BaseFragment<FragmentTripStampBinding>(R.layout.fragment_trip_stamp) {
    private val TAG = "TripStampFragment_DaeRo"
    private val tripStampViewModel: TripStampViewModel by viewModels()
    private var isGood = 'T'

    private var imagePath: String? = null
    private var placeName: String = ""
    private var dateTime: Long = 0

    override fun init() {
        initView()
        setOnClickListeners()
        observeData()
    }

    private fun initView(){
        binding.apply {
            //
            // arguments?.getInt("tripSeq")
            arguments?.getString("placeName")?.let {
                placeName = it
                textItemTripStampTitle.text = it
            }


            arguments?.getLong("dateTime")?.let{
                dateTime = it
                val format = SimpleDateFormat("yyyy.MM.dd(E) a hh:mm")
                Log.d(TAG, "initView: ${format.format(it)}")
                textItemTripStampDate.text = format.format(it)
            }


            arguments?.getString("imagePath")?.let {
                Glide.with(requireContext())
                    .load(it)
                    .placeholder(R.drawable.img_user)
                    .apply(RequestOptions().centerCrop())
                    .error(R.drawable.img_user)
                    .into(binding.imageTripStampStamp)
                imagePath = it
            }
        }
    }

    private fun setOnClickListeners(){
        binding.apply {
            imageTripStampThumbup.setOnClickListener {
                isGood = 'Y'
                imageTripStampThumbup.setColorFilter(ContextCompat.getColor(requireActivity().applicationContext, R.color.primaryColor))
                imageTripStampThumbDown.setColorFilter(ContextCompat.getColor(requireActivity().applicationContext, R.color.lightGray))
            }

            imageTripStampThumbDown.setOnClickListener {
                isGood = 'N'
                imageTripStampThumbDown.setColorFilter(ContextCompat.getColor(requireActivity().applicationContext, R.color.primaryColor))
                imageTripStampThumbup.setColorFilter(ContextCompat.getColor(requireActivity().applicationContext, R.color.lightGray))
            }

            buttonTripStampSave.setOnClickListener {
                if(isGood != 'T'){
                    val dto = TripStamp(
                        App.prefs.placeSeq,
                        placeName,
                        dateTime,
                        imagePath!!,
                        isGood
                    )
                    tripStampViewModel.insertTripStamp(dto)
                } else {
                    toast("만족도를 선택해 주세요")
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

    private fun observeData(){
        tripStampViewModel.responseState.observe(viewLifecycleOwner){ state ->
            when(state){
                SUCCESS -> {
                    findNavController().navigate(R.id.action_tripStampFragment_to_tripNextFragment,
                    bundleOf("tripSeq" to arguments?.getInt("tripSeq")))
                }
                FAIL -> {
                    toast("저장에 실패했습니다. 다시 시도해 주세요.")
                }
            }
        }
    }

    private val setPhotos: (Boolean, Boolean) -> Unit = {gallery, camera ->
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

        imagePath = it.getUriFilePath(requireContext().applicationContext, true)
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