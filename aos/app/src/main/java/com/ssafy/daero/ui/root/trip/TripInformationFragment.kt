package com.ssafy.daero.ui.root.trip

import android.Manifest
import android.graphics.Paint
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.trip.FirstTripRecommendRequestDto
import com.ssafy.daero.databinding.FragmentTripInformationBinding
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.permission.checkPermission
import com.ssafy.daero.utils.permission.requestPermission
import com.ssafy.daero.utils.tag.TagCollection
import com.ssafy.daero.utils.view.setStatusBarOrigin
import com.ssafy.daero.utils.view.setStatusBarTransparent
import com.ssafy.daero.utils.view.toast

class TripInformationFragment :
    BaseFragment<FragmentTripInformationBinding>(R.layout.fragment_trip_information) {
    private val tripInformationViewModel: TripInformationViewModel by viewModels()

    // 여행지 정보 seq
    private var placeSeq = 0

    // 여행지 추천받았는지 여부 (추천안받고 내 주변 여행지 또는 인기있는 여행지 경우 false
    private var isRecommend = true

    // 여행 중 화면에서 다시 추천받기 눌러서 온경우
    private var isReRecommend = false

    // 첫 여행지 추천 시 선택한 키워드 태그들
    private var tagCollection: TagCollection? = null

    override fun init() {
        initData()
        initView()
        observeData()
        setOnClickListeners()
    }

    private fun initData() {
        placeSeq = arguments!!.getInt(PLACE_SEQ, 0)
        tagCollection = arguments?.getParcelable<TagCollection>(TAG_COLLECTION)
        isRecommend = arguments?.getBoolean(IS_RECOMMEND, true) ?: true
        isReRecommend = arguments?.getBoolean(IS_RE_RECOMMEND, false) ?: false


        if (placeSeq > 0) {
            tripInformationViewModel.getTripInformation(placeSeq)
        } else {
            toast("여행지 정보를 불러오는데 실패했습니다.")
        }
    }


    private fun initView() {
        binding.textTripInformationFold.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        if (!isRecommend) {
            binding.buttonTripInformationReRecommend.text = "돌아가기"
        }
    }

    private fun observeData() {
        tripInformationViewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBarTripInformationLoading.isVisible = it
        }
        tripInformationViewModel.tripInformationState.observe(viewLifecycleOwner) {
            when (it) {
                FAIL -> {
                    toast("여행지 정보를 불러오는데 실패했습니다.")
                    tripInformationViewModel.tripInformationState.value = DEFAULT
                }
            }
        }
        tripInformationViewModel.tripInformation.observe(viewLifecycleOwner) {
            binding.tripInfo = it
        }
        tripInformationViewModel.placeSeq.observe(viewLifecycleOwner) {
            if (it > 0) {
                placeSeq = it
                tripInformationViewModel.placeSeq.value = 0
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageTripInformationBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.buttonTripInformationStartTrip.setOnClickListener {
            // 권한 체크
            if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                // 권한 요청
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, {
                    startTrip()
                }, {
                    toast("권한이 없으면 여행을 할 수 없습니다.")
                })
            } else {
                startTrip()
            }
        }
        binding.buttonTripInformationReRecommend.setOnClickListener {
            // 여행지 추천받고온 상태가 아니라면
            if (!isRecommend) {
                requireActivity().onBackPressed()
                return@setOnClickListener
            }

            // 첫 여행지 추천 상태일 경우, 다시 추천
            if (App.prefs.isFirstTrip) {
                tripInformationViewModel.getReFirstTripRecommend(
                    FirstTripRecommendRequestDto(
                        regions = tagCollection?.regionTags ?: listOf(),
                        tags = tagCollection?.categoryTags ?: listOf()
                    )
                )
            }
            // 다음 여행지 추천 상태일 경우
            else {
                tripInformationViewModel.getNextTripRecommend()
            }
        }
        binding.textTripInformationFold.setOnClickListener {
            if (binding.scrollTripInformationDescription.visibility == View.VISIBLE) {
                binding.textTripInformationFold.text = "펼치기"
                binding.scrollTripInformationDescription.visibility = View.GONE
            } else {
                binding.textTripInformationFold.text = "접기"
                binding.scrollTripInformationDescription.visibility = View.VISIBLE
            }
        }
    }

    private fun startTrip() {
        // 현재 추천받은 여행지 seq 저장
        App.prefs.curPlaceSeq = placeSeq
        if (!isReRecommend) {
            // 여행 시작 했는지 체크 -> RootFragment 에서 체크 후 여행 중 화면으로 전환
            App.prefs.isTripStart = true
        }

        requireActivity().onBackPressed()
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