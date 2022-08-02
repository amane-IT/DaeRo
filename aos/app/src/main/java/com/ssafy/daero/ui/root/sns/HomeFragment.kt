package com.ssafy.daero.ui.root.sns

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentHomeBinding
import com.ssafy.daero.ui.adapter.sns.HomeAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private lateinit var homeAdapter: HomeAdapter

    override fun init() {
        initAdapter()
//        binding.textview.setOnClickListener {
//            findNavController().navigate(R.id.action_rootFragment_to_articleFragment)
//        }
    }

    private fun initAdapter() {
        homeAdapter = HomeAdapter()
        binding.recyclerHome.adapter = homeAdapter
    }
}