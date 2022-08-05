package com.ssafy.daero.ui.root.trip

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentTripStampBottomSheetBinding
import com.ssafy.daero.utils.permission.checkPermission
import com.ssafy.daero.utils.permission.requestPermission
import com.ssafy.daero.utils.view.toast


class TripStampBottomSheetFragment(private val onItemClickListener : (Boolean, Boolean) -> Unit) : BottomSheetDialogFragment() {
    private var _binding: FragmentTripStampBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripStampBottomSheetBinding.inflate(inflater, container, false)
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
        binding.apply {
            imageTripStampCamera.setOnClickListener {
                // TODO: 카메라 촬영 기능
                if(!checkPermission(Manifest.permission.CAMERA)) {
                    requestPermission(Manifest.permission.CAMERA, {
                        onItemClickListener(false, true)
                    }, {
                        toast("카메라 권한이 거부되었습니다.")
                    })
                } else {
                    onItemClickListener(false, true)
                }
                dismiss()
            }

            imageTripStampGallery.setOnClickListener {
                // TODO: 갤러리 사진 선택 기능
                onItemClickListener(true, false)
                dismiss()
            }
        }
    }
}