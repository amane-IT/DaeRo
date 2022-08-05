package com.ssafy.daero.ui.root.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.trip.MyJourneyResponseDto
import com.ssafy.daero.databinding.FragmentOtherPageMapBinding
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.getPxFromDp
import com.ssafy.daero.utils.view.toast

class OtherPageMapFragment : BaseFragment<FragmentOtherPageMapBinding>(R.layout.fragment_other_page_map),
    OnMapReadyCallback {
    private val otherPageViewModel: OtherPageViewModel by viewModels({requireParentFragment()})

    private var naverMap: NaverMap? = null
    private var uiSettings: UiSettings? = null
    private var markers = mutableListOf<MutableList<Marker>>()
    private var paths = mutableListOf<PathOverlay>()

    private var userSeq = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initNaverMap()
        userSeq = (requireParentFragment() as OtherPageFragment).userSeq
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun init() {
        setOnClickListeners()
        observeData()
    }

    private fun observeData() {
        otherPageViewModel.getJourneyState.observe(viewLifecycleOwner) {
            when (it) {
                FAIL -> {
                    toast("여정 조회를 가져오는데 실패했습니다.")
                    deleteMarkers()
                    deletePaths()
                    otherPageViewModel.getJourneyState.value = DEFAULT
                }
                SUCCESS -> {
                    toast("여정이 없습니다.")
                    deleteMarkers()
                    deletePaths()
                    otherPageViewModel.getJourneyState.value = DEFAULT
                }
            }
        }
        otherPageViewModel.myJourney.observe(viewLifecycleOwner) {
            deleteMarkers()
            deletePaths()
            drawMarkers(it)
            drawPolyline(it)
        }
    }

    private fun deleteMarkers() {
        if (markers.isNotEmpty()) {
            markers.forEach { list ->
                list.forEach {
                    it.map = null
                }
            }
        }
        markers = mutableListOf()
    }

    private fun deletePaths() {
        if (paths.isNotEmpty()) {
            paths.forEach {
                it.map = null
            }
        }
        paths = mutableListOf()
    }

    private fun drawMarkers(journey: List<List<MyJourneyResponseDto>>) {
        if (journey.isNotEmpty()) {
            journey.forEachIndexed { idx, trips ->
                markers.add(mutableListOf())
                markers[idx] = trips.map { trip ->
                    createMarker(trip)
                }.toMutableList()
            }

            naverMap?.moveCamera(
                CameraUpdate.scrollTo(
                    LatLng(
                        journey.last().last().latitude,
                        journey.last().last().longitude
                    )
                )
            )
            naverMap?.moveCamera(CameraUpdate.zoomTo(10.0))
        }
    }

    private fun createMarker(trip: MyJourneyResponseDto): Marker {
        return Marker().apply {
            position = LatLng(trip.latitude, trip.longitude)    // 마커 좌표
            icon = OverlayImage.fromResource(R.drawable.ic_marker)
            iconTintColor = requireActivity().getColor(R.color.primaryDarkColor)// 마커 색깔
            width = requireContext().getPxFromDp(40f)   // 마커 가로 크기
            height = requireContext().getPxFromDp(40f)  // 마커 세로 크기
            zIndex = 0  // 마커 높이
            onClickListener = Overlay.OnClickListener {     // 마커 클릭 리스너
                // todo: trip_seq 이용해서 트립스탬프 상세화면으로 이동
                return@OnClickListener true
            }
            isHideCollidedMarkers = true    // 겹치면 다른 마커 숨기기
            map = naverMap  // 지도에 마커 표시
        }
    }

    private fun drawPolyline(journey: List<List<MyJourneyResponseDto>>) {
        if (journey.isNotEmpty()) {
            journey.forEachIndexed { idx, trips ->
                if (trips.size >= 2) {  // 한 여행에서 두 개 이상 여행지 방문했을 때만 경로 그리기
                    paths.add(PathOverlay().apply {
                        color = requireActivity().getColor(R.color.red) // 경로 색깔
                        outlineColor = requireActivity().getColor(R.color.red) // 경로 색깔
                        outlineWidth = requireContext().getPxFromDp(1.5f) // 경로 두께
                        coords =
                            trips.map { trip -> LatLng(trip.latitude, trip.longitude) }    // 경로 좌표
                        map = naverMap
                    })
                }
            }
        }
    }

    private val applyFilter: (String, String) -> Unit = { startDate, endDate ->
        getJourney(startDate, endDate)
    }

    private fun setOnClickListeners() {
        binding.fabOtherPageMapFilter.setOnClickListener {
            MapFilterBottomSheetFragment(applyFilter).show(
                childFragmentManager,
                "MapFilterBottomSheetFragment"
            )
        }
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible) {
            (requireParentFragment() as OtherPageFragment).disableSlide()
        }
    }

    private fun initNaverMap() {
        val _naverMap =
            childFragmentManager.findFragmentById(R.id.fragmentContainer_otherPageMap) as MapFragment?
                ?: MapFragment.newInstance().also {
                    childFragmentManager.beginTransaction()
                        .add(R.id.fragmentContainer_otherPageMap, it)
                        .commit()
                }
        _naverMap.getMapAsync(this)
    }

    override fun onMapReady(_naverMap: NaverMap) {
        naverMap = _naverMap

        setNaverMapUI()
        getJourney("", "")
    }

    private fun setNaverMapUI() {
        naverMap?.apply {
            isLiteModeEnabled = true // 가벼운 지도 모드 (건물 내부 상세 표시 X)

            this@OtherPageMapFragment.uiSettings = this.uiSettings.apply {
                isCompassEnabled = false // 나침반 비활성화
                isZoomControlEnabled = false // 확대 축소 버튼 비활성화
                isScaleBarEnabled = false // 스케일 바 비활성화
                isLocationButtonEnabled = false // 기본 내 위치 버튼 비활성화
            }
        }
    }

    private fun getJourney(startDate: String, endDate: String) {
        otherPageViewModel.getJourney(userSeq, startDate, endDate)
    }

}