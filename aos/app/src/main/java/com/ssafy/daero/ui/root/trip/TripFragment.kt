package com.ssafy.daero.ui.root.trip

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.trip.FirstTripRecommendRequestDto
import com.ssafy.daero.data.dto.trip.FirstTripRecommendResponseDto
import com.ssafy.daero.databinding.FragmentTripBinding
import com.ssafy.daero.ui.adapter.trip.TripHotAdapter
import com.ssafy.daero.ui.adapter.trip.TripPopularAdapter
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.tag.TagCollection
import com.ssafy.daero.utils.view.toast

class TripFragment : BaseFragment<FragmentTripBinding>(R.layout.fragment_trip) {
    private val tripViewModel: TripViewModel by viewModels()
    private lateinit var tripPopularAdapter: TripPopularAdapter
    private lateinit var tripHotAdapter: TripHotAdapter

    lateinit var loadingDialog: LoadingDialogFragment
    private lateinit var bottomSheet: BottomSheetBehavior<CardView>
    private var cornerRadius: Float = 0f
    private var originPeekHeight: Int = 0

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
        getHotArticle()
    }

    private fun initData() {
        categoryTags = listOf()
        regionTags = listOf()
    }

    private fun initView() {
        loadingDialog = LoadingDialogFragment.newInstance()
        bottomSheet = BottomSheetBehavior.from(binding.cardTripRecommend)
        bottomSheet.saveFlags = BottomSheetBehavior.SAVE_PEEK_HEIGHT
        originPeekHeight = bottomSheet.peekHeight
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
        val bundle = Bundle().apply {
            putInt(PLACE_SEQ, tripPlaceSeq)
            putBoolean(IS_RECOMMEND, false)
        }

        findNavController().navigate(
            R.id.action_rootFragment_to_tripInformationFragment,
            bundle
        )

        tripViewModel.initTripInformation()
    }

    private val hotArticleClickListener: (View, Int) -> Unit = { _, articleSeq ->
        findNavController().navigate(R.id.action_rootFragment_to_articleFragment, bundleOf(
            ARTICLE_SEQ to articleSeq))
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

        binding.chipGroupTripTags.removeAllViews()

        // 태그가 비어있다면
        if(categoryTags.isEmpty() && regionTags.isEmpty()) {
            bottomSheet.peekHeight = originPeekHeight
            binding.chipGroupTripTags.visibility = View.GONE
        } else {
            binding.chipGroupTripTags.visibility = View.VISIBLE
            categoryTags.forEach {
                binding.chipGroupTripTags.addView(
                    Chip(requireContext()).apply {
                        setTextAppearanceResource(R.style.ChipText)
                        setTextColor(resources.getColorStateList(R.color.selector_chip_color_text))
                        setChipBackgroundColorResource(R.color.selector_chip_color)
                        text = com.ssafy.daero.utils.tag.categoryTags[it-1].tag
                    }
                )
            }
            regionTags.forEach {
                binding.chipGroupTripTags.addView(
                    Chip(requireContext()).apply {
                        setTextAppearanceResource(R.style.ChipText)
                        setTextColor(resources.getColorStateList(R.color.selector_chip_color_text))
                        setChipBackgroundColorResource(R.color.selector_chip_color)
                        text = com.ssafy.daero.utils.tag.regionTags[it-1].tag
                    }
                )
            }
            val tagHeight = ((((categoryTags.size + regionTags.size) / 4.5) + 1) * 150).toInt()
            bottomSheet.peekHeight = originPeekHeight + tagHeight
        }
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
                            regionTags, categoryTags
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
        tripViewModel.hotArticle.observe(viewLifecycleOwner) {
            tripHotAdapter.tripHots = it
            tripHotAdapter.notifyDataSetChanged()
        }

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

    private val tripInformationObserver = { recommend : FirstTripRecommendResponseDto ->
        if(recommend.place_seq > 0) {
            Glide.with(requireContext()).load(recommend.image_url)

            val bundle = Bundle().apply {
                putInt(PLACE_SEQ, recommend.place_seq)
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

    private fun getHotArticle() {
        tripViewModel.getHotArticle()
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