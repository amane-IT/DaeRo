package com.ssafy.daero.ui.root.sns

import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.daero.R
import com.ssafy.daero.application.MainActivity
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

class ArticleFragment : BaseFragment<FragmentArticleBinding>(R.layout.fragment_article) {

    private val articleViewModel : ArticleViewModel by viewModels()
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var expenseAdapter: ExpenseAdapter
    var recordList: MutableList<Record> = mutableListOf()
    var stampList: MutableList<TripStamp> = mutableListOf()
    var expenseList: MutableList<Expense> = mutableListOf()

    private val onItemClickListener: (View, Int) -> Unit = { _, id ->
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
            //todo 마이페이지로 이동
        }
        binding.tvArticleUser.setOnClickListener {
            //todo 마이페이지로 이동
        }
        binding.imgArticleLike.setOnClickListener {
            //todo 좋아요 상태 변경 API 연동(좋아요 상태에 따라 변경)
//            binding.imgArticleLike.setImageResource(R.drawable.ic_like)

            binding.imgArticleLike.setImageResource(R.drawable.ic_like_full)
            var fadeScale: Animation  = AnimationUtils.loadAnimation(requireContext(), R.anim.scale)
            binding.imgArticleLike.startAnimation(fadeScale)
        }
        binding.LinearArticleLike.setOnClickListener {
            //todo 좋아요 누른 인원 바텀싯
        }
        binding.LinearArticleComment.setOnClickListener {
            //todo 댓글 바텀싯
        }
        binding.LinearArticleCommentImg.setOnClickListener {
            //todo 댓글 바텀싯
        }
        binding.imgArticleMenu.setOnClickListener {
            val articleMenuBottomSheetFragment = ArticleMenuBottomSheetFragment()
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
    }
}