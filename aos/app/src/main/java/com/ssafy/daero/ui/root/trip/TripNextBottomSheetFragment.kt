package com.ssafy.daero.ui.root.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.databinding.FragmentTripNextBottomSheetBinding


class TripNextBottomSheetFragment(private val applyOptions: (Int, String) -> Unit) : BottomSheetDialogFragment() {
    private var _binding: FragmentTripNextBottomSheetBinding? = null
    private val binding get() = _binding!!

    private var time = 0
    private var transportation = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripNextBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        expandFullHeight()
        setOnClickListeners()
        setOnItemSelectedListener()
        setOnCheckedChangeListener()
    }

    private fun initView(){
        binding.apply {
            spinnerTripNextBottomTime.adapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner_time_list, android.R.layout.simple_spinner_dropdown_item)
        }
    }

    private fun expandFullHeight() {
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setOnClickListeners() {
        binding.apply {
            buttonTripNextBottomRecommend.setOnClickListener {
                applyOptions(time, transportation)
                dismiss()
            }
        }
    }

    private fun setOnItemSelectedListener(){
        binding.spinnerTripNextBottomTime.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) { }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        // TODO: 이동시간 리스트 재설정하기
                        0 -> time = 30
                        1 -> time = 60
                        2 -> time = 90
                        3 -> time = 120
                        4 -> time = 180
                        else -> time = 0
                    }
                }
            }
    }

    private fun setOnCheckedChangeListener() {
        binding.radioGroupTripNextBottom.setOnCheckedChangeListener { radioGroup, checkedId ->
            when(checkedId){
                // TODO: tranportation 타입에 맞춰서 변경하기
                R.id.radioButton_tripNextBottm_walk -> transportation = "walk"
                R.id.radioButton_tripNextBottm_byCar -> transportation = "car"
            }
        }

        binding.buttonTripNextBottomRecommend.setOnClickListener {
            // place_seq 가져오기 -> DB 가장 마지막 데이터의 place_seq를 가져오면 되나..?
            // prefs에 저장해서 가져오기로 결정
            // TODO: place_seq 가져오기
            val placeSeq = App.prefs.placeSeq



        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}