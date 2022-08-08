package com.ssafy.daero.ui.root.sns

import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentTripStampDetailBinding
import com.ssafy.daero.utils.constant.SUCCESS

class TripStampDetailFragment : BaseFragment<FragmentTripStampDetailBinding>(R.layout.fragment_trip_stamp_detail) {

    private val tripStampDetailViewModel: TripStampDetailViewModel by viewModels()

    override fun init() {
        initData()
        observeData()
        setOnClickListeners()
    }

    private fun initData(){
        val tripStampSeq = arguments?.getInt("tripStampSeq", 0) ?: 0
        tripStampDetailViewModel.getTripStampDetail(tripStampSeq)
    }

    private fun observeData(){
        tripStampDetailViewModel.tripStampDetail.observe(viewLifecycleOwner) {
            binding.textItemTripStampDetailTitle.text = it.place
            binding.textItemTripStampDetailDate.text = it.datetime

            Glide.with(requireContext())
                .load(it.image_url)
                .placeholder(R.drawable.placeholder_trip_album)
                .apply(RequestOptions().centerCrop())
                .error(R.drawable.placeholder_trip_album)
                .into(binding.imageTripStampDetailStamp)
        }

    }

    private fun setOnClickListeners(){
        binding.apply {
            buttonTripStampDetailShare.setOnClickListener {
                // TODO: 트립스탬프 카카오톡으로 공유 기능 구현
            }

            imgTripStampDetailBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }
}
