package com.ssafy.daero.ui.root.sns

import android.util.Log
import androidx.core.content.ContextCompat
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentTripStampDetailBinding
import com.ssafy.daero.ui.root.trip.TripStampBottomSheetFragment

class TripStampDetailFragment : BaseFragment<FragmentTripStampDetailBinding>(R.layout.fragment_trip_stamp_detail) {

    private var isGood = true
    override fun init() {
        initView()
        setOnClickListeners()
    }

    private fun initView(){
        binding.apply {
            // TODO: 이미지뷰에 사진 & 장소명 & 날짜 뿌리기
        }
    }

    private fun setOnClickListeners(){
        binding.apply {
            imageTripStampDetailThumbup.setOnClickListener {
                isGood = true
                imageTripStampDetailThumbup.setColorFilter(ContextCompat.getColor(requireActivity().applicationContext, R.color.primaryColor))
                imageTripStampDetailThumbDown.setColorFilter(ContextCompat.getColor(requireActivity().applicationContext, R.color.lightGray))
                Log.d("TripStampDetailVM_DaeRo", "setOnClickListeners: ${imageTripStampDetailThumbup.imageTintList.toString()}")
                Log.d("TripStampDetailVM_DaeRo", "setOnClickListeners: ${imageTripStampDetailThumbDown.imageTintList.toString()}")

            }

            imageTripStampDetailThumbDown.setOnClickListener {
                isGood = false
                imageTripStampDetailThumbDown.setColorFilter(ContextCompat.getColor(requireActivity().applicationContext, R.color.primaryColor))
                imageTripStampDetailThumbup.setColorFilter(ContextCompat.getColor(requireActivity().applicationContext, R.color.lightGray))
                Log.d("TripStampDetailVM_DaeRo", "setOnClickListeners: ThumbDown")
            }

            buttonTripStampSave.setOnClickListener {
                // TODO: 트립스탬프 저장 기능 구현
            }


//          buttonTripStampRetry.setOnClickListener {
                // TODO: 트립스탬프 재생성
//                TripStampBottomSheetFragment().show(
//                    childFragmentManager,
//                    "TripStampBottomSheetFragment"
//                )
            }

            buttonTripStampShare.setOnClickListener {
                // TODO: 트립스탬프 카카오톡 공유하기
                // 참고: https://youngest-programming.tistory.com/530
            }

            imgSignupEmailBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

        }
    }
}