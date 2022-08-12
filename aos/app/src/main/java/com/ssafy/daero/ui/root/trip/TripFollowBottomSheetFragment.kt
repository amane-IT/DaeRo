package com.ssafy.daero.ui.root.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.databinding.BottomsheetTripFollowBinding
import com.ssafy.daero.utils.constant.ARTICLE_SEQ
import com.ssafy.daero.utils.constant.TRIP_BEFORE
import com.ssafy.daero.utils.file.deleteCache

class TripFollowBottomSheetFragment(
    private val fragmentSeq: Int, private val articleSeq: Int
) : BottomSheetDialogFragment() {
    private var _binding: BottomsheetTripFollowBinding? = null
    private val binding get() = _binding!!

    private val tripFollowBottomSheetViewModel : TripFollowBottomSheetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetTripFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expandFullHeight()
        setOnClickListeners()
    }

    private fun expandFullHeight() {
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setOnClickListeners() {
        binding.buttonTripFollowFollow.setOnClickListener {

            deleteAllInformation()

            when (fragmentSeq) {
                1 -> findNavController().navigate(
                    R.id.action_rootFragment_to_tripFollowSelectFragment,
                    bundleOf(ARTICLE_SEQ to articleSeq)
                )
                2 -> findNavController().navigate(
                    R.id.action_articleFragment_to_tripFollowSelectFragment,
                    bundleOf(ARTICLE_SEQ to articleSeq)
                )
            }
            dismiss()
        }
        binding.buttonTripFollowBack.setOnClickListener { dismiss() }
    }

    private fun deleteAllInformation() {
        // 캐시 디렉토리 전체 삭제
        deleteCache(requireContext())

        // Room 에 저장되어있는 TripStamp, TripFollow 전체 삭제
        tripFollowBottomSheetViewModel.deleteAllTripRecord()

        // Prefs 초기화
        App.prefs.initTrip()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


