package com.ssafy.daero.ui.root.trip

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentTravelingBinding
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.tag.TagCollection
import com.ssafy.daero.utils.view.toast
import java.util.*
import kotlin.math.sqrt

class TravelingFragment : BaseFragment<FragmentTravelingBinding>(R.layout.fragment_traveling), SensorEventListener {

    private val tripInformationViewModel: TripInformationViewModel by viewModels()
    private var placeSeq = 0
    private var tagCollection: TagCollection? = null
    private var tripKind = 0
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor
    private val SHAKE_THRESHOLD_GRAVITY = 2.7f
    private val SHAKE_SKIP_TIME = 500
    private var mShakeTime: Long = 0
    private var shakeCount = 5
    private var mShakeCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }

    override fun init() {
        initData()
        observeData()
        setOnClickListeners()
    }

    private fun initData(){
        binding.textTravelingUsername.text = App.userName

        placeSeq = arguments!!.getInt(PLACE_SEQ, 0)
        tagCollection = arguments!!.getParcelable<TagCollection>(TAG_COLLECTION)
        tripKind = arguments!!.getInt(TRIP_KIND, 0)

        if(placeSeq > 0) {
            tripInformationViewModel.getTripInformation(placeSeq)
        } else {
            toast("여행지 정보를 불러오는데 실패했습니다.")
        }
    }

    private fun observeData() {
        tripInformationViewModel.tripInformationState.observe(viewLifecycleOwner) {
            when(it) {
                FAIL -> {
                    toast("여행지 정보를 불러오는데 실패했습니다.")
                    tripInformationViewModel.tripInformationState.value = DEFAULT
                }
            }
        }
        tripInformationViewModel.tripInformation.observe(viewLifecycleOwner) {
            Glide.with(binding.imgTravelingTripStamp)
                .load(it.image_url)
                .placeholder(R.drawable.img_my_page_album)
                .apply(RequestOptions().centerCrop())
                .error(R.drawable.img_my_page_album)
                .into(binding.imgTravelingTripStamp)
            placeSeq = it.place_seq
            binding.tvTravelingTripName.text = it.place_name
            binding.tvTravelingAddress.text = it.address
            latitude = it.latitude
            longitude = it.longitude
            setBinding()
        }
    }

    private fun setBinding(){

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event!!.sensor.type == Sensor.TYPE_ACCELEROMETER){
            var axisX = event.values[0]
            var axisY = event.values[1]
            var axisZ = event.values[2]

            var gravityX = axisX/SensorManager.GRAVITY_EARTH
            var gravityY = axisY/SensorManager.GRAVITY_EARTH
            var gravityZ = axisZ/SensorManager.GRAVITY_EARTH

            var f = gravityX * gravityX + gravityY * gravityY + gravityZ * gravityZ
            var squared = sqrt(f.toDouble())
            var gForce: Float = squared.toFloat()
            if(gForce > SHAKE_THRESHOLD_GRAVITY){
                var currentTime = System.currentTimeMillis()
                if(mShakeTime + SHAKE_SKIP_TIME > currentTime){
                    return
                }
                mShakeTime = currentTime
                mShakeCount++
                shakeCount -= mShakeCount
                binding.tvTravelingVerificationCount.text = shakeCount.toString()
                if(shakeCount < 1){
                    //todo : 반경 10km 안이면 인증 완료 화면 전환, 아니면 다시 인증해주세요 다이얼로그 -> 현재위치 전송해줘야함
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }
}