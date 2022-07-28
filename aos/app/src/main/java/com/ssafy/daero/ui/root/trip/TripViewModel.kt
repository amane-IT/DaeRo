package com.ssafy.daero.ui.root.trip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TripViewModel : BaseViewModel() {
    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    fun getRecommendTripPlace() {
        // 2초
        _showProgress.postValue(true)
        Completable.complete().delay(2500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation()).subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _showProgress.postValue(false)
            }
    }
}