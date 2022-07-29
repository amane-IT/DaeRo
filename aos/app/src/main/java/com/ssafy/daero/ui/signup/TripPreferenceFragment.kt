package com.ssafy.daero.ui.signup

import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.signup.TripPreferenceResponseDto
import com.ssafy.daero.databinding.FragmentTripPreferenceBinding
import com.ssafy.daero.ui.adapter.signup.TripPreferenceAdapter

class TripPreferenceFragment : BaseFragment<FragmentTripPreferenceBinding>(R.layout.fragment_trip_preference){

    private lateinit var tripPreferenceAdapter: TripPreferenceAdapter
    private val tripPreferenceViewModel: TripPreferenceViewModel by viewModels()
    private val result = mutableListOf<Int>()

    override fun init() {
        initView()
        setOnClickListener()
        observeData()
    }

    private fun initView(){
        binding.buttonTripPreferenceStart.isEnabled = false

        tripPreferenceAdapter = TripPreferenceAdapter().apply {
            onItemClickListener = tripPreferenceItemClickListener
        }

        binding.recyclerTripPreferenceImageList.adapter = tripPreferenceAdapter
    }

    private fun setOnClickListener(){
        binding.apply {
            buttonTripPreferenceStart.setOnClickListener {
                if(result.size == 5){
                    var requestList = result.toList()
                    requestList = requestList.sorted()
                    Log.d("Trip", "setOnClickListener: ${requestList.toString()}")
                    tripPreferenceViewModel.postPreference(App.prefs.userSeq, requestList)
                    // TODO: 프래그먼트 이동
                }
            }
        }
    }

    private fun observeData(){
        tripPreferenceViewModel.showProgress.observe(viewLifecycleOwner){
            binding.progressBarSignupEmailLoading.isVisible = it
        }

//        tripPreferenceViewModel.responseState_getPreference.observe(viewLifecycleOwner){ state ->
//            when(state){
//                SUCCESS -> {
//                    tripPreferenceAdapter.dataList = tripPreferenceViewModel.preferenceList
//                }
//
//                FAIL -> {
//                    toast("이미지 로딩에 실패했습니다.")
//                }
//            }
//        }

        tripPreferenceViewModel.count.observe(viewLifecycleOwner){
            binding.textTripPreferenceCount.text = "$it / 5"
        }

        tripPreferenceAdapter.dataList = listOf(
            TripPreferenceResponseDto(1, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
            TripPreferenceResponseDto(2, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
            TripPreferenceResponseDto(3, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
            TripPreferenceResponseDto(4, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
            TripPreferenceResponseDto(5, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
            TripPreferenceResponseDto(6, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
            TripPreferenceResponseDto(7, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
            TripPreferenceResponseDto(8, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
            TripPreferenceResponseDto(9, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
            TripPreferenceResponseDto(10, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
        )

        tripPreferenceAdapter.notifyDataSetChanged()
    }

    private val tripPreferenceItemClickListener : (View, Int) -> Unit = { _, place_seq ->

        if(tripPreferenceViewModel.count.value!! < 5){
            if(tripPreferenceAdapter.dataList[place_seq - 1].isSelected){
                tripPreferenceAdapter.dataList[place_seq - 1].isSelected = false
                tripPreferenceViewModel.minusCount()
                result.remove(place_seq)
            } else {
                tripPreferenceAdapter.dataList[place_seq - 1].isSelected = true
                tripPreferenceViewModel.plusCount()
                result.add(place_seq)
                if(tripPreferenceViewModel.count.value!! == 5)
                    binding.buttonTripPreferenceStart.isEnabled = true
            }
        } else {
            if(tripPreferenceAdapter.dataList[place_seq - 1].isSelected) {
                tripPreferenceAdapter.dataList[place_seq - 1].isSelected = false
                tripPreferenceViewModel.minusCount()
                result.remove(place_seq)
                binding.buttonTripPreferenceStart.isEnabled = false
            }
        }

        tripPreferenceAdapter.notifyItemChanged(place_seq - 1)
    }

}