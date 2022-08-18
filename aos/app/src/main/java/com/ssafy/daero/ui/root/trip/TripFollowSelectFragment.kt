package com.ssafy.daero.ui.root.trip

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.entity.TripFollow
import com.ssafy.daero.databinding.FragmentTripFollowSelectBinding
import com.ssafy.daero.ui.adapter.trip.TripFollowSelectAdapter
import com.ssafy.daero.ui.root.RootFragment
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.view.toast

class TripFollowSelectFragment :
    BaseFragment<FragmentTripFollowSelectBinding>(R.layout.fragment_trip_follow_select) {

    private val tripFollowViewModel: TripFollowViewModel by viewModels()
    private lateinit var tripFollowSelectAdapter: TripFollowSelectAdapter
    private val result = mutableListOf<Int>()
    private var count = 0

    private val tripFollowSelectClickListener: (View, Int, Int) -> Unit =
        { _, tripPlaceSeq, position ->
            tripFollowSelectAdapter.tripFollowSelectResponseDto[position].isSelected
            if (tripFollowSelectAdapter.tripFollowSelectResponseDto[position].isSelected) {
                tripFollowSelectAdapter.tripFollowSelectResponseDto[position].isSelected = false
                binding.textTripTripFollowSelectCount.text = "${--count}개"
            } else {
                tripFollowSelectAdapter.tripFollowSelectResponseDto[position].isSelected = true
                binding.textTripTripFollowSelectCount.text = "${++count}개"
            }
            tripFollowSelectAdapter.notifyItemChanged(position)
        }

    override fun init() {
        initView()
        setOnClickListener()
        observeData()
    }

    private fun initView() {

        tripFollowViewModel.getTripFollow(arguments!!.getInt(ARTICLE_SEQ, 0))
    }

    private fun setOnClickListener() {
        binding.buttonTripTripFollowSelectTripStamp.setOnClickListener {
            tripFollowSelectAdapter.tripFollowSelectResponseDto.forEach {
                if (it.isSelected) {
                    result.add(it.trip_place_seq)
                }
            }

            if (result.isEmpty()) {
                toast("여행지를 1개 이상 선택해주세요.")
                return@setOnClickListener
            }

            for (i in result) {
                tripFollowViewModel.insertTripFollow(TripFollow(i))
            }

            App.prefs.curPlaceSeq = result[0]

            App.prefs.isFollow = true
            App.prefs.tripState = TRIP_ING
            RootFragment.selectPosition = R.id.TripFragment
            findNavController().navigate(
                R.id.action_tripFollowSelectFragment_to_rootFragment
            )
        }
        binding.imgTripFollowSelectBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun observeData() {
        tripFollowViewModel.responseState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    setBinding()
                    tripFollowViewModel.responseState.value = DEFAULT
                }
                FAIL -> {
                    toast("여행지 정보를 불러오는데 실패했습니다.")
                    tripFollowViewModel.responseState.value = DEFAULT
                }
            }
        }
    }

    private fun setBinding() {
        tripFollowSelectAdapter = TripFollowSelectAdapter().apply {
            this.onItemClickListener = tripFollowSelectClickListener
            tripFollowSelectResponseDto = tripFollowViewModel.resultTripFollow
        }
        binding.recyclerTripFollowSelect.apply {
            adapter = tripFollowSelectAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
}