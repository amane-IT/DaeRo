package com.ssafy.daero.ui.setting

import android.graphics.Paint
import android.view.View
import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentNotificationBinding
import com.ssafy.daero.ui.adapter.setting.NotificationAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast

class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(R.layout.fragment_notification) {
    private lateinit var notificationAdapter: NotificationAdapter
    private val notificationViewModel: NotificationViewModel by viewModels()

    override fun init() {
        initView()
        initAdapter()
        setOnClickListeners()
        observeData()
        getNotifications()
    }

    private fun observeData() {
        notificationViewModel.notificationState.observe(viewLifecycleOwner) {
            when (it) {
                FAIL -> {
                    toast("알림 목록을 가져오는데 실패했습니다.")
                    notificationViewModel.notificationState.value = DEFAULT
                }
            }
        }
        notificationViewModel.notifications.observe(viewLifecycleOwner) {
            if(it.isEmpty()) {
                binding.textNotificationNoContent.visibility = View.VISIBLE
            } else {
                binding.textNotificationNoContent.visibility = View.GONE
                notificationAdapter.notifications = it
                notificationAdapter.notifyDataSetChanged()
            }
        }
        notificationViewModel.deleteState.observe(viewLifecycleOwner) {
            when (it) {
                FAIL -> {
                    toast("알림 전체 삭제를 실패했습니다.")
                    notificationViewModel.deleteState.value = DEFAULT
                }
                SUCCESS -> {
                    binding.textNotificationNoContent.visibility = View.VISIBLE
                    notificationAdapter.apply {
                        notifications = listOf()
                        notifyDataSetChanged()
                    }
                    notificationViewModel.deleteState.value = DEFAULT
                }
            }
        }
    }

    private fun getNotifications() {
        notificationViewModel.getNotifications()
    }

    private fun initAdapter() {
        notificationAdapter = NotificationAdapter()
        binding.recyclerNotification.adapter = notificationAdapter
    }

    private fun initView() {
        binding.textNotificationDeleteAll.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    private fun setOnClickListeners() {
        binding.imgNotificationBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.textNotificationDeleteAll.setOnClickListener {
            notificationViewModel.deleteAllNotifications()
        }
    }
}