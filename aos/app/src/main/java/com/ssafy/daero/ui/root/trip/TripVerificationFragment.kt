package com.ssafy.daero.ui.root.trip


import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentTripVerificationBinding
import com.ssafy.daero.utils.constant.PLACE_SEQ
import com.ssafy.daero.utils.constant.TAG_COLLECTION
import com.ssafy.daero.utils.constant.TRIP_KIND
import com.ssafy.daero.utils.tag.TagCollection

class TripVerificationFragment : BaseFragment<FragmentTripVerificationBinding>(R.layout.fragment_trip_verification) {

    override fun init() {
        initData()
        observeData()
        setOnClickListeners()
    }

    private fun initData() {
        binding.textTripVerificationUsername.text = "${App.prefs.nickname}님"

        //todo : Traveling에서 넘어올 때 번들로 trip_place_seq,place_name 넘겨받기

        binding.tvTripVerificationAddress.text = 
    }
}