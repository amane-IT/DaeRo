package com.ssafy.daero.ui.root.trip

import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentTripNextBinding
import com.ssafy.daero.ui.adapter.TripNearByAdapter
import com.ssafy.daero.ui.adapter.TripUntilNowAdapter
import com.ssafy.daero.ui.root.RootFragment
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.file.deleteCache
import com.ssafy.daero.utils.view.toast

class TripNextFragment : BaseFragment<FragmentTripNextBinding>(R.layout.fragment_trip_next) {

    private val tripNextViewModel: TripNextViewModel by viewModels()
    private lateinit var tripNearByAdapter: TripNearByAdapter
    private lateinit var tripUntilNowAdapter: TripUntilNowAdapter

    override fun init() {
        setTripState()
        initView()
        initAdapter()
        observeData()
        setOnClickListeners()
        getTripStamps()
        getAroundTrips()
    }

    private fun setTripState() {
        // 다음 여행지 추천 화면으로 온경우 다음 여행지 추천상태로 변경
        App.prefs.isFirstTrip = false
    }

    private fun initView() {
        binding.textTripNextTitle1.text = "${App.prefs.nickname}님"
    }

    private fun initAdapter() {
        tripNearByAdapter = TripNearByAdapter().apply {
            onItemClickListener = nearByTripPlaceClickListener
        }
        binding.recyclerTripNextNearBy.adapter = tripNearByAdapter

        tripUntilNowAdapter = TripUntilNowAdapter().apply {
            onItemClickListener = tripStampClickListener
        }
        binding.recyclerTripNextNow.adapter = tripUntilNowAdapter
    }

    private val nearByTripPlaceClickListener: (View, Int) -> Unit = { _, tripPlaceSeq ->
        findNavController().navigate(
            R.id.action_rootFragment_to_tripInformationFragment,
            bundleOf(PLACE_SEQ to tripPlaceSeq, IS_RECOMMEND to false)
        )
    }

    private val tripStampClickListener: (View, Int) -> Unit = { _, tripStampId ->
        findNavController().navigate(
            R.id.action_rootFragment_to_tripStampFragment,
            bundleOf(TRIP_STAMP_ID to tripStampId, IS_TRIP_STAMP_UPDATE to true)
        )
    }

    private val applyOptions: (Int, String) -> Unit = { time, transportation ->
        App.prefs.tripTime = time
        App.prefs.tripTransportation = transportation
        tripNextViewModel.recommendNextPlace(time, transportation)
    }

    private fun setOnClickListeners() {
        binding.apply {

            buttonTripNextNextTripRecommend.setOnClickListener {
                TripNextBottomSheetFragment(applyOptions).show(
                    childFragmentManager,
                    "TripNextBottomSheetFragment"
                )
            }

            // TODO: 여행 그만두기 기능 = 게시글 작성
            buttonTripNextStop.setOnClickListener {
                TripCompleteBottomSheetFragment(finishTrip, doneTrip)
                    .show(childFragmentManager, TRIP_COMPLETE_BOTTOM_SHEET)
            }

            imageTripNextNotification.setOnClickListener {
                findNavController().navigate(R.id.action_rootFragment_to_notificationFragment)
            }
        }
    }

    private val finishTrip : () -> Unit = {
        // 게시글 작성 상태로 변경
        App.prefs.isPosting = true

        // 상태 잠시 변경
        App.isDone = true

        // 게시글 추가 화면으로 이동
        findNavController().navigate(R.id.action_rootFragment_to_articleWriteDayFragment)
    }

    private val doneTrip: () -> Unit = {
        // 캐시 디렉토리 전체 삭제
        deleteCache(requireContext())

        // Room 에 저장되어있는 TripStamp, TripFollow 전체 삭제
        tripNextViewModel.deleteAllTripRecord()

        // Prefs 초기화
        App.prefs.initTrip()
        (requireParentFragment() as RootFragment).changeTripState(TRIP_BEFORE)
    }

    private fun getTripStamps() {
        tripNextViewModel.getTripStamps()
    }

    private fun getAroundTrips() {
        tripNextViewModel.getAroundTrips(App.prefs.curPlaceSeq)
    }

    private fun observeData() {
        tripNextViewModel.aroundTrips.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.textTripNextNoNearBy.visibility = View.VISIBLE
            } else {
                binding.textTripNextNoNearBy.visibility = View.GONE
                tripNearByAdapter.apply {
                    tripPlaces = it
                    notifyDataSetChanged()
                }
            }
        }
        tripNextViewModel.tripStamps.observe(viewLifecycleOwner) {
            tripUntilNowAdapter.apply {
                tripStamps = it
                notifyDataSetChanged()
            }
        }
        tripNextViewModel.showProgress.observe(viewLifecycleOwner) {
            binding.progressBarTripNextLoading.isVisible = it
        }
        tripNextViewModel.nextTripRecommendResponseDto.observe(viewLifecycleOwner) { placeSeq ->
            if (placeSeq > 0) {
                findNavController().navigate(
                    R.id.action_rootFragment_to_tripInformationFragment, bundleOf(
                        PLACE_SEQ to placeSeq
                    )
                )
                tripNextViewModel.initNextTripRecommend()
            }
        }
        tripNextViewModel.nextTripRecommendState.observe(viewLifecycleOwner) {
            when (it) {
                FAIL -> {
                    toast("여행지 추천을 받는데 실패했습니다.")
                    tripNextViewModel.nextTripRecommendState.value = DEFAULT
                }
                EMPTY -> {
                    toast("해당 조건에 맞는 여행지가 없습니다.")
                    tripNextViewModel.nextTripRecommendState.value = DEFAULT
                }
            }
        }
        tripNextViewModel.imageUrl.observe(viewLifecycleOwner) {
            if(it.isNotBlank()) {
                Glide.with(requireContext()).load(it)
                tripNextViewModel.imageUrl.value = ""
            }
        }
    }
}
