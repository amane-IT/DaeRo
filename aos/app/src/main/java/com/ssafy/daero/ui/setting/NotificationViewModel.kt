package com.ssafy.daero.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.entity.Notification
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class NotificationViewModel : BaseViewModel() {
    private val tripRepository = TripRepository.get()

    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>>
        get() = _notifications

    val notificationState = MutableLiveData<Int>()
    val deleteState = MutableLiveData<Int>()

    fun getNotifications() {
        addDisposable(
            tripRepository.getNotifications()
                .subscribe({ response ->
                    _notifications.postValue(response)
                }, { throwable ->
                    notificationState.postValue(FAIL)
                })
        )
    }

    fun deleteAllNotifications() {
        addDisposable(
            tripRepository.deleteAllNotifications()
                .subscribe({
                    deleteState.postValue(SUCCESS)
                }, { throwable ->
                    deleteState.postValue(FAIL)
                })
        )
    }
}