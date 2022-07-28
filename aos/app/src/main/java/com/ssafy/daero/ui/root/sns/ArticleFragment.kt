package com.ssafy.daero.ui.root.sns

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.article.Expense
import com.ssafy.daero.data.dto.article.Record
import com.ssafy.daero.data.dto.article.TripStamp
import com.ssafy.daero.databinding.FragmentArticleBinding
import com.ssafy.daero.ui.adapter.sns.ArticleAdapter
import com.ssafy.daero.ui.adapter.sns.ExpenseAdapter
import com.ssafy.daero.ui.root.mypage.MyPageFragment
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.getPxFromDp

class ArticleFragment : BaseFragment<FragmentArticleBinding>(R.layout.fragment_article),
    OnMapReadyCallback {

    private val articleViewModel : ArticleViewModel by viewModels()
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var expenseAdapter: ExpenseAdapter
    var recordList: MutableList<Record> = mutableListOf()
    var stampList: MutableList<TripStamp> = mutableListOf()
    var expenseList: MutableList<Expense> = mutableListOf()

    private var naverMap: NaverMap? = null
    private var uiSettings: UiSettings? = null
    private var markers = mutableListOf<MutableList<Marker>>()
    private var paths = mutableListOf<PathOverlay>()

    private val onItemClickListener: (View, Int) -> Unit = { _, id ->
        //todo 트립스탬프, trip_stamp_seq 번들로 전달
        requireParentFragment().findNavController().navigate(
            R.id.action_articleFragment_to_tripStampDetailFragment
        )
    }

    override fun init() {
        initData()
        setOnClickListeners()
        observeData()
    }

    private fun initData() {
        articleAdapter = ArticleAdapter().apply {
            this.onItemClickListener = this@ArticleFragment.onItemClickListener
            stampList.add(TripStamp("https://unsplash.com/photos/qyAka7W5uMY/download?ixid=MnwxMjA3fDB8MXxhbGx8fHx8fHx8fHwxNjU4OTA1NDIx&force=true&w=1920",
                1,1.0,1.0
            ))
            stampList.add(TripStamp("https://unsplash.com/photos/A5rCN8626Ck/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8Mnx8dHJpcHxlbnwwfHx8fDE2NTg4OTEyNjg&force=true&w=1920",
                2,2.0,2.0
            ))

            stampList.add(TripStamp("https://unsplash.com/photos/A5rCN8626Ck/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8Mnx8dHJpcHxlbnwwfHx8fDE2NTg4OTEyNjg&force=true&w=1920",
                3,3.0,3.0
            ))
            recordList.add(Record("2022.07.16", "나랑 바다 보러갈래?? 대답.", stampList))
            recordList.add(Record("2022.07.17", "나랑 산 보러갈래?? 대답.", stampList))
            this.articleData = recordList.toList()
        }
        binding.recyclerArticleTrip.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        expenseList.add(Expense("대게 먹방", "300000"))
        expenseList.add(Expense("카페베네", "28000"))
        expenseList.add(Expense("입장료", "3000"))
        expenseAdapter = ExpenseAdapter().apply {
            this.expense = expenseList.toList()
        }
        binding.recyclerArticleExpense.apply {
            adapter = expenseAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        //articleViewModel.article("1")
    }

    private fun setOnClickListeners() {
        binding.imgArticleUser.setOnClickListener {
            //todo 마이페이지로 이동, user_seq 번들로 전달
            requireParentFragment().findNavController().navigate(
                R.id.action_articleFragment_to_myPageFragment
            )
        }
        binding.tvArticleUser.setOnClickListener {
            //todo 마이페이지로 이동, user_seq 번들로 전달
            requireParentFragment().findNavController().navigate(
                R.id.action_articleFragment_to_myPageFragment
            )
        }
        binding.imgArticleLike.setOnClickListener {
            //todo 좋아요 상태 변경 API 연동(좋아요 상태에 따라 변경)
            //binding.imgArticleLike.setImageResource(R.drawable.ic_like)

            binding.imgArticleLike.setImageResource(R.drawable.ic_like_full)
            var fadeScale: Animation  = AnimationUtils.loadAnimation(requireContext(), R.anim.scale)
            binding.imgArticleLike.startAnimation(fadeScale)
        }
        binding.LinearArticleLike.setOnClickListener {
            //todo 좋아요 누른 인원, article_seq 번들로 전달
            requireParentFragment().findNavController().navigate(
                R.id.action_articleFragment_to_likeFragment
            )
        }
        binding.LinearArticleComment.setOnClickListener {
            //todo 댓글, article_seq 번들로 전달
            requireParentFragment().findNavController().navigate(
                R.id.action_articleFragment_to_commentFragment
            )
        }
        binding.LinearArticleCommentImg.setOnClickListener {
            //todo 댓글, article_seq 번들로 전달
            requireParentFragment().findNavController().navigate(
                R.id.action_articleFragment_to_commentFragment
            )
        }
        binding.imgArticleMenu.setOnClickListener {
            val articleMenuBottomSheetFragment = ArticleMenuBottomSheetFragment()
            articleMenuBottomSheetFragment.show(childFragmentManager,articleMenuBottomSheetFragment.tag)
        }
        binding.imgArticleBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun observeData() {
        articleViewModel.responseState.observe(viewLifecycleOwner) { state ->
            when(state) {
                SUCCESS -> {
                    setBinding()
                    articleViewModel.responseState.value = DEFAULT
                }
                FAIL -> {

                    articleViewModel.responseState.value = DEFAULT
                }
            }
        }
    }

    private fun setBinding() {
        articleAdapter = ArticleAdapter().apply {
            this.onItemClickListener = this@ArticleFragment.onItemClickListener
        }
        binding.recyclerArticleTrip.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        expenseAdapter = ExpenseAdapter()
        binding.recyclerArticleExpense.apply {
            adapter = expenseAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        binding.tvArticleTitle.text = articleViewModel.articleData.title
        if(articleViewModel.articleData.records.size>1){
            binding.tvArticleDate.text = articleViewModel.articleData.records[0].datetime + " ~ " +
                    articleViewModel.articleData.records[articleViewModel.articleData.records.size-1].datetime
        }else{
            binding.tvArticleDate.text = articleViewModel.articleData.records[0].datetime
        }
        binding.tvArticleContent.text = articleViewModel.articleData.trip_comment
        binding.ratingArticleSatisfaction.rating = articleViewModel.articleData.rating.toFloat()
        binding.tvArticleUser.text = articleViewModel.articleData.nickname
        Glide.with(binding.imgArticleUser)
            .load(articleViewModel.articleData.profile_url)
            .placeholder(R.drawable.ic_back)
            .apply(RequestOptions().centerCrop())
            .error(R.drawable.ic_back)
            .into(binding.imgArticleUser)
        binding.tvArticleComment.text = articleViewModel.articleData.comments.toString()
        binding.tvArticleLike.text = articleViewModel.articleData.likes.toString()
        deleteMarkers()
        deletePaths()
        var list = mutableListOf<Record>()
        for(i in 0..articleViewModel.articleData.records.size){
            list.add(articleViewModel.articleData.records[i])
        }
        drawMarkers(list)
        drawPolyline(it)
    }

    private fun deleteMarkers() {
        if (markers.isNotEmpty()) {
            markers.forEach { list ->
                list.forEach {
                    it.map = null
                }
            }
        }
        markers = mutableListOf()
    }

    private fun deletePaths() {
        if (paths.isNotEmpty()) {
            paths.forEach {
                it.map = null
            }
        }
        paths = mutableListOf()
    }

    private fun drawMarkers(journey: List<Record>) {
        if (journey.isNotEmpty()) {
            journey.forEachIndexed { idx, trips ->
                markers.add(mutableListOf())
                markers[idx] = trips.map { trip ->
                    createMarker(trip)
                }.toMutableList()
            }
            naverMap?.moveCamera(
                CameraUpdate.scrollTo(
                    LatLng(
                        journey.last().last().latitude,
                        journey.last().last().longitude
                    )
                )
            )
            naverMap?.moveCamera(CameraUpdate.zoomTo(10.0))
        }
    }

    private fun createMarker(trip: MyJourneyResponseDto): Marker {
        return Marker().apply {
            position = LatLng(trip.latitude, trip.longitude)    // 마커 좌표
            icon = OverlayImage.fromResource(R.drawable.ic_marker)
            iconTintColor = requireActivity().getColor(R.color.primaryDarkColor)// 마커 색깔
            width = requireContext().getPxFromDp(40f)   // 마커 가로 크기
            height = requireContext().getPxFromDp(40f)  // 마커 세로 크기
            zIndex = 0  // 마커 높이
            onClickListener = Overlay.OnClickListener {     // 마커 클릭 리스너
                // todo: trip_seq 이용해서 트립스탬프 상세화면으로 이동
                return@OnClickListener true
            }
            isHideCollidedMarkers = true    // 겹치면 다른 마커 숨기기
            map = naverMap  // 지도에 마커 표시
        }
    }

    private fun drawPolyline(journey: List<List<MyJourneyResponseDto>>) {
        if (journey.isNotEmpty()) {
            journey.forEachIndexed { idx, trips ->
                if (trips.size >= 2) {  // 한 여행에서 두 개 이상 여행지 방문했을 때만 경로 그리기
                    paths.add(PathOverlay().apply {
                        color = requireActivity().getColor(R.color.red) // 경로 색깔
                        outlineColor = requireActivity().getColor(R.color.red) // 경로 색깔
                        outlineWidth = requireContext().getPxFromDp(1.5f) // 경로 두께
                        coords =
                            trips.map { trip -> LatLng(trip.latitude, trip.longitude) }    // 경로 좌표
                        map = naverMap
                    })
                }
            }
        }
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible) {
            (requireParentFragment() as MyPageFragment).disableSlide()
        }
    }

    private fun initNaverMap() {
        val _naverMap =
            childFragmentManager.findFragmentById(R.id.fragmentContainer_myPageMap) as MapFragment?
                ?: MapFragment.newInstance().also {
                    childFragmentManager.beginTransaction()
                        .add(R.id.fragmentContainer_myPageMap, it)
                        .commit()
                }
        _naverMap.getMapAsync(this)
    }

    override fun onMapReady(_naverMap: NaverMap) {
        naverMap = _naverMap

        setNaverMapUI()
        getMyJourney("", "")
    }

    private fun setNaverMapUI() {
        naverMap?.apply {
            isLiteModeEnabled = true // 가벼운 지도 모드 (건물 내부 상세 표시 X)

            this@ArticleFrgment.uiSettings = this.uiSettings.apply {
                isCompassEnabled = false // 나침반 비활성화
                isZoomControlEnabled = false // 확대 축소 버튼 비활성화
                isScaleBarEnabled = false // 스케일 바 비활성화
                isLocationButtonEnabled = false // 기본 내 위치 버튼 비활성화
            }
        }
    }
}