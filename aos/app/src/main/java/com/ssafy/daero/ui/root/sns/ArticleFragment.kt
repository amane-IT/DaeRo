package com.ssafy.daero.ui.root.sns

import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.article.Record
import com.ssafy.daero.data.dto.article.TripStamp
import com.ssafy.daero.databinding.FragmentArticleBinding
import com.ssafy.daero.ui.adapter.sns.ArticleAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS

class ArticleFragment : BaseFragment<FragmentArticleBinding>(R.layout.fragment_article) {

    private val articleViewModel : ArticleViewModel by viewModels()
    private lateinit var articleAdapter: ArticleAdapter
    var recordList: MutableList<Record> = mutableListOf()
    var stampList: MutableList<TripStamp> = mutableListOf()

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
        //articleViewModel.article("1")
    }

    private fun setOnClickListeners() {
        binding.imgArticleUser.setOnClickListener {
            //todo 마이페이지로 이동
        }
        binding.tvArticleUser.setOnClickListener {
            //todo 마이페이지로 이동
        }
        binding.LinearArticleLikeImg.setOnClickListener {
            //todo 좋아요 상태 변경
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
    }

    private fun observeData() {
        articleViewModel.responseState.observe(viewLifecycleOwner) { state ->
            when(state) {
                SUCCESS -> {
                    articleAdapter = ArticleAdapter().apply {
                        this.onItemClickListener = this@ArticleFragment.onItemClickListener
                    }
                    binding.recyclerArticleTrip.apply {
                        adapter = articleAdapter
                        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    }
                    articleViewModel.responseState.value = DEFAULT
                }
                FAIL -> {

                    articleViewModel.responseState.value = DEFAULT
                }
            }
        }
    }
}