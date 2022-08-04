package com.ssafy.daero.ui.root.sns

import android.util.Log
import androidx.core.content.ContextCompat
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentTripStampDetailBinding
import com.ssafy.daero.ui.root.trip.TripStampBottomSheetFragment

class TripStampDetailFragment : BaseFragment<FragmentTripStampDetailBinding>(R.layout.fragment_trip_stamp_detail) {

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
            buttonTripStampDetailShare.setOnClickListener {
                // TODO: 트립스탬프 공유 기능 구현
            }


//          buttonTripStampRetry.setOnClickListener {
                // TODO: 트립스탬프 재생성
//                TripStampBottomSheetFragment().show(
//                    childFragmentManager,
//                    "TripStampBottomSheetFragment"
//                )
//            }

            imgTripStampDetailBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }
}