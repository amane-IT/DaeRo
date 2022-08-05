package com.ssafy.daero.ui.setting

import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentNoticeBinding
import com.ssafy.daero.ui.adapter.setting.NoticeAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.view.toast

class NoticeFragment : BaseFragment<FragmentNoticeBinding>(R.layout.fragment_notice) {
    private val noticeViewModel: NoticeViewModel by viewModels()
    private lateinit var noticeAdapter: NoticeAdapter

    override fun init() {
        initAdapter()
        setOnClickListeners()
        observeData()
        getNotices()
    }

    private fun observeData() {
        noticeViewModel.noticeState.observe(viewLifecycleOwner) {
            when (it) {
                FAIL -> {
                    toast("공지사항 목록을 조회하는데 실패했습니다.")
                    noticeViewModel.noticeState.value = DEFAULT
                }
            }
        }
        noticeViewModel.notices.observe(viewLifecycleOwner) {
            noticeAdapter.apply {
                notices = it
                notifyDataSetChanged()
            }
        }
    }

    private fun getNotices() {
        noticeViewModel.getNotices()
    }

    private fun initAdapter() {
        noticeAdapter = NoticeAdapter()
        binding.recyclerNotice.adapter = noticeAdapter
    }

    private fun setOnClickListeners() {
        binding.imgNoticeBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}