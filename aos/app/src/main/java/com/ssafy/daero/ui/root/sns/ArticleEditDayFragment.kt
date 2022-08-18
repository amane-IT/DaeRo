package com.ssafy.daero.ui.root.sns

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.application.App
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentArticleEditDayBinding
import com.ssafy.daero.ui.adapter.sns.ArticleEditTripStampAdapter
import com.ssafy.daero.utils.constant.ARTICLE_SEQ
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.view.toast

class ArticleEditDayFragment :
    BaseFragment<FragmentArticleEditDayBinding>(R.layout.fragment_article_edit_day) {

    val articleEditViewModel: ArticleEditViewModel by viewModels({ requireParentFragment() })
    private lateinit var tripStampAdapter: ArticleEditTripStampAdapter

    private var articleSeq = 0

    override fun init() {
        initAdapter()
        initView()
        observeData()
        setOnClickListeners()
        initData()
    }

    private fun initAdapter() {
        tripStampAdapter = ArticleEditTripStampAdapter()
        binding.recyclerArticleEditTripStamp.adapter = tripStampAdapter
    }

    private fun initView() {
        if (articleEditViewModel.dayIndex >= 0 && articleEditViewModel.tripStamps.isNotEmpty()) {
            tripStampAdapter.tripStamps =
                articleEditViewModel.tripStamps[articleEditViewModel.dayIndex]
            tripStampAdapter.notifyDataSetChanged()

            binding.textArticleEditDay.text = "Day${articleEditViewModel.dayIndex + 1}"
            binding.textArticleEditDate.text =
                articleEditViewModel.dates[articleEditViewModel.dayIndex]
            binding.editTextArticleEditComment.setText(articleEditViewModel.articleEditRequest!!.records[articleEditViewModel.dayIndex])
        }
    }

    private fun initData() {
        articleSeq = arguments?.getInt(ARTICLE_SEQ, 0) ?: 0
        if (App.isEdit && articleSeq > 0) {
            articleEditViewModel.editArticleSeq = articleSeq
            articleEditViewModel.getArticle(articleSeq)
            App.isEdit = false
        }
    }

    private fun observeData() {
        articleEditViewModel.articleState.observe(viewLifecycleOwner) {
            when (it) {
                SUCCESS -> {
                    tripStampAdapter.tripStamps =
                        articleEditViewModel.tripStamps[articleEditViewModel.dayIndex]
                    tripStampAdapter.notifyDataSetChanged()

                    binding.textArticleEditDay.text = "Day${articleEditViewModel.dayIndex + 1}"
                    binding.textArticleEditDate.text =
                        articleEditViewModel.dates[articleEditViewModel.dayIndex]
                    binding.editTextArticleEditComment.setText(articleEditViewModel.articleEditRequest!!.records[articleEditViewModel.dayIndex])

                    articleEditViewModel.articleState.value = DEFAULT
                }
                FAIL -> {
                    toast("게시글 정보를 불러오는데 실패했습니다.")
                    articleEditViewModel.articleState.value = DEFAULT
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageArticleEditBack.setOnClickListener { requireActivity().onBackPressed() }
        binding.buttonArticleEditNext.setOnClickListener {
            // dayComment 수정
            articleEditViewModel.articleEditRequest?.let {
                it.records[articleEditViewModel.dayIndex] =
                    binding.editTextArticleEditComment.text.toString()
            }

            // 아직 Day 가 남아있다면 다시 ArticleEditDayFragment 로 이동
            if (articleEditViewModel.dayIndex < articleEditViewModel.tripStamps.size - 1) {
                articleEditViewModel.dayIndex += 1
                findNavController().navigate(R.id.action_articleEditDayFragment_self)
            }
            // Day 기록 다 했으면 ArticleEditExpenseFragment 로 이동
            else {
                findNavController().navigate(R.id.action_articleEditDayFragment_to_articleEditExpenseFragment)
            }
        }
    }
}