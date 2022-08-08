package com.ssafy.daero.ui.root.trip

import android.util.Log
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.trip.TripPopularResponseDto
import com.ssafy.daero.data.entity.TripFollow
import com.ssafy.daero.databinding.FragmentTripFollowBinding
import com.ssafy.daero.ui.adapter.TripNearByAdapter
import com.ssafy.daero.ui.adapter.TripUntilNowAdapter
import com.ssafy.daero.ui.root.RootFragment
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.popularTripPlaces
import com.ssafy.daero.utils.view.toast

class TripFollowFragment : BaseFragment<FragmentTripFollowBinding>(R.layout.fragment_trip_follow) {

    private val tripFollowViewModel:TripFollowViewModel by viewModels()
    private val travelingViewModel: TravelingViewModel by viewModels()
    private val tripInformationViewModel: TripInformationViewModel by viewModels()
    private lateinit var tripUntilNowAdapter: TripUntilNowAdapter
    private lateinit var tripFollowList: List<TripFollow>

    override fun init() {
        initData()
        initAdapter() // 데이터 연동 되면 지울 것!
        observeData()
        setOnClickListeners()
    }

    private fun initData(){
        tripFollowViewModel.getTripFollows()
        travelingViewModel.getTripStamps()
    }

    private fun initAdapter(){
        tripUntilNowAdapter = TripUntilNowAdapter().apply {
            onItemClickListener = tripUntilNowClickListener
        }
        binding.recyclerTripTripFollowTripStampSoFar.apply {
            adapter = tripUntilNowAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
    }

    private val tripUntilNowClickListener: (View, Int) -> Unit = { _, tripStampId ->
        findNavController().navigate(R.id.action_rootFragment_to_tripStampFragment,
            bundleOf(TRIP_STAMP_ID to tripStampId, IS_TRIP_STAMP_UPDATE to true)
        )
    }

    private fun setOnClickListeners(){
        binding.apply {
            buttonTripTripFollowTripStamp.setOnClickListener {
                //TODO: 여행 중으로 이동
                (requireParentFragment() as RootFragment).changeTripState(TRIP_ING)
            }

            // TODO: 여행 그만두기 기능
            buttonTripTripFollowStop.setOnClickListener {

            }
        }
    }

    private fun observeData() {
        // TODO: 지금까지 여행지 상세 정보 받아오기
        tripFollowViewModel.tripFollowState.observe(viewLifecycleOwner){ state ->
            when(state){
                SUCCESS -> {
                    setBinding()
                    tripFollowViewModel.tripFollowState.value = DEFAULT
                }
                FAIL -> {
                    toast("이미지 로딩에 실패했습니다.")
                    tripFollowViewModel.tripFollowState.value = DEFAULT
                }
            }
        }
        travelingViewModel.responseState.observe(viewLifecycleOwner){ state ->
            when(state){
                SUCCESS -> {
                    initAdapter()
                    travelingViewModel.responseState.value = DEFAULT
                }

                FAIL -> {
                    toast("이미지 로딩에 실패했습니다.")
                    travelingViewModel.responseState.value = DEFAULT
                }
            }
        }
        travelingViewModel.tripStamps.observe(viewLifecycleOwner) {
            tripUntilNowAdapter.tripStamps = it
            tripUntilNowAdapter.notifyDataSetChanged()
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
            Glide.with(binding.imgTripFollowTripStamp)
                .load(it.image_url)
                .placeholder(R.drawable.img_my_page_album)
                .apply(RequestOptions().centerCrop())
                .error(R.drawable.img_my_page_album)
                .into(binding.imgTripFollowTripStamp)
            binding.tvTripTripFollowTripName.text = it.place_name
            binding.tvTripTripFollowAddress.text = it.address
        }
    }

    private  fun setBinding(){
        tripFollowList = tripFollowViewModel.TripFollowData
        if(tripFollowList.size>travelingViewModel.articleTripStampData.size){
            val iterator = tripFollowViewModel.TripFollowData.iterator()
            while(iterator.hasNext()){
                val nextTripSeq = iterator.next().tripPlaceSeq
                Log.d("다음 여행지 : ", nextTripSeq.toString())
                if(App.prefs.curPlaceSeq == nextTripSeq && iterator.hasNext()){
                    App.prefs.curPlaceSeq = iterator.next().tripPlaceSeq
                    Log.d("다음 여행지 curPlaceSeq : ", App.prefs.curPlaceSeq.toString())
                    break
                }
            }
            tripInformationViewModel.getTripInformation(App.prefs.curPlaceSeq)
        }else{
            //TODO : 사이즈가 같으면 다음 여행지 없음 표시
            binding.buttonTripTripFollowTripStamp.isVisible = false
            binding.tvTripTripFollowTripName.isVisible = false
            binding.tvTripTripFollowAddress.isVisible = false
        }
    }
}