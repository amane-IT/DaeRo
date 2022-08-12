package com.ssafy.daero.ui.root.sns

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.article.TripStamp
import com.ssafy.daero.databinding.FragmentArticleBinding
import com.ssafy.daero.ui.adapter.sns.ArticleAdapter
import com.ssafy.daero.ui.adapter.sns.ExpenseAdapter
import com.ssafy.daero.ui.root.mypage.ReportListener
import com.ssafy.daero.ui.setting.BlockUserViewModel
import com.ssafy.daero.utils.constant.*
import com.ssafy.daero.utils.tag.categoryTags
import com.ssafy.daero.utils.view.getPxFromDp
import com.ssafy.daero.utils.view.toast


class ArticleFragment : BaseFragment<FragmentArticleBinding>(R.layout.fragment_article),
    ArticleListener,
    ReportListener,
    OnMapReadyCallback {

    private val blockUserViewModel: BlockUserViewModel by viewModels()
    private val articleViewModel: ArticleViewModel by viewModels()
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var expenseAdapter: ExpenseAdapter
    private var likeYn: Boolean? = null
    private var likes: Int = 0

    private var naverMap: NaverMap? = null
    private var uiSettings: UiSettings? = null
    private var marker = mutableListOf<Marker>()
    private var path: PathOverlay? = null

    private lateinit var mapView: ArticleMapView

    private var articleSeq = 0

    private val onItemClickListener: (View, Int) -> Unit = { _, id ->
        findNavController().navigate(
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
        articleSeq = arguments!!.getInt(ARTICLE_SEQ, 0)
    }

    private fun setOnClickListeners() {
        binding.imgArticleUser.setOnClickListener {
            findNavController().navigate(
                R.id.action_articleFragment_to_otherPageFragment,
                bundleOf(USER_SEQ to articleViewModel.articleData.user_seq)
            )
        }
        binding.tvArticleUser.setOnClickListener {
            findNavController().navigate(
                R.id.action_articleFragment_to_otherPageFragment,
                bundleOf(USER_SEQ to articleViewModel.articleData.user_seq)
            )
        }
        binding.imgArticleLike.setOnClickListener {
            // todo articleSeq
            if (likeYn == true) {
                articleViewModel.likeDelete(App.prefs.userSeq, articleSeq)
                if (binding.tvArticleLike.text.toString().toInt() > 0) {
                    likes -= 1
                    binding.tvArticleLike.text = likes.toString()
                }
            } else {
                articleViewModel.likeAdd(App.prefs.userSeq, articleSeq)
                likes += 1
                binding.tvArticleLike.text = likes.toString()
            }
            likeYn = !likeYn!!
            likeSetting()
        }
        binding.LinearArticleLike.setOnClickListener {
            //todo: 좋아요 리스트 페이지: 좋아요 누른 인원, article_seq 번들로 전달
            LikeBottomSheetFragment(
                articleSeq,
                articleViewModel.articleData.likes,
                userProfileClickListener
            ).show(childFragmentManager, LIKE_BOTTOM_SHEET)
        }
        binding.LinearArticleComment.setOnClickListener {
            //todo 댓글 리스트 페이지: 댓글수, article_seq 번들로 전달
            CommentBottomSheetFragment(articleSeq, articleViewModel.articleData.comments, userProfileClickListener).show(
                childFragmentManager,
                COMMENT_BOTTOM_SHEET
            )
        }
        binding.LinearArticleCommentImg.setOnClickListener {
            //todo 댓글, article_seq 번들로 전달
            CommentBottomSheetFragment(articleSeq, articleViewModel.articleData.comments, userProfileClickListener).show(
                childFragmentManager,
                COMMENT_BOTTOM_SHEET
            )
        }
        binding.imgArticleMenu.setOnClickListener {
            // todo: articleSeq 넘기기
            ArticleMenuBottomSheetFragment(
                articleSeq,
                articleViewModel.articleData.user_seq,
                2,
                this@ArticleFragment,
                this@ArticleFragment,
                articleViewModel.articleData.expose
            ).show(
                childFragmentManager,
                ARTICLE_MENU_BOTTOM_SHEET
            )
        }
        binding.imgArticleBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private val userProfileClickListener: (Int) -> Unit = { userSeq ->
        findNavController().navigate(R.id.action_articleFragment_to_otherPageFragment, bundleOf(
            USER_SEQ to userSeq))
    }

    private fun likeSetting() {
        if (likeYn == true) {
            binding.imgArticleLike.setImageResource(R.drawable.ic_like_full)
            var fadeScale: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.scale)
            binding.imgArticleLike.startAnimation(fadeScale)
        } else {
            binding.imgArticleLike.setImageDrawable(requireActivity().getDrawable(R.drawable.ic_like))
            binding.imgArticleLike.invalidate()
        }
    }

    private fun observeData() {
        articleViewModel.responseState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SUCCESS -> {
                    setBinding()
                    articleViewModel.responseState.value = DEFAULT
                }
                FAIL -> {

                    articleViewModel.responseState.value = DEFAULT
                }
            }
        }
        articleViewModel.likeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SUCCESS -> {
                    articleViewModel.likeState.value = DEFAULT
                }
                FAIL -> {

                    articleViewModel.likeState.value = DEFAULT
                }
            }
        }
    }

    private fun setBinding() {
        bindAdapter()
        bindTextContent()
        bindImage()
        bindTags()

        likes = articleViewModel.articleData.likes
        likeYn = articleViewModel.articleData.like_yn == 'y'
        likeSetting()

        deleteMarkers()
        deletePaths()

        var list = mutableListOf<TripStamp>()
        for (i in 0 until articleViewModel.articleData.records.size) {
            for (j in 0 until articleViewModel.articleData.records[i].trip_stamps.size) {
                list.add(articleViewModel.articleData.records[i].trip_stamps[j])
            }
        }
        drawMarkers(list)
        drawPolyline(list)
    }

    private fun bindAdapter() {
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
    }

    private fun bindTextContent() {
        binding.tvArticleTitle.text = articleViewModel.articleData.title
        if (articleViewModel.articleData.records.size > 1) {
            binding.tvArticleDate.text = articleViewModel.articleData.records[0].datetime + " ~ " +
                    articleViewModel.articleData.records[articleViewModel.articleData.records.size - 1].datetime
        } else {
            binding.tvArticleDate.text = articleViewModel.articleData.records[0].datetime
        }
        binding.tvArticleContent.text = articleViewModel.articleData.trip_comment
        binding.ratingArticleSatisfaction.rating = articleViewModel.articleData.rating.toFloat()
        binding.tvArticleUser.text = articleViewModel.articleData.nickname
        binding.tvArticleComment.text = articleViewModel.articleData.comments.toString()
        binding.tvArticleLike.text = articleViewModel.articleData.likes.toString()
    }

    private fun bindImage() {
        Glide.with(binding.imgArticleUser)
            .load(articleViewModel.articleData.profile_url)
            .placeholder(R.drawable.ic_back)
            .apply(RequestOptions().centerCrop())
            .error(R.drawable.ic_back)
            .into(binding.imgArticleUser)
    }

    private fun bindTags() {
        articleViewModel.articleData.tags.forEach { tag ->
            binding.chipArticleTag.addView(
                Chip(requireContext()).apply {
                    text = categoryTags[tag - 1].tag
                    id = tag
                    isCheckable = false
                    setTextAppearanceResource(R.style.ChipText)
                    setTextColor(resources.getColorStateList(R.color.selector_chip_color_text_white))
                    setChipBackgroundColorResource(R.color.primaryColor)
                }
            )
        }
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
        articleSeq = arguments!!.getInt(ARTICLE_SEQ)
        setNaverMapUI()
        articleViewModel.article(articleSeq)
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

    override fun articleDelete(articleSeq: Int) {
        articleViewModel.articleDelete(articleSeq)
        articleViewModel.deleteState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    toast("해당 게시글을 삭제했습니다.")
                    requireActivity().onBackPressed()
                    articleViewModel.deleteState.value = DEFAULT
                }
                FAIL -> {
                    toast("게시글 삭제를 실패했습니다.")
                    articleViewModel.deleteState.value = DEFAULT
                }
            }
        }
    }

    override fun blockArticle(articleSeq: Int,position: Int) {
        blockUserViewModel.blockArticle(articleSeq)
        blockUserViewModel.blockState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    toast("해당 여행기록을 차단했습니다.")
                    requireActivity().onBackPressed()
                    blockUserViewModel.blockState.value = DEFAULT
                }
                FAIL -> {
                    toast("여행기록 차단을 실패했습니다.")
                    blockUserViewModel.blockState.value = DEFAULT
                }
            }
        }
    }

    override fun setPublic() {
        blockUserViewModel
    }

    override fun articleOpen(articleSeq: Int) {
        articleViewModel.articleOpen(articleSeq)
        articleViewModel.exposeState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    toast("해당 게시글을 공개하였습니다.")
                    articleViewModel.article(articleSeq)
                    articleViewModel.exposeState.value = DEFAULT
                }
                FAIL -> {
                    toast("게시글 공개 처리를 실패했습니다.")
                    articleViewModel.exposeState.value = DEFAULT
                }
            }
        }
    }

    override fun articleClose(articleSeq: Int) {
        articleViewModel.articleClose(articleSeq)
        articleViewModel.exposeState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    toast("해당 게시글을 비공개하였습니다.")
                    articleViewModel.article(articleSeq)
                    articleViewModel.exposeState.value = DEFAULT
                }
                FAIL -> {
                    toast("게시글 공개 처리를 실패했습니다.")
                    articleViewModel.exposeState.value = DEFAULT
                }
            }
        }
    }

    override fun block(seq: Int, position: Int) {
        blockUserViewModel.blockArticle(seq)
        blockUserViewModel.blockState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    requireActivity().onBackPressed()
                    blockUserViewModel.blockState.value = DEFAULT
                }
                FAIL -> {
                    toast("여행기록 차단을 실패했습니다.")
                    blockUserViewModel.blockState.value = DEFAULT
                }
            }
        }
    }

}