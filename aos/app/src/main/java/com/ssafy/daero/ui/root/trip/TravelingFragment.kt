package com.ssafy.daero.ui.root.trip

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location

import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.location.*
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.trip.TripPopularResponseDto
import com.ssafy.daero.databinding.FragmentTravelingBinding
import com.ssafy.daero.ui.adapter.TripUntilNowAdapter
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.permission.checkPermission
import com.ssafy.daero.utils.permission.requestPermission
import com.ssafy.daero.utils.tag.TagCollection
import com.ssafy.daero.utils.view.toast
import kotlin.math.sqrt

class TravelingFragment : BaseFragment<FragmentTravelingBinding>(R.layout.fragment_traveling),
    SensorEventListener {

    private val tripInformationViewModel: TripInformationViewModel by viewModels()
    private val travelingViewModel: TravelingViewModel by viewModels()
    private lateinit var tripUntilNowAdapter: TripUntilNowAdapter
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
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    lateinit var mLastLocation: Location
    internal lateinit var mLocationRequest: LocationRequest
    private val REQUEST_PERMISSION_LOCATION = 10

    private val tripUntilNowClickListener: (View, Int) -> Unit = { _, articleSeq ->

    }

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

    private fun initData() {
        if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, {
                toast("권한 허용이 확인되었습니다.")
            }, {
                toast("권한을 허용하지 않으면 인증할 수 없습니다.")
            })
        }
        binding.textTravelingUsername.text = "${App.prefs.nickname}님"

//        placeSeq = arguments!!.getInt(PLACE_SEQ, 0)
//        tagCollection = arguments!!.getParcelable<TagCollection>(TAG_COLLECTION)
//        tripKind = arguments!!.getInt(TRIP_KIND, 0)

        if (placeSeq > 0) {
            tripInformationViewModel.getTripInformation(placeSeq)
        } else {
            toast("여행지 정보를 불러오는데 실패했습니다.")
        }
        travelingViewModel.getTripStamps()
    }

    private fun observeData() {
        tripInformationViewModel.tripInformationState.observe(viewLifecycleOwner) {
            when (it) {
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
        }
        setBinding()
    }

    private fun setBinding() {
        val tripUntilNowList = mutableListOf<TripPopularResponseDto>()
        for (i in travelingViewModel.articleTripStampData) {
            tripUntilNowList.add(TripPopularResponseDto(i.tripPlaceSeq, i.imageUrl, i.placeName))
        }
        if (tripUntilNowList.size > 0) {
            binding.tvTravelingTripStampSoFarNone.visibility = View.GONE
            binding.recyclerTravelingTripStampSoFar.visibility = View.VISIBLE
            tripUntilNowAdapter = TripUntilNowAdapter().apply {
                onItemClickListener = tripUntilNowClickListener
                tripPlaces = tripUntilNowList
            }
            binding.recyclerTravelingTripStampSoFar.apply {
                adapter = tripUntilNowAdapter
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            }
        } else {
            binding.tvTravelingTripStampSoFarNone.visibility = View.VISIBLE
            binding.recyclerTravelingTripStampSoFar.visibility = View.INVISIBLE
        }
    }

    private fun setOnClickListeners() {
        binding.buttonTravelingNext.setOnClickListener {
            //todo : 다른 여행지 추천 화면으로 전환
        }
        binding.buttonTravelingStop.setOnClickListener {
            //todo : 만약 이전에 했던 여행이 있으면 게시글 추가 페이지로, 없으면 홈화면으로
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            var axisX = event.values[0]
            var axisY = event.values[1]
            var axisZ = event.values[2]

            var gravityX = axisX / SensorManager.GRAVITY_EARTH
            var gravityY = axisY / SensorManager.GRAVITY_EARTH
            var gravityZ = axisZ / SensorManager.GRAVITY_EARTH

            var f = gravityX * gravityX + gravityY * gravityY + gravityZ * gravityZ
            var squared = sqrt(f.toDouble())
            var gForce: Float = squared.toFloat()
            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                var currentTime = System.currentTimeMillis()
                if (mShakeTime + SHAKE_SKIP_TIME > currentTime) {
                    return
                }
                mShakeTime = currentTime
                mShakeCount++
                shakeCount -= mShakeCount
                binding.tvTravelingVerificationCount.text = shakeCount.toString()
                if (shakeCount < 1) {
                    startLocationUpdates()
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //TODO("Not yet implemented")
    }

    private fun startLocationUpdates() {
        mFusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocationProviderClient!!.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    // 시스템으로 부터 받은 위치정보를 화면에 갱신해주는 메소드
    fun onLocationChanged(location: Location) {
        //todo : 반경 10km 안이면 인증 완료 화면 전환, 아니면 다시 인증해주세요 다이얼로그 -> 현재위치 전송해줘야함
        mLastLocation = location
        mLastLocation.latitude // 갱신 된 위도
        mLastLocation.longitude // 갱신 된 경도

    }
}