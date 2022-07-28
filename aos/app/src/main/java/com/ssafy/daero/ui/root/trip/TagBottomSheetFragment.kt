package com.ssafy.daero.ui.root.trip

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.ssafy.daero.R
import com.ssafy.daero.databinding.BottomsheetTagBinding
import com.ssafy.daero.utils.tag.categoryTags
import com.ssafy.daero.utils.tag.regionTags

class TagBottomSheetFragment(
    private val _categoryTags: List<Int>,
    private val _regionTags: List<Int>,
    private val applyFilter: (List<Int>, List<Int>) -> Unit
) : BottomSheetDialogFragment() {
    private var _binding: BottomsheetTagBinding? = null
    private val binding get() = _binding!!

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
        binding.textTagCategoryInit.paintFlags = Paint.UNDERLINE_TEXT_FLAG;
        binding.textTagRegionInit.paintFlags = Paint.UNDERLINE_TEXT_FLAG;
        addCategoryChip()
        addRegionChip()
    }

    private fun addCategoryChip() {
        categoryTags.forEach { tag ->
            binding.chipGroupTagCategory.addView(
                Chip(requireContext()).apply {
                    text = tag.tag
                    id = tag.seq
                    isCheckable = true
                    setTextAppearanceResource(R.style.ChipText)
                    setTextColor(resources.getColorStateList(R.color.selector_chip_color_text))
                    setChipBackgroundColorResource(R.color.selector_chip_color)
                }
            )
        }
        if (_categoryTags.isNotEmpty()) {
            _categoryTags.forEach {
                (binding.chipGroupTagCategory.getChildAt(it - 1) as Chip).isChecked = true
            }
        }
    }

    private fun addRegionChip() {
        regionTags.forEach { tag ->
            binding.chipGroupTagRegion.addView(
                Chip(requireContext()).apply {
                    text = tag.tag
                    id = tag.seq
                    isCheckable = true
                    setTextAppearanceResource(R.style.ChipText)
                    setTextColor(resources.getColorStateList(R.color.selector_chip_color_text))
                    setChipBackgroundColorResource(R.color.selector_chip_color)
                }
            )
        }
        if (_regionTags.isNotEmpty()) {
            _regionTags.forEach {
                (binding.chipGroupTagRegion.getChildAt(it - 1) as Chip).isChecked = true
            }
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
            val categoryTags = binding.chipGroupTagCategory.checkedChipIds
            val regionTags = binding.chipGroupTagRegion.checkedChipIds
            applyFilter(categoryTags, regionTags)
            dismiss()
        }
        binding.imageTagClose.setOnClickListener {
            dismiss()
        }
        binding.textTagCategoryInit.setOnClickListener {
            binding.chipGroupTagCategory.clearCheck()
        }
        binding.textTagRegionInit.setOnClickListener {
            binding.chipGroupTagRegion.clearCheck()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


