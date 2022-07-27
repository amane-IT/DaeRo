package com.ssafy.daero.ui.root.mypage

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.daero.databinding.BottomsheetMapFilterBinding
import java.util.*

class MapFilterBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding : BottomsheetMapFilterBinding? = null
    private val binding get() = _binding!!

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

        expandFullHeight()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.textMapFilterStartDate.setOnClickListener { showDatePicker() }
        binding.textMapFilterEndDate.setOnClickListener { showDatePicker() }
    }

    private fun expandFullHeight(){
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun showDatePicker() {
        DatePickerDialog(requireContext()).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}