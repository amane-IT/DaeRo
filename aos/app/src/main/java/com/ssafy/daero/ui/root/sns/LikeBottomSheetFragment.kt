package com.ssafy.daero.ui.root.sns

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.daero.R
import com.ssafy.daero.databinding.BottomsheetLikeBinding
import com.ssafy.daero.ui.adapter.sns.LikeAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.USER_SEQ
import com.ssafy.daero.utils.view.setFullHeight
import com.ssafy.daero.utils.view.toast

class LikeBottomSheetFragment(
    private val articleSeq: Int,
    private val likes: Int,
    private val userProfileClickListener: (Int) -> Unit
) : BottomSheetDialogFragment() {
    private val likeViewModel: LikeViewModel by viewModels()
    private lateinit var likeAdapter: LikeAdapter

    private var _binding: BottomsheetLikeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetLikeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return setFullHeight()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initAdapter()
        observeData()
        setOnClickListeners()
        getLikeUsers()
    }

    private fun initView() {
        binding.textLikeCount.text = "$likes"
    }

    private fun initAdapter() {
        likeAdapter = LikeAdapter().apply {
            onItemClickListener = userProfileClickListener
        }
        binding.recyclerLike.adapter = likeAdapter
    }

    private fun getLikeUsers() {
        likeViewModel.getLikeUsers(articleSeq)
    }

    private fun observeData() {
        likeViewModel.likeUsers.observe(viewLifecycleOwner) {
            likeAdapter.submitData(lifecycle, it)
        }

        likeViewModel.likeUsersState.observe(viewLifecycleOwner) {
            when (it) {
                FAIL -> {
                    toast("좋아요 목록을 불러오는데 실패했습니다.")
                    likeViewModel.likeUsersState.value = DEFAULT
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageLikeClose.setOnClickListener { dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


