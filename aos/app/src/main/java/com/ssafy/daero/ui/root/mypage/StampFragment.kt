package com.ssafy.daero.ui.root.mypage

import android.graphics.Paint
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.badge.BadgeItem
import com.ssafy.daero.databinding.FragmentStampBinding
import com.ssafy.daero.ui.adapter.mypage.StampAdapter
import com.ssafy.daero.ui.adapter.mypage.StampDetailAdapter
import com.ssafy.daero.utils.constant.USER_SEQ
import java.util.Collections.shuffle

class StampFragment : BaseFragment<FragmentStampBinding>(R.layout.fragment_stamp) {

    private val stampViewModel: StampViewModel by viewModels()
    private val O = "ONE"
    private val F = "FIVE"
    private val T = "TEN"

    private lateinit var stampAdapter: StampAdapter
    private lateinit var stampLevelAdapter: StampDetailAdapter

    private var stampList = mutableListOf<BadgeItem>()
    private var stampLevel = mutableListOf<BadgeItem>()
    private var userSeq = 0

    override fun init() {
        initData()
        initView()
        initAdapter()
        observeData()
        setOnClickListeners()
        getBadges()
    }

    private fun initData() {
        userSeq = arguments?.getInt(USER_SEQ, 0) ?: 0
    }

    private fun initView() {
        binding.textStampRegionMore.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    private fun initAdapter(){
        stampAdapter = StampAdapter()
        binding.recyclerStampRegion.adapter = stampAdapter

        stampLevelAdapter = StampDetailAdapter()
        binding.recyclerStampCount.adapter = stampLevelAdapter
    }

    private fun observeData(){
        stampViewModel.stampCount.observe(viewLifecycleOwner) {
            stampList.clear()
            stampLevel.clear()

            getRegions("서울", it.seoul)
            getRegions("경기\n& 인천", it.incheon)
            getRegions("부울경", it.busan)
            getRegions("대구\n& 경북", it.daegu)
            getRegions("광주\n& 전남", it.gwangju)
            getRegions("전북", it.jeonbuk)
            getRegions("대전\n& 충남", it.daejeon)
            getRegions("충북", it.chungbuk)
            getRegions("강원도", it.gangwon)
            getRegions("제주도", it.jeju)

            getLevel(
                it.total
            )
            shuffle(stampList)

            stampAdapter.apply {
                badgeList = stampList
                notifyDataSetChanged()
            }

            stampLevelAdapter.apply {
                badgeList = stampLevel
                notifyDataSetChanged()
            }
        }
    }

    private fun setOnClickListeners(){
        binding.apply {
            textStampRegionMore.setOnClickListener {
                findNavController().navigate(R.id.action_stampFragment_to_stampRegionDetailFragment, bundleOf(
                    USER_SEQ to userSeq))
            }
            imgStampBack.setOnClickListener { requireActivity().onBackPressed() }
        }
    }

    private fun getBadges() {
        stampViewModel.getBadges(userSeq)
    }

    private fun getRegions(placeName: String, count: Int) {
        when(count) {
            0 -> return
            in 10..100 -> {
                stampList.add(BadgeItem('y', placeName, O, R.drawable.stamp_one))
                stampList.add(BadgeItem('y', placeName, F, R.drawable.stamp_five))
                stampList.add(BadgeItem('y', placeName, T, R.drawable.stamp_ten))
            }
            in 5..100 -> {
                stampList.add(BadgeItem('y', placeName, O, R.drawable.stamp_one))
                stampList.add(BadgeItem('y', placeName, F, R.drawable.stamp_five))
            }
            in 1..100 -> stampList.add(BadgeItem('y', placeName, O, R.drawable.stamp_one))
        }
    }

    private fun getLevel(count: Int){
        when(count) {
            in 100 .. 200-> {
                stampLevel.add(BadgeItem('y', "FIVE", "LEVEL", R.drawable.stamp_normal))
                stampLevel.add(BadgeItem('y', "FOUR", "LEVEL", R.drawable.stamp_normal))
                stampLevel.add(BadgeItem('y', "THREE", "LEVEL", R.drawable.stamp_normal))
                stampLevel.add(BadgeItem('y', "TWO", "LEVEL", R.drawable.stamp_normal))
                stampLevel.add(BadgeItem('y', "ONE", "LEVEL", R.drawable.stamp_normal))
            }

            in 50 .. 100 -> {
                stampLevel.add(BadgeItem('y', "FOUR", "LEVEL", R.drawable.stamp_normal))
                stampLevel.add(BadgeItem('y', "THREE", "LEVEL", R.drawable.stamp_normal))
                stampLevel.add(BadgeItem('y', "TWO", "LEVEL", R.drawable.stamp_normal))
                stampLevel.add(BadgeItem('y', "ONE", "LEVEL", R.drawable.stamp_normal))
            }

            in 10 .. 100 -> {
                stampLevel.add(BadgeItem('y', "THREE", "LEVEL", R.drawable.stamp_normal))
                stampLevel.add(BadgeItem('y', "TWO", "LEVEL", R.drawable.stamp_normal))
                stampLevel.add(BadgeItem('y', "ONE", "LEVEL", R.drawable.stamp_normal))
            }

            in 5 .. 100 -> {
                stampLevel.add(BadgeItem('y', "TWO", "LEVEL", R.drawable.stamp_normal))
                stampLevel.add(BadgeItem('y', "ONE", "LEVEL", R.drawable.stamp_normal))
            }

            in 1 .. 100 ->
                stampLevel.add(BadgeItem('y', "ONE", "LEVEL", R.drawable.stamp_normal))

        }
    }
}