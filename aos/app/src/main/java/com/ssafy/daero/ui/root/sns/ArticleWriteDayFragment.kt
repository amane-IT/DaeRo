package com.ssafy.daero.ui.root.sns

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentArticleWriteDayBinding
import com.ssafy.daero.ui.adapter.sns.ArticleWriteTripStampAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class ArticleWriteDayFragment :
    BaseFragment<FragmentArticleWriteDayBinding>(R.layout.fragment_article_write_day) {

    val articleWriteViewModel: ArticleWriteViewModel by viewModels({ requireParentFragment() })
    private lateinit var articleWriteTripStampAdapter: ArticleWriteTripStampAdapter

    override fun init() {
        initAdapter()
        initView()
        observeData()
        setOnClickListeners()
        initData()
    }

    private fun initAdapter() {
        articleWriteTripStampAdapter = ArticleWriteTripStampAdapter()
        binding.recyclerArticleWriteTripStamp.adapter = articleWriteTripStampAdapter

        if (articleWriteViewModel.dayIndex >= 0 && articleWriteViewModel.tripStamps.isNotEmpty()) {
            articleWriteTripStampAdapter.tripStamps =
                articleWriteViewModel.tripStamps[articleWriteViewModel.dayIndex]
            articleWriteTripStampAdapter.notifyDataSetChanged()
        }
    }

    private fun initView() {
        if (articleWriteViewModel.dayIndex >= 0 && articleWriteViewModel.tripStamps.isNotEmpty()) {
            binding.editTextCommentAddComment.setText(
                articleWriteViewModel.articleWriteRequest!!.records[articleWriteViewModel.dayIndex].dayComment
            )
            binding.textArticleWriteDay.text = "Day${articleWriteViewModel.dayIndex + 1}"
            binding.textArticleWriteDate.text =
                articleWriteViewModel.tripStamps[articleWriteViewModel.dayIndex][0].day
        }
    }

    private fun observeData() {
        articleWriteViewModel.tripStampState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SUCCESS -> {
                    articleWriteTripStampAdapter.apply {
                        tripStamps =
                            articleWriteViewModel.tripStamps[articleWriteViewModel.dayIndex]
                        notifyDataSetChanged()
                    }
                    binding.editTextCommentAddComment.setText(
                        articleWriteViewModel.articleWriteRequest!!.records[articleWriteViewModel.dayIndex].dayComment
                    )
                    binding.textArticleWriteDay.text = "Day${articleWriteViewModel.dayIndex + 1}"
                    binding.textArticleWriteDate.text =
                        articleWriteViewModel.tripStamps[articleWriteViewModel.dayIndex][0].day
                    articleWriteViewModel.tripStampState.value = DEFAULT
                }
                FAIL -> {
                    articleWriteViewModel.tripStampState.value = DEFAULT
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.buttonArticleWriteNext.setOnClickListener {
            // dayComment 기록
            articleWriteViewModel.let {
                it.articleWriteRequest!!.records[it.dayIndex].dayComment =
                    binding.editTextCommentAddComment.text.toString()
            }

            // 아직 기록할 Day 가 남아있는 경우 다시 ArticleWriteDayFragment 로 이동
            if (articleWriteViewModel.dayIndex < articleWriteViewModel.tripStamps.size - 1) {
                articleWriteViewModel.dayIndex += 1
                findNavController().navigate(R.id.action_articleWriteDayFragment_to_articleWriteDayFragment)
            }
            // Day 기록 다 했으면 ArticleWriteExpenseFragment 로 이동
            else {
                findNavController().navigate(R.id.action_articleWriteDayFragment_to_articleWriteExpenseFragment)
            }
        }

        binding.imgArticleWriteBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initData() {
        if (App.isDone) {
            articleWriteViewModel.getTripStamps()
            App.isDone = false
        }
    }
}