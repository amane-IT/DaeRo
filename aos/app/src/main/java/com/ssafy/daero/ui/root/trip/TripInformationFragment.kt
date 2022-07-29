package com.ssafy.daero.ui.root.trip

import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentTripInformationBinding
import com.ssafy.daero.utils.view.setStatusBarOrigin
import com.ssafy.daero.utils.view.setStatusBarTransparent

class TripInformationFragment : BaseFragment<FragmentTripInformationBinding>(R.layout.fragment_trip_information) {
    override fun init() {

    }

    private fun setStatusBarTransParent() {
        binding.constraintTripInformationInnerContainer.setStatusBarTransparent(requireActivity())
    }

    private fun setStatusBarOrigin() {
        requireActivity().setStatusBarOrigin()
    }

    override fun onStart() {
        super.onStart()
        setStatusBarTransParent()
    }

    override fun onStop() {
        super.onStop()
        setStatusBarOrigin()
    }
}