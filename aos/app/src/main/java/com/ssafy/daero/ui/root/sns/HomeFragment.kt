package com.ssafy.daero.ui.root.sns

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun init() {
        binding.textview.setOnClickListener {
            findNavController().navigate(R.id.action_rootFragment_to_articleFragment)
        }
    }
}