package com.ssafy.daero.ui.root.mypage

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build.VERSION_CODES.O
import android.util.Log
import androidx.fragment.app.viewModels
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.badge.BadgeItem
import com.ssafy.daero.databinding.FragmentStampBinding
import com.ssafy.daero.databinding.FragmentStampRegionDetailBinding
import com.ssafy.daero.ui.adapter.mypage.StampDetailAdapter
import com.ssafy.daero.utils.constant.USER_SEQ
import java.util.*

class StampRegionDetailFragment : BaseFragment<FragmentStampRegionDetailBinding>(R.layout.fragment_stamp_region_detail) {

    private val stampViewModel: StampViewModel by viewModels()
    private lateinit var stampDetailAdapter: StampDetailAdapter

    private val O = "ONE"
    private val F = "FIVE"
    private val T = "TEN"

    private var stampList = mutableListOf<BadgeItem>()
    private var userSeq = 0

    override fun init() {
        initData()
        initAdapter()
        setOnClickListeners()
        observeData()
        getBadges()
    }

    private fun initData() {
        userSeq = arguments?.getInt(USER_SEQ, 0) ?: 0
    }

    private fun getBadges() {
        stampViewModel.getBadges(userSeq)
    }

    private fun initAdapter(){
        stampDetailAdapter = StampDetailAdapter()
        binding.recyclerStampReion.adapter = stampDetailAdapter
    }

    private fun setOnClickListeners() {
        binding.imgStampRegionBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun observeData(){
        stampViewModel.stampCount.observe(viewLifecycleOwner) {
            stampList.clear()

            getRegions("서울", it.seoul, 1)
            getRegions("경기\n& 인천", it.incheon, 2)
            getRegions("부울경", it.busan, 3)
            getRegions("대구\n& 경북", it.daegu, 4)
            getRegions("광주\n& 전남", it.gwangju, 5)
            getRegions("전북", it.jeonbuk, 6)
            getRegions("대전\n& 충남", it.daejeon, 7)
            getRegions("충북", it.chungbuk, 8)
            getRegions("강원도", it.gangwon, 9)
            getRegions("제주도", it.jeju, 10)

            stampDetailAdapter.apply {
                badgeList = stampList
                Log.d("배지리스트", "observeData: $badgeList")
                notifyDataSetChanged()
            }
        }
    }

    private fun getRegions(placeName: String, count: Int, id: Int) {
        when(count) {
            in 10..100 -> {
                stampList.add(BadgeItem('y', placeName, O, R.drawable.stamp_one))
                stampList.add(BadgeItem('y', placeName, F, R.drawable.stamp_five))
                stampList.add(BadgeItem('y', placeName, T, R.drawable.stamp_ten))
            }
            in 5..100 -> {
                stampList.add(BadgeItem('y', placeName, O, R.drawable.stamp_one))
                stampList.add(BadgeItem('y', placeName, F, R.drawable.stamp_five))
                stampList.add(BadgeItem('n', placeName, T, R.drawable.stamp_undo))
            }
            in 1..100 ->{
                stampList.add(BadgeItem('y', placeName, O, R.drawable.stamp_one))
                stampList.add(BadgeItem('n', placeName, F, R.drawable.stamp_undo))
                stampList.add(BadgeItem('n', placeName, T, R.drawable.stamp_undo))
            }
            0 -> {
                stampList.add(BadgeItem('n', placeName, O, R.drawable.stamp_undo))
                stampList.add(BadgeItem('n', placeName, F, R.drawable.stamp_undo))
                stampList.add(BadgeItem('n', placeName, T, R.drawable.stamp_undo))
            }
        }
    }

}