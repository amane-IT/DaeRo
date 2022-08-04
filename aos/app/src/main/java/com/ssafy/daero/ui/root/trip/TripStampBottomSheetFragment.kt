package com.ssafy.daero.ui.root.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.daero.databinding.FragmentTripStampBottomSheetBinding
import com.ssafy.daero.utils.permission.checkPermission
import com.ssafy.daero.utils.permission.requestPermission
import com.ssafy.daero.utils.view.toast

class TripStampBottomSheetFragment(val setPhotos: (Boolean) -> Unit) : BottomSheetDialogFragment() {
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
                if(!checkPermission(android.Manifest.permission.CAMERA)){
                    requestPermission(android.Manifest.permission.CAMERA, {
                        toast("권한 허용이 확인되었습니다.")
                        setPhotos(true)
                        dismiss()
                    }, {
                        toast("권한을 허용하지 않으면 트립스탬프를 만들 수 없습니다.")
                    })
                } else {
                    setPhotos(true)
                }
            }

            imageTripStampGallery.setOnClickListener {
                setPhotos(false)
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}