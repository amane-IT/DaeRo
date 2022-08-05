package com.ssafy.daero.ui.signup

import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.signup.TripPreferenceResponseDto
import com.ssafy.daero.databinding.FragmentTripPreferenceBinding
import com.ssafy.daero.ui.adapter.signup.TripPreferenceAdapter
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast

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

        tripPreferenceViewModel.getPreferences()
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
                    findNavController().navigate(R.id.action_tripPreference_to_rootFragment)
                }
            }
        }
    }

    private fun observeData(){
        tripPreferenceViewModel.showProgress.observe(viewLifecycleOwner){
            binding.progressBarTripPreferenceLoading.isVisible = it
        }

        tripPreferenceViewModel.responseState_getPreference.observe(viewLifecycleOwner){ state ->
            when(state){
                SUCCESS -> {
                    tripPreferenceAdapter.dataList = tripPreferenceViewModel.preferenceList
                }

                FAIL -> {
                    toast("이미지 로딩에 실패했습니다.")
                }
            }
        }

        tripPreferenceViewModel.count.observe(viewLifecycleOwner){
            binding.textTripPreferenceCount.text = "$it / 5"
        }

//        tripPreferenceAdapter.dataList = listOf(
//            TripPreferenceResponseDto(1, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
//            TripPreferenceResponseDto(2, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
//            TripPreferenceResponseDto(3, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
//            TripPreferenceResponseDto(4, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
//            TripPreferenceResponseDto(5, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
//            TripPreferenceResponseDto(6, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
//            TripPreferenceResponseDto(7, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
//            TripPreferenceResponseDto(8, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
//            TripPreferenceResponseDto(9, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
//            TripPreferenceResponseDto(10, "dummy", "https://unsplash.com/photos/jVT8vo04UT0/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjN8fHRyaXB8ZW58MHx8fHwxNjU4ODkyNzgy&force=true&w=1920"),
//        )

        tripPreferenceAdapter.notifyDataSetChanged()
    }

    private val tripPreferenceItemClickListener : (View, Int) -> Unit = { _, idx ->

        if(tripPreferenceViewModel.count.value!! < 5){
            Log.d("TAG", ": $idx")
            Log.d("TAG", ": ${tripPreferenceAdapter.dataList[idx].place_seq}")
            if(tripPreferenceAdapter.dataList[idx].isSelected){
                tripPreferenceAdapter.dataList[idx].isSelected = false
                tripPreferenceViewModel.minusCount()
                result.remove(tripPreferenceAdapter.dataList[idx].place_seq)
            } else {
                tripPreferenceAdapter.dataList[idx].isSelected = true
                tripPreferenceViewModel.plusCount()
                result.add(tripPreferenceAdapter.dataList[idx].place_seq)
                if(tripPreferenceViewModel.count.value!! == 5)
                    binding.buttonTripPreferenceStart.isEnabled = true
            }
        } else {
            if(tripPreferenceAdapter.dataList[idx].isSelected) {
                tripPreferenceAdapter.dataList[idx].isSelected = false
                tripPreferenceViewModel.minusCount()
                result.remove(tripPreferenceAdapter.dataList[idx].place_seq)
                binding.buttonTripPreferenceStart.isEnabled = false
            }
        }

        tripPreferenceAdapter.notifyItemChanged(idx)
    }
}