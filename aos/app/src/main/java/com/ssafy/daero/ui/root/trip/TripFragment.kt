package com.ssafy.daero.ui.root.trip

import android.graphics.Paint
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.trip.TripPopularResponseDto
import com.ssafy.daero.databinding.FragmentTripBinding
import com.ssafy.daero.ui.adapter.TripHotAdapter
import com.ssafy.daero.ui.adapter.TripPopularAdapter
import com.ssafy.daero.utils.hotArticles
import com.ssafy.daero.utils.popularTripPlaces
import com.ssafy.daero.utils.view.getScreenHeight
import com.ssafy.daero.utils.view.toast

class TripFragment : BaseFragment<FragmentTripBinding>(R.layout.fragment_trip) {
    private val tripViewModel: TripViewModel by viewModels()
    private lateinit var tripPopularAdapter: TripPopularAdapter
    private lateinit var tripHotAdapter: TripHotAdapter

    lateinit var loadingDialog: LoadingDialogFragment
    private lateinit var bottomSheet : BottomSheetBehavior<CardView>
    private var cornerRadius : Float = 0f


    override fun init() {
        initView()
        initAdapter()
        observeData()
        setOnClickListeners()
        otherListeners()
    }

    private fun initView() {
        loadingDialog = LoadingDialogFragment.newInstance()
        bottomSheet = BottomSheetBehavior.from(binding.cardTripRecommend)
        bottomSheet.saveFlags = BottomSheetBehavior.SAVE_PEEK_HEIGHT
        cornerRadius = binding.cardTripRecommend.radius
        binding.textTripKeyword.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    private fun initAdapter() {
        tripPopularAdapter = TripPopularAdapter().apply {
            onItemClickListener = popularTripPlaceClickListener
        }
        binding.recyclerTripPopular.adapter = tripPopularAdapter

        tripHotAdapter = TripHotAdapter().apply {
            onItemClickListener = hotArticleClickListener
        }
        binding.recyclerTripHot.adapter = tripHotAdapter
    }

    private val popularTripPlaceClickListener: (View, Int) -> Unit = { _, tripPlaceSeq ->
        // todo: 여행지 정보 상세 페이지로 이동
    }

    private val hotArticleClickListener: (View, Int) -> Unit = { _, articleSeq ->
        // todo: 상세 게시글로 이동
    }

    private fun setOnClickListeners() {
        binding.textTripKeyword.setOnClickListener {
            // todo: 키워드 선택 바텀싯 띄우기
            TagBottomSheetFragment().show(childFragmentManager, "TagBottomSheetFragment")
        }
        binding.imageTripNotification.setOnClickListener {
            // todo: 알림 페이지로 이동
        }
    }

    private fun otherListeners() {
        bottomSheet.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        // todo: 여행지 추천 받기 기능
                        tripViewModel.getRecommendTripPlace()
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // slideOffset 접힘 -> 펼쳐짐: 0.0 ~ 1.0
                if (slideOffset >= 0) {
                    // 둥글기는 펼칠수록 줄어들도록
                    binding.cardTripRecommend.radius = cornerRadius - (cornerRadius * slideOffset)
                    // 컨텐츠들 사라지도록
                    binding.imageTripUp.alpha = 1 - slideOffset * 2.3F
                    binding.textTripSwipeDescription.alpha = 1 - slideOffset * 2.3F
                    binding.textTripKeyword.alpha = 1 - slideOffset * 2.3F
                }
            }
        })
    }

    private fun observeData() {
        // todo: 인기있는 여행지 받아오기
        tripPopularAdapter.tripPlaces = popularTripPlaces

        // todo: 핫한 여행기 받아오기
        tripHotAdapter.tripHots = hotArticles

        tripViewModel.showProgress.observe(viewLifecycleOwner) {
            if(it) {
                showProgressDialog()
            } else {
                hideProgressDialog()

            }
        }
    }

    fun showProgressDialog() {
        loadingDialog.show(
            requireActivity().supportFragmentManager,
            loadingDialog.tag
        )
    }

    fun hideProgressDialog() {
        if (loadingDialog.isAdded) {
            loadingDialog.dismissAllowingStateLoss()
        }
    }
}