package com.ssafy.daero.ui.root.mypage

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.daero.databinding.BottomsheetMapFilterBinding
import com.ssafy.daero.utils.time.calendarToString
import com.ssafy.daero.utils.time.calendarToStringOnceYearAgo
import com.ssafy.daero.utils.time.getNowCalendar
import com.ssafy.daero.utils.time.stringToDate
import java.util.*

class MapFilterBottomSheetFragment(val applyFilter : (String, String) -> Unit) : BottomSheetDialogFragment() {
    private var _binding: BottomsheetMapFilterBinding? = null
    private val binding get() = _binding!!

    private val startDate = MutableLiveData<String>()
    private val endDate = MutableLiveData<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetMapFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFilterDate()
        expandFullHeight()
        observeData()
        setOnClickListeners()
    }

    private fun observeData() {
        startDate.observe(viewLifecycleOwner) {
            val date = it.split("-")
            binding.textMapFilterStartDate.text = "${date[0]}년 ${date[1]}월 ${date[2]}일"
        }
        endDate.observe(viewLifecycleOwner) {
            val date = it.split("-")
            binding.textMapFilterEndDate.text = "${date[0]}년 ${date[1]}월 ${date[2]}일"
        }
    }

    private fun initFilterDate() {
        val cal = getNowCalendar()
        startDate.value = calendarToStringOnceYearAgo(cal)
        endDate.value = calendarToString(cal)
    }

    private fun setOnClickListeners() {
        binding.textMapFilterStartDate.setOnClickListener { showStartDatePicker(startDate.value!!) }
        binding.textMapFilterEndDate.setOnClickListener { showEndDatePicker(endDate.value!!) }
        binding.buttonMapFilterApply.setOnClickListener {
            applyFilter(startDate.value ?: "", endDate.value ?: "")
            dismiss()
        }
        binding.imageTagClose.setOnClickListener {
            dismiss()
        }
    }

    private fun expandFullHeight() {
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun showStartDatePicker(date: String) {
        val dates = date.split("-")

        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                compareToEndDate("$year-${month+1}-$day")
            },
            dates[0].toInt(), dates[1].toInt() - 1, dates[2].toInt()
        ).show()
    }

    private fun showEndDatePicker(date: String) {
        val dates = date.split("-")

        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                compareToStartDate("$year-${month+1}-$day")
            },
            dates[0].toInt(), dates[1].toInt() - 1, dates[2].toInt()
        ).show()
    }

    private fun compareToStartDate(date : String) {
        // 종료일이 시작일보다 앞선다면 시작일을 변경
        if(stringToDate(date).before(stringToDate(startDate.value!!))) {
            startDate.value = date
        }
        endDate.value = date
    }

    private fun compareToEndDate(date : String) {
        // 시작일이 종료일보다 뒤라면 종료일을 변경
        if(stringToDate(date).after(stringToDate(endDate.value!!))) {
            endDate.value = date
        }
        startDate.value = date
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


