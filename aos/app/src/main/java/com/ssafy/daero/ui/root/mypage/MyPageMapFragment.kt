package com.ssafy.daero.ui.root.mypage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.UiSettings
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentMyPageMapBinding

class MyPageMapFragment : BaseFragment<FragmentMyPageMapBinding>(R.layout.fragment_my_page_map), OnMapReadyCallback {
    private val myPageViewModel : MyPageViewModel by viewModels ({ requireParentFragment() })

    private var naverMap: NaverMap? = null
    private var uiSettings: UiSettings? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initNaverMap()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun init() {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.fabMyPageMapFilter.setOnClickListener {
            // todo: FilterBottomSheet 띄우기
            MapFilterBottomSheetFragment().show(
                childFragmentManager,
                "MapFilterBottomSheetFragment"
            )
        }
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if(menuVisible) {
            (requireParentFragment() as MyPageFragment).disableSlide()
        }
    }

    private fun initNaverMap() {
        val _naverMap =
            childFragmentManager.findFragmentById(R.id.fragmentContainer_myPageMap) as MapFragment?
                ?: MapFragment.newInstance().also {
                    childFragmentManager.beginTransaction()
                        .add(R.id.fragmentContainer_myPageMap, it)
                        .commit()
                }
        _naverMap.getMapAsync(this)
    }

    override fun onMapReady(_naverMap: NaverMap) {
        naverMap = _naverMap

        setNaverMapUI()
    }

    private fun setNaverMapUI() {
        naverMap?.apply {
            isLiteModeEnabled = true // 가벼운 지도 모드 (건물 내부 상세 표시 X)

            this@MyPageMapFragment.uiSettings = this.uiSettings.apply {
                isCompassEnabled = false // 나침반 비활성화
                isZoomControlEnabled = false // 확대 축소 버튼 비활성화
                isScaleBarEnabled = false // 스케일 바 비활성화
                isLocationButtonEnabled = false // 기본 내 위치 버튼 비활성화
            }
        }
    }
}