package com.ssafy.daero.ui.root.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.ssafy.daero.R
import com.ssafy.daero.databinding.BottomsheetTagBinding
import com.ssafy.daero.utils.tag.keywordTags

class TagBottomSheetFragment() : BottomSheetDialogFragment() {
    private var _binding: BottomsheetTagBinding? = null
    private val binding get() = _binding!!

    private val startDate = MutableLiveData<String>()
    private val endDate = MutableLiveData<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetTagBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        expandFullHeight()
        setOnClickListeners()
    }

    private fun initViews() {
        addKeywordChip()
    }

    private fun addKeywordChip() {
        keywordTags.forEach { tag ->
            binding.chipGroupTagKeyword.addView(
                Chip(requireContext()).apply {
                    text = tag.tag
                    id = tag.seq
                    isCheckable = true
                    setTextAppearanceResource(R.style.BodyWhite)
                    setChipBackgroundColorResource(R.drawable.selector_chip_color)
                }
            )
        }
    }

    private fun expandFullHeight() {
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setOnClickListeners() {
        binding.buttonTagApply.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


