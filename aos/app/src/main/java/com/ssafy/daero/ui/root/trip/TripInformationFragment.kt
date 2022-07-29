package com.ssafy.daero.ui.root.trip

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.trip.FirstTripRecommendRequestDto
import com.ssafy.daero.databinding.FragmentTripInformationBinding
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.tag.TagCollection
import com.ssafy.daero.utils.view.setStatusBarOrigin
import com.ssafy.daero.utils.view.setStatusBarTransparent
import com.ssafy.daero.utils.view.toast

class TripInformationFragment : BaseFragment<FragmentTripInformationBinding>(R.layout.fragment_trip_information) {
    private val tripInformationViewModel : TripInformationViewModel by viewModels()
    private var placeSeq = 0
    private var tagCollection: TagCollection? = null
    private var tripKind = 0

    override fun init() {
        initData()
        observeData()
        setOnClickListeners()
    }

    private fun initData() {
        placeSeq = arguments!!.getInt(PLACE_SEQ, 0)
        tagCollection = arguments!!.getParcelable<TagCollection>(TAG_COLLECTION)
        tripKind = arguments!!.getInt(TRIP_KIND, 0)


        if(placeSeq > 0) {
            tripInformationViewModel.getTripInformation(placeSeq)
        } else {
            toast("여행지 정보를 불러오는데 실패했습니다.")
        }
    }

    private fun observeData() {
        tripInformationViewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBarTripInformationLoading.isVisible = it
        }
        tripInformationViewModel.tripInformationState.observe(viewLifecycleOwner) {
            when(it) {
                FAIL -> {
                    toast("여행지 정보를 불러오는데 실패했습니다.")
                    tripInformationViewModel.tripInformationState.value = DEFAULT
                }
            }
        }
        tripInformationViewModel.tripInformation.observe(viewLifecycleOwner) {
            binding.tripInfo = it
        }
    }

    private fun setOnClickListeners() {
        binding.imageTripInformationBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.buttonTripInformationStartTrip.setOnClickListener {
            // todo : 여행 시작하기 기능
        }
        binding.buttonTripInformationReRecommend.setOnClickListener {
            when(tripKind) {
                // 첫 여행지 다시 추천
                FIRST_TRIP -> {
                    tripInformationViewModel.getReFirstTripRecommend(
                        FirstTripRecommendRequestDto(
                            regions = tagCollection?.regionTags ?: listOf(),
                            tags = tagCollection?.categoryTags ?: listOf()
                        )
                    )
                }
                // 다음 여행지 다시 추천
                NEXT_TRIP -> {
                    // todo: 다음 여행지 추천일때, 다시 추천 기능
                }
            }
        }
    }

    private fun setStatusBarTransParent() {
        binding.constraintTripInformationInnerContainer.setStatusBarTransparent(requireActivity())
    }

    private fun setStatusBarOrigin() {
        requireActivity().setStatusBarOrigin()
    }

    override fun onStart() {
        super.onStart()
        setStatusBarTransParent()
    }

    override fun onStop() {
        super.onStop()
        setStatusBarOrigin()
    }
}