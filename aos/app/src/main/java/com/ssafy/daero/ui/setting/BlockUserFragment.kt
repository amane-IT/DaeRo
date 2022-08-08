package com.ssafy.daero.ui.setting

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.R
import com.ssafy.daero.application.App.Companion.userSeq
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentBlockUserBinding
import com.ssafy.daero.ui.adapter.TripUntilNowAdapter
import com.ssafy.daero.ui.adapter.setting.UserBlockAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast

class BlockUserFragment : BaseFragment<FragmentBlockUserBinding>(R.layout.fragment_block_user), BlockUserListener {

    private val blockUserViewModel: BlockUserViewModel by viewModels()
    private lateinit var blockAdapter: UserBlockAdapter

    private val userBlockClickListener: (View, Int) -> Unit = { _, articleSeq ->
        findNavController().navigate(
            R.id.action_blockUserFragment_to_otherPageFragment,
            bundleOf("UserSeq" to userSeq)
        )
    }

    override fun init() {
        initData()
        observeData()
        setOnClickListeners()
    }

    private fun initData(){
        //todo : bundle로 넘겨받은 유저시퀀스 처리
        blockUserViewModel.getBlockUser()
    }

    private fun observeData(){
        blockUserViewModel.responseState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    setBinding()
                    blockUserViewModel.responseState.value = DEFAULT
                }
                FAIL -> {
                    toast("유저 차단 목록을 불러오는데 실패했습니다.")
                    blockUserViewModel.responseState.value = DEFAULT
                }
            }
        }
    }

    private fun setBinding(){
        blockAdapter = UserBlockAdapter(this@BlockUserFragment).apply {
            onItemClickListener = userBlockClickListener
            userBlockResponseDto = blockUserViewModel.userBlockData
        }
        binding.recyclerUserBlock.apply {
            adapter = blockAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun setOnClickListeners(){
        binding.imgUserBlockBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun blockDelete(sequence: Int) {
        blockUserViewModel.blockDelete(sequence)
        blockUserViewModel.responseState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    toast("차단 해제했습니다.")
                    blockUserViewModel.responseState.value = DEFAULT
                }
                FAIL -> {
                    toast("차단 해제를 실패했습니다.")
                    blockUserViewModel.responseState.value = DEFAULT
                }
            }
        }
    }

    override fun blockAdd(sequence: Int) {
        blockUserViewModel.blockAdd(sequence)
        blockUserViewModel.responseState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    toast("차단 해제했습니다.")
                    blockUserViewModel.responseState.value = DEFAULT
                }
                FAIL -> {
                    toast("차단 해제를 실패했습니다.")
                    blockUserViewModel.responseState.value = DEFAULT
                }
            }
        }
    }
}