package com.ssafy.daero.ui.root.trip

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.trip.FirstTripRecommendRequestDto
import com.ssafy.daero.databinding.FragmentTripBinding
import com.ssafy.daero.ui.adapter.trip.TripHotAdapter
import com.ssafy.daero.ui.adapter.trip.TripPopularAdapter
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.hotArticles
import com.ssafy.daero.utils.tag.TagCollection
import com.ssafy.daero.utils.view.toast

class TripFragment : BaseFragment<FragmentTripBinding>(R.layout.fragment_trip) {
    private val tripViewModel: TripViewModel by viewModels()
    private lateinit var tripPopularAdapter: TripPopularAdapter
    private lateinit var tripHotAdapter: TripHotAdapter

    lateinit var loadingDialog: LoadingDialogFragment
    private lateinit var bottomSheet: BottomSheetBehavior<CardView>
    private var cornerRadius: Float = 0f
    private var peekHeight: Int = 0

    private var categoryTags = listOf<Int>()
    private var regionTags = listOf<Int>()

    override fun init() {
        initData()
        initView()
        initAdapter()
        observeData()
        setOnClickListeners()
        otherListeners()
        getPopularTrip()
    }

    private fun initData() {
        categoryTags = listOf()
        regionTags = listOf()
    }

    private fun initView() {
        loadingDialog = LoadingDialogFragment.newInstance()
        bottomSheet = BottomSheetBehavior.from(binding.cardTripRecommend)
        bottomSheet.saveFlags = BottomSheetBehavior.SAVE_PEEK_HEIGHT
        peekHeight = bottomSheet.peekHeight
        cornerRadius = binding.cardTripRecommend.radius
        binding.textTripKeyword.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.textTripUsername.text = "${App.prefs.nickname}님"
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
        val bundle = Bundle().apply {
            putInt(PLACE_SEQ, tripPlaceSeq)
            putInt(TRIP_KIND, FIRST_TRIP)
            putParcelable(TAG_COLLECTION, TagCollection(categoryTags, regionTags))
        }

        findNavController().navigate(
            R.id.action_rootFragment_to_tripInformationFragment,
            bundle
        )

        tripViewModel.initTripInformation()
    }

    private val hotArticleClickListener: (View, Int) -> Unit = { _, articleSeq ->
        // todo: 상세 게시글로 이동
    }

    private fun setOnClickListeners() {
        binding.textTripKeyword.setOnClickListener {
            TagBottomSheetFragment(categoryTags, regionTags, applyFilter).show(
                childFragmentManager,
                "TagBottomSheetFragment"
            )
        }
        binding.imageTripNotification.setOnClickListener {
            findNavController().navigate(R.id.action_rootFragment_to_notificationFragment)
        }
    }

    private val applyFilter: (List<Int>, List<Int>) -> Unit = { categoryTags, regionTags ->
        this.categoryTags = categoryTags
        this.regionTags = regionTags
    }

    private fun otherListeners() {
        bottomSheet.addBottomSheetCallback(bottomSheetCallback)
    }

    private val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_EXPANDED -> {
                    tripViewModel.getFirstTripRecommend(
                        FirstTripRecommendRequestDto(
                            categoryTags, regionTags
                        )
                    )
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
    }

    private fun observeData() {
        tripViewModel.popularTrip.observe(viewLifecycleOwner) {
            tripPopularAdapter.tripPlaces = it
            tripPopularAdapter.notifyDataSetChanged()
        }

        // todo: 핫한 여행기 받아오기
        tripHotAdapter.tripHots = hotArticles

        tripViewModel.showProgress.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    showProgressDialog()
                    tripViewModel.showProgress.value = DEFAULT
                }
                FAIL -> {
                    hideProgressDialog()
                    tripViewModel.showProgress.value = DEFAULT
                }
            }
        }
        tripViewModel.firstTripRecommendState.observe(viewLifecycleOwner) {
            when (it) {
                FAIL -> {
                    toast("여행지 추천을 받는데 실패했습니다.")
                    bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                    tripViewModel.firstTripRecommendState.value = DEFAULT
                }
            }
        }
        tripViewModel.firstTripRecommendResponseDto.observe(viewLifecycleOwner, tripInformationObserver)
    }

    private val tripInformationObserver = { place_seq : Int ->
        if(place_seq > 0) {
            val bundle = Bundle().apply {
                putInt(PLACE_SEQ, place_seq)
                putInt(TRIP_KIND, FIRST_TRIP)
                putParcelable(TAG_COLLECTION, TagCollection(categoryTags, regionTags))
            }

            findNavController().navigate(
                R.id.action_rootFragment_to_tripInformationFragment,
                bundle
            )

            tripViewModel.initTripInformation()
        }
    }

    private fun getPopularTrip() {
        tripViewModel.getPopularTrips()
    }

    private fun showProgressDialog() {
        loadingDialog.show(
            requireActivity().supportFragmentManager,
            loadingDialog.tag
        )
    }

    private fun hideProgressDialog() {
        if (loadingDialog.isAdded) {
            loadingDialog.dismissAllowingStateLoss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tripViewModel.firstTripRecommendResponseDto.removeObserver(tripInformationObserver)
        bottomSheet.removeBottomSheetCallback(bottomSheetCallback)
    }
}