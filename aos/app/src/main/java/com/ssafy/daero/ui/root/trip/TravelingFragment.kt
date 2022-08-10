package com.ssafy.daero.ui.root.trip

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.location.*
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.trip.FirstTripRecommendRequestDto
import com.ssafy.daero.databinding.FragmentTravelingBinding
import com.ssafy.daero.ui.adapter.TripUntilNowAdapter
import com.ssafy.daero.ui.root.RootFragment
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.file.deleteCache
import com.ssafy.daero.utils.permission.checkPermission
import com.ssafy.daero.utils.permission.requestPermission
import com.ssafy.daero.utils.tag.TagCollection
import com.ssafy.daero.utils.view.toast
import kotlin.math.*

class TravelingFragment : BaseFragment<FragmentTravelingBinding>(R.layout.fragment_traveling),
    SensorEventListener {

    private val travelingViewModel: TravelingViewModel by viewModels()

    private lateinit var tripUntilNowAdapter: TripUntilNowAdapter
    private var placeSeq = 0
    private var address = ""
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor
    private val SHAKE_THRESHOLD_GRAVITY = 2.7f
    private val SHAKE_SKIP_TIME = 500
    private var mShakeTime: Long = 0
    private var shakeCount = 5
    private var mShakeCount = 1
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    lateinit var mLastLocation: Location
    lateinit var mLocationRequest: LocationRequest
    private val REQUEST_PERMISSION_LOCATION = 10
    private var categoryTags = listOf<Int>()
    private var regionTags = listOf<Int>()
    private lateinit var vibrator: Vibrator
    private val tripUntilNowClickListener: (View, Int) -> Unit = { _, tripStampId ->
        findNavController().navigate(
            R.id.action_rootFragment_to_tripStampFragment,
            bundleOf(TRIP_STAMP_ID to tripStampId, IS_TRIP_STAMP_UPDATE to true)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        vibrator = requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

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
        checkLocationPermission()
        initData()
        initView()
        initAdapter()
        observeData()
        setOnClickListeners()
        getTripInformation()
        getTripStamps()
    }

    private fun checkLocationPermission() {
        if (!checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION, {
                toast("권한 허용이 확인되었습니다.")
            }, {
                toast("권한을 허용하지 않으면 인증할 수 없습니다.")
            })
        }
    }

    private fun initData() {
        placeSeq = App.prefs.curPlaceSeq
    }

    private fun initView() {
        binding.textTravelingUsername.text = "${App.prefs.nickname}님"
        if (App.prefs.isFollow) {
            binding.buttonTravelingNext.visibility = View.GONE
        }
    }

    private fun initAdapter() {
        tripUntilNowAdapter = TripUntilNowAdapter().apply {
            onItemClickListener = tripUntilNowClickListener
        }
        binding.recyclerTravelingTripStampSoFar.adapter = tripUntilNowAdapter
    }

    private fun observeData() {
        travelingViewModel.tripInformationState.observe(viewLifecycleOwner) {
            when (it) {
                FAIL -> {
                    toast("여행지 정보를 불러오는데 실패했습니다.")
                    travelingViewModel.tripInformationState.value = DEFAULT
                }
            }
        }
        travelingViewModel.tripInformation.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it.image_url)
                .override(1200, 1200)
                .placeholder(R.drawable.placeholder_trip_album)
                .apply(RequestOptions().centerCrop())
                .error(R.drawable.placeholder_trip_album)
                .into(binding.imgTravelingTripStamp)
            placeSeq = it.place_seq
            binding.tvTravelingTripName.text = it.place_name
            binding.tvTravelingAddress.text = it.address
            latitude = it.latitude
            longitude = it.longitude
            address = it.address
        }
        travelingViewModel.tripStamps.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.tvTravelingTripStampSoFarNone.visibility = View.VISIBLE
                return@observe
            }
            tripUntilNowAdapter.tripStamps = it
            tripUntilNowAdapter.notifyDataSetChanged()
        }
        travelingViewModel.tripRecommendState.observe(viewLifecycleOwner) {
            when (it) {
                FAIL -> {
                    toast("해당 조건에 맞는 여행지가 없습니다.")
                    travelingViewModel.tripRecommendState.value = DEFAULT
                }
            }
        }
        travelingViewModel.placeSeq.observe(viewLifecycleOwner) {
            if (it > 0) {
                val bundle = Bundle().apply {
                    putInt(PLACE_SEQ, it)
                    putBoolean(IS_RE_RECOMMEND, true)
                    if (App.prefs.isFirstTrip) {
                        putParcelable(TAG_COLLECTION, TagCollection(categoryTags, regionTags))
                    }
                }
                findNavController().navigate(
                    R.id.action_rootFragment_to_tripInformationFragment,
                    bundle
                )
                travelingViewModel.placeSeq.value = 0
            }
        }
        travelingViewModel.imageUrl.observe(viewLifecycleOwner) {
            if (it.isNotBlank()) {
                Glide.with(requireContext()).load(it)
                travelingViewModel.imageUrl.value = ""
            }
        }
    }

    private fun getTripStamps() {
        travelingViewModel.getTripStamps()
    }

    private fun getTripInformation() {
        if (placeSeq > 0) {
            travelingViewModel.getTripInformation(placeSeq)
        } else {
            toast("여행지 정보를 불러오는데 실패했습니다.")
        }
    }

    private val applyFilter: (List<Int>, List<Int>) -> Unit = { categoryTags, regionTags ->
        this.categoryTags = categoryTags
        this.regionTags = regionTags
        travelingViewModel.getFirstTripRecommend(
            FirstTripRecommendRequestDto(
                categoryTags, regionTags
            )
        )
    }

    private val applyOptions: (Int, String) -> Unit = { time, transportation ->
        App.prefs.tripTime = time
        App.prefs.tripTransportation = transportation
        travelingViewModel.recommendNextPlace(time, transportation)
    }

    private fun setOnClickListeners() {
        binding.buttonTravelingDirections.setOnClickListener {
            // 네이버 지도 길찾기
            findDirectionByNaverMap()
        }
        binding.buttonTravelingNext.setOnClickListener {
            // 첫 여행지 추천상태라면 여행지 태그 바텀싯 띄우기
            if (App.prefs.isFirstTrip) {
                TagBottomSheetFragment(categoryTags, regionTags, applyFilter).show(
                    childFragmentManager,
                    "TagBottomSheetFragment"
                )
            }
            // 다음 여행지 추천상태라면 다음 여행지 추천 옵션 띄우기
            else {
                TripNextBottomSheetFragment(applyOptions).show(
                    childFragmentManager,
                    "TripNextBottomSheetFragment"
                )
            }
        }
        binding.buttonTravelingTemporary.setOnClickListener {
            // todo: 임시 인증 버튼, 추후에 삭제

            // 인증 완료한 시각 기록
            App.prefs.verificationTime = System.currentTimeMillis()
            (requireParentFragment() as RootFragment).changeTripState(TRIP_VERIFICATION)
        }
        binding.buttonTravelingStop.setOnClickListener {
            // 이전 여행기록이 없다면
            if (travelingViewModel.tripStamps.value?.isEmpty() != false) {
                // 캐시 디렉토리 전체 삭제
                deleteCache(requireContext())

                // Room 에 저장되어있는 TripStamp, TripFollow 전체 삭제
                travelingViewModel.deleteAllTripRecord()

                // Prefs 초기화
                App.prefs.initTrip()
                (requireParentFragment() as RootFragment).changeTripState(TRIP_BEFORE)
            } else {
                TripCompleteBottomSheetFragment(finishTrip, doneTrip)
                    .show(childFragmentManager, TRIP_COMPLETE_BOTTOM_SHEET)
            }
        }
        binding.imageTravelingNotification.setOnClickListener {
            findNavController().navigate(R.id.action_rootFragment_to_notificationFragment)
        }
    }

    private val finishTrip: () -> Unit = {
        // 게시글 작성 상태로 변경
        App.prefs.isPosting = true

        // 상태 잠시 변경
        App.isDone = true

        // 게시글 추가 화면으로 이동
        findNavController().navigate(R.id.action_rootFragment_to_articleWriteDayFragment)
    }

    private val doneTrip: () -> Unit = {
        // 캐시 디렉토리 전체 삭제
        deleteCache(requireContext())

        // Room 에 저장되어있는 TripStamp, TripFollow 전체 삭제
        travelingViewModel.deleteAllTripRecord()

        // Prefs 초기화
        App.prefs.initTrip()
        (requireParentFragment() as RootFragment).changeTripState(TRIP_BEFORE)
    }

    private fun findDirectionByNaverMap() {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("navermaps://?menu=location&pinType=place&lat=$latitude&lng=$longitude&title=$address")
            ).apply {
                `package` = "com.nhn.android.nmap"
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
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
                shakeCount -= mShakeCount
                binding.tvTravelingVerificationCount.text = shakeCount.toString()
                if (shakeCount < 1) {
                    shakeCount = 5
                    binding.tvTravelingVerificationCount.text = shakeCount.toString()
                    startLocationUpdates()
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //TODO("Not yet implemented")
    }

    private fun startLocationUpdates() {
        Log.d("확인","1")
        mFusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mLocationRequest = LocationRequest.create()
        mFusedLocationProviderClient!!.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    private var mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            Log.d("확인","2")
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    // 시스템으로 부터 받은 위치정보를 화면에 갱신해주는 메소드
    fun onLocationChanged(location: Location) {
        Log.d("확인","3")
        //todo : 반경 10km 안이면 인증 완료 화면 전환, 아니면 다시 인증해주세요 다이얼로그 -> 현재위치 전송해줘야함
        mLastLocation = location
        mLastLocation.latitude // 갱신 된 위도
        mLastLocation.longitude // 갱신 된 경도
        var distance = distanceInKilometerByHaversine(mLastLocation.latitude,mLastLocation.longitude,latitude,longitude)
        Log.d("거리",distance.toString())
        if(distance <= 10.0){
            App.prefs.verificationTime = System.currentTimeMillis()
            (requireParentFragment() as RootFragment).changeTripState(TRIP_VERIFICATION)
        }else{
            toast("거리가 부족합니다.\n여행지에 도착 후 다시 인증해주세요.")
            vibrator.vibrate(VibrationEffect.createOneShot(150, 100))
            mFusedLocationProviderClient!!.removeLocationUpdates(mLocationCallback)
        }
    }

    private fun distanceInKilometerByHaversine(x1: Double, y1: Double, x2: Double, y2: Double): Double {
        var distance: Double
        var radius = 6371.0 // 지구 반지름(km)
        var toRadian: Double = Math.PI / 180;

        var deltaLatitude: Double = abs(x1 - x2) * toRadian;
        var deltaLongitude: Double = abs(y1 - y2) * toRadian;

        var sinDeltaLat: Double = sin(deltaLatitude / 2);
        var sinDeltaLng: Double = sin(deltaLongitude / 2);
        var squareRoot: Double = sqrt(
                sinDeltaLat * sinDeltaLat +
                        cos(x1 * toRadian) * cos(x2 * toRadian) * sinDeltaLng * sinDeltaLng);

        distance = 2 * radius * asin(squareRoot);

        return distance;
    }
}