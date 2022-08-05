package com.ssafy.daero.ui.root.trip


import android.util.Log
import android.view.View
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.trip.TripPopularResponseDto
import com.ssafy.daero.databinding.FragmentTripNextBinding
import com.ssafy.daero.ui.adapter.TripNearByAdapter
import com.ssafy.daero.ui.adapter.TripUntilNowAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.popularTripPlaces
import com.ssafy.daero.utils.view.toast


class TripNextFragment : BaseFragment<FragmentTripNextBinding>(R.layout.fragment_trip_next) {

    private val tripNextViewModel: TripNextViewModel by viewModels()
    private lateinit var tripNearByAdapter: TripNearByAdapter
    private lateinit var tripUntilNowAdapter: TripUntilNowAdapter

    override fun init() {
        initAdapter()
        observeData()
        setOnClickListeners()
        initData()
    }

    private fun initAdapter(){
        tripNearByAdapter = TripNearByAdapter().apply {
            onItemClickListener = nearByTripPlaceClickListener
        }
        binding.recyclerTripNextNearBy.adapter = tripNearByAdapter

        tripUntilNowAdapter = TripUntilNowAdapter().apply {
            onItemClickListener = hotArticleClickListener
        }
        binding.recyclerTripNextNow.adapter = tripUntilNowAdapter
    }

    private val nearByTripPlaceClickListener: (View, Int) -> Unit = { _, tripPlaceSeq ->
        // TODO: 주변 여행지 정보 상세 페이지로 이동
    }

    private val hotArticleClickListener: (View, Int) -> Unit = { _, articleSeq ->
        // TODO: 지금까지 여행지 상세 페이지로 이동
    }

    private fun setOnClickListeners(){
        binding.apply {

            buttonTripNextNextTripRecommend.setOnClickListener {
                TripNextBottomSheetFragment(applyOptions).show(
                    childFragmentManager,
                    "TripNextBottomSheetFragment"
                )
            }

            // TODO: 여행 그만두기 기능
            buttonTripNextStop.setOnClickListener {

            }
        }
    }

    private fun initData() {
        tripNextViewModel.selectTripStampList()
        tripNextViewModel.getNearByPlaces()
    }


    private val applyOptions: (Int, String) -> Unit = { time, transportation ->
        tripNextViewModel.recommendNextPlace(time, transportation)
    }

    private fun observeData() {
        // TODO: 주변 여행지 정보 받아오기
        tripNextViewModel.nearByPlaces.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()){
                tripNearByAdapter.tripPlaces = it
                for(i in it){
                    Log.d("TAG", "observeData: ${i.toString()}")
                }
                tripNearByAdapter.notifyDataSetChanged()
            }
            else{
                Log.d("TAG", "observeData: 없음")
                tripNearByAdapter.tripPlaces = popularTripPlaces
            }
        }


        // TODO: 지금까지 여행지 상세 정보 받아오기
        tripNextViewModel.tripListState.observe(viewLifecycleOwner) {
            when(it){
                FAIL -> {
                    toast("여행 목록을 가져오는데 실패했습니다.")
                    tripNextViewModel.tripListState.value = DEFAULT
                }
            }
        }

        tripNextViewModel.tripList.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()){
                val list = mutableListOf<TripPopularResponseDto>()
                for(stamp in it){
                    val dto = TripPopularResponseDto(stamp.tripPlaceSeq, stamp.imageUrl, stamp.placeName)
                    list.add(dto)
                    Log.d("TripNextFragment", "observeData: ${dto.toString()}")
                }

                tripUntilNowAdapter.tripPlaces = list
                tripUntilNowAdapter.notifyDataSetChanged()
            }
        }
    }
}