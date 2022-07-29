package com.ssafy.daero.ui.root.sns

import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.os.bundleOf
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
    private var marker = mutableListOf<Marker>()
    private var path: PathOverlay? = null

    private lateinit var mapView: ArticleMapView

    private val onItemClickListener: (View, Int) -> Unit = { _, id ->
        requireParentFragment().findNavController().navigate(
            R.id.action_articleFragment_to_tripStampDetailFragment, bundleOf("tripStampSeq" to id)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initNaverMap()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.fragment_article_map)
        mapView.onCreate(savedInstanceState)
        binding.scrollArticle.overScrollMode = View.OVER_SCROLL_NEVER
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }


    override fun init() {
        initData()
        setOnClickListeners()
        observeData()
    }

    private fun initData() {
//        articleAdapter = ArticleAdapter().apply {
//            this.onItemClickListener = this@ArticleFragment.onItemClickListener
//            stampList.add(TripStamp("https://unsplash.com/photos/qyAka7W5uMY/download?ixid=MnwxMjA3fDB8MXxhbGx8fHx8fHx8fHwxNjU4OTA1NDIx&force=true&w=1920",
//                1,1.0,1.0
//            ))
//            stampList.add(TripStamp("https://unsplash.com/photos/A5rCN8626Ck/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8Mnx8dHJpcHxlbnwwfHx8fDE2NTg4OTEyNjg&force=true&w=1920",
//                2,2.0,2.0
//            ))
//
//            stampList.add(TripStamp("https://unsplash.com/photos/A5rCN8626Ck/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8Mnx8dHJpcHxlbnwwfHx8fDE2NTg4OTEyNjg&force=true&w=1920",
//                3,3.0,3.0
//            ))
//            recordList.add(Record("2022.07.16", "나랑 바다 보러갈래?? 대답.", stampList))
//            recordList.add(Record("2022.07.17", "나랑 산 보러갈래?? 대답.", stampList))
//            this.articleData = recordList.toList()
//        }
//        binding.recyclerArticleTrip.apply {
//            adapter = articleAdapter
//            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
//        }
//
//        expenseList.add(Expense("대게 먹방", "300000"))
//        expenseList.add(Expense("카페베네", "28000"))
//        expenseList.add(Expense("입장료", "3000"))
//        expenseAdapter = ExpenseAdapter().apply {
//            this.expense = expenseList.toList()
//        }
//        binding.recyclerArticleExpense.apply {
//            adapter = expenseAdapter
//            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
//        }
        articleViewModel.article("1")
    }

    private fun setOnClickListeners() {
        binding.imgArticleUser.setOnClickListener {
            requireParentFragment().findNavController().navigate(
                R.id.action_articleFragment_to_myPageFragment,
                bundleOf("UserSeq" to articleViewModel.articleData.user_seq)
            )
        }
        binding.tvArticleUser.setOnClickListener {
            requireParentFragment().findNavController().navigate(
                R.id.action_articleFragment_to_myPageFragment,
                bundleOf("UserSeq" to articleViewModel.articleData.user_seq)
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
                R.id.action_articleFragment_to_likeFragment,
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
            this.articleData = articleViewModel.articleData.records
        }
        binding.recyclerArticleTrip.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        expenseAdapter = ExpenseAdapter().apply {
            this.expense = articleViewModel.articleData.trip_expenses
        }
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
        var list = mutableListOf<TripStamp>()
        for(i in 0..articleViewModel.articleData.records.size){
            for(j in 0..articleViewModel.articleData.records[i].trip_stamps.size){
                list.add(articleViewModel.articleData.records[i].trip_stamps[j])
            }
        }
        drawMarkers(list)
        drawPolyline(list)
    }

    private fun deleteMarkers() {
        if (marker.isNotEmpty()) {
            marker.forEach {
                it.map = null
            }
        }
        marker = mutableListOf()
    }

    private fun deletePaths() {
        path = null
    }

    private fun drawMarkers(journey: List<TripStamp>) {
        if (journey.isNotEmpty()) {
            journey.forEachIndexed { idx, trip ->
                marker.add(createMarker(trip))
            }
            naverMap?.moveCamera(
                CameraUpdate.scrollTo(
                    LatLng(
                        journey.last().latitude,
                        journey.last().longitude
                    )
                )
            )
            naverMap?.moveCamera(CameraUpdate.zoomTo(10.0))
        }
    }

    private fun createMarker(trip: TripStamp): Marker {
        return Marker().apply {
            position = LatLng(trip.latitude, trip.longitude)    // 마커 좌표
            icon = OverlayImage.fromResource(R.drawable.ic_marker)
            iconTintColor = requireActivity().getColor(R.color.primaryDarkColor)// 마커 색깔
            width = requireContext().getPxFromDp(40f)   // 마커 가로 크기
            height = requireContext().getPxFromDp(40f)  // 마커 세로 크기
            zIndex = 0  // 마커 높이
            onClickListener = Overlay.OnClickListener {     // 마커 클릭 리스너
                return@OnClickListener true
            }
            isHideCollidedMarkers = true    // 겹치면 다른 마커 숨기기
            map = naverMap  // 지도에 마커 표시
        }
    }

    private fun drawPolyline(journey: List<TripStamp>) {
        if (journey.isNotEmpty()) {
            if (journey.size >= 2) {  // 한 여행에서 두 개 이상 여행지 방문했을 때만 경로 그리기

                val coord = mutableListOf<LatLng>()
                journey.forEachIndexed { idx, trip ->
                    coord.add(LatLng(trip.latitude, trip.longitude))
                }
                path = PathOverlay().apply {
                    color = requireActivity().getColor(R.color.red) // 경로 색깔
                    outlineColor = requireActivity().getColor(R.color.red) // 경로 색깔
                    outlineWidth = requireContext().getPxFromDp(1.5f) // 경로 두께
                    coords = coord // 경로 좌표
                    map = naverMap
                }
            }
        }
    }

    private fun initNaverMap() {
        val _naverMap =
            childFragmentManager.findFragmentById(R.id.fragment_article_map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    childFragmentManager.beginTransaction()
                        .add(R.id.fragment_article_map, it)
                        .commit()
                }
        _naverMap.getMapAsync(this)
    }


    override fun onMapReady(_naverMap: NaverMap) {
        naverMap = _naverMap

        setNaverMapUI()
        //getMyJourney("", "")
    }

    private fun setNaverMapUI() {
        naverMap?.apply {
            isLiteModeEnabled = true // 가벼운 지도 모드 (건물 내부 상세 표시 X)

            this@ArticleFragment.uiSettings = this.uiSettings.apply {
                isCompassEnabled = false // 나침반 비활성화
                isZoomControlEnabled = false // 확대 축소 버튼 비활성화
                isScaleBarEnabled = false // 스케일 바 비활성화
                isLocationButtonEnabled = false // 기본 내 위치 버튼 비활성화
            }
        }
    }
}