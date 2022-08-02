package com.ssafy.daero.ui.root.trip

import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.trip.WeatherResponseDto
import com.ssafy.daero.databinding.FragmentTravelingBinding
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.tag.TagCollection
import com.ssafy.daero.utils.view.toast
import java.text.SimpleDateFormat
import java.util.*

class TravelingFragment : BaseFragment<FragmentTravelingBinding>(R.layout.fragment_traveling) {

    private val tripInformationViewModel: TripInformationViewModel by viewModels()
    private var placeSeq = 0
    private var tagCollection: TagCollection? = null
    private var tripKind = 0
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0

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
        val cal = Calendar.getInstance()
        var baseDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
        val timeH = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time)
        val timeM = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time)
        var baseTime = CommonDate().getBaseTime(timeH, timeM)
        if (timeH == "00" && baseTime == "2330") {
            cal.add(Calendar.DATE, -1).toString()
            baseDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
        }
        tripInformationViewModel.getWeather("JSON", 60, 1, baseDate, baseTime, longitude, latitude)
        val item = tripInformationViewModel.weather.response.body.items.item
        val weather = WeatherResponseDto(item[0].fcstValue,item[0].fcstValue,item[0].fcstValue,item[0].fcstValue,"지금")
    }
}