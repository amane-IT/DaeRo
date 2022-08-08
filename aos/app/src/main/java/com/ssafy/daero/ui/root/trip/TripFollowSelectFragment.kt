package com.ssafy.daero.ui.root.trip

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.entity.TripFollow
import com.ssafy.daero.databinding.FragmentTripFollowSelectBinding
import com.ssafy.daero.ui.adapter.sns.CommentAdapter
import com.ssafy.daero.ui.adapter.trip.TripFollowSelectAdapter
import com.ssafy.daero.utils.constant.ARTICLE_SEQ
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast

class TripFollowSelectFragment : BaseFragment<FragmentTripFollowSelectBinding>(R.layout.fragment_trip_follow_select) {

    private val tripFollowViewModel: TripFollowViewModel by viewModels()
    private lateinit var tripFollowSelectAdapter: TripFollowSelectAdapter
    private val result = mutableListOf<Int>()

    private val tripFollowSelectClickListener: (View, Int, Int) -> Unit = { _, tripPlaceSeq, position ->
        // TODO: 선택, 선택취소 각각의 상태에 따라 리스트에 추가 제거
        tripFollowSelectAdapter.tripFollowSelectResponseDto[position].isSelected
        if(tripFollowSelectAdapter.tripFollowSelectResponseDto[position].isSelected){
            tripFollowSelectAdapter.tripFollowSelectResponseDto[position].isSelected = false
            result.remove(tripPlaceSeq)
            binding.textTripTripFollowSelectCount.text = result.size.toString()+"개"
        } else {
            tripFollowSelectAdapter.tripFollowSelectResponseDto[position].isSelected = true
            result.add(tripPlaceSeq)
            binding.textTripTripFollowSelectCount.text = result.size.toString()+"개"
        }
        tripFollowSelectAdapter.notifyDataSetChanged()
    }

    override fun init() {
        initView()
        setOnClickListener()
        observeData()
    }

    private fun initView(){

        tripFollowViewModel.getTripFollow(arguments!!.getInt(ARTICLE_SEQ, 0))
    }

    private fun setOnClickListener(){
        binding.buttonTripTripFollowSelectTripStamp.setOnClickListener {
            for(i in result){
                tripFollowViewModel.insertTripFollow(TripFollow(i))
            }
            //todo : 여행중으로 이동

        }
        binding.imgTripFollowSelectBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun observeData(){
        tripFollowViewModel.responseState.observe(viewLifecycleOwner) {
            when(it) {
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

    private fun setBinding(){
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