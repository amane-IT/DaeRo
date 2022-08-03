package com.ssafy.daero.ui.root.trip


import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.trip.TripPopularResponseDto
import com.ssafy.daero.databinding.FragmentTripVerificationBinding
import com.ssafy.daero.ui.adapter.TripUntilNowAdapter
import com.ssafy.daero.ui.root.sns.ArticleMenuBottomSheetFragment
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.tag.TagCollection
import com.ssafy.daero.utils.view.toast
import java.text.SimpleDateFormat
import java.util.*

class TripVerificationFragment : BaseFragment<FragmentTripVerificationBinding>(R.layout.fragment_trip_verification) {

    private val travelingViewModel: TravelingViewModel by viewModels()
    private lateinit var tripUntilNowAdapter: TripUntilNowAdapter
    private lateinit var dateTime: Date

    private val tripUntilNowClickListener: (View, Int) -> Unit = { _, articleSeq ->
        // TODO: 지금까지 여행지 상세 페이지로 이동
    }

    override fun init() {
        initData()
        setOnClickListeners()
    }

    private fun initData() {
        travelingViewModel.getTripStamps()
        binding.textTripVerificationUsername.text = "${App.prefs.nickname}님"

        //todo : Traveling에서 넘어올 때 번들로 trip_place_seq,place_name 넘겨받기

        var dateFormat = SimpleDateFormat("yyyy.MM.dd(E)")
        dateTime = Date(System.currentTimeMillis())
        val str:String = dateFormat.format(dateTime)
        binding.tvTripVerificationAddress.text = str

        val tripUntilNowList = mutableListOf<TripPopularResponseDto>()
        for(i in travelingViewModel.articleTripStampData){
            tripUntilNowList.add(TripPopularResponseDto(i.tripPlaceSeq,i.imageUrl,i.placeName))
        }
        tripUntilNowAdapter = TripUntilNowAdapter().apply {
            onItemClickListener = tripUntilNowClickListener
            tripPlaces = tripUntilNowList
        }
        binding.recyclerTripVerificationTripStampSoFar.apply {
            adapter = tripUntilNowAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun setOnClickListeners(){
        binding.buttonTripVerificationTripStamp.setOnClickListener {
            //todo : trip_place_seq,place_name 매개변수로 넘겨주기
            TripStampBottomSheetFragment(0,"",dateTime.time).show(childFragmentManager, TRIP_STAMP_BOTTOM_SHEET)
        }
        binding.buttonTripVerificationStop.setOnClickListener {
            //todo : 만약 이전에 했던 여행이 있으면(없어도 트립스탬프 만들었으면) 게시글 추가 페이지로, 없으면(+트립스탬프 안만들었으면) 홈화면으로
        }
    }
}