package com.ssafy.daero.ui.root.trip

import android.util.Log
import androidx.core.content.ContextCompat
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentTripStampBinding

class TripStampFragment : BaseFragment<FragmentTripStampBinding>(R.layout.fragment_trip_stamp) {
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
            imageTripStampThumbup.setOnClickListener {
                isGood = true
                imageTripStampThumbup.setColorFilter(ContextCompat.getColor(requireActivity().applicationContext, R.color.primaryColor))
                imageTripStampThumbDown.setColorFilter(ContextCompat.getColor(requireActivity().applicationContext, R.color.lightGray))
                Log.d("TripStampDetailVM_DaeRo", "setOnClickListeners: ${imageTripStampThumbup.imageTintList.toString()}")
                Log.d("TripStampDetailVM_DaeRo", "setOnClickListeners: ${imageTripStampThumbDown.imageTintList.toString()}")

            }

            imageTripStampThumbDown.setOnClickListener {
                isGood = false
                imageTripStampThumbDown.setColorFilter(ContextCompat.getColor(requireActivity().applicationContext, R.color.primaryColor))
                imageTripStampThumbup.setColorFilter(ContextCompat.getColor(requireActivity().applicationContext, R.color.lightGray))
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
//        }

            buttonTripStampShare.setOnClickListener {
                // TODO: 트립스탬프 카카오톡 공유하기
                // 참고: https://youngest-programming.tistory.com/530
            }

            imgTripStampBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

        }
    }
}