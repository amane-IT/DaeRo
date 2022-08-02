package com.ssafy.daero.ui.root.sns

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.databinding.*
import com.ssafy.daero.ui.adapter.sns.ArticleWriteThumbnailAdapter
import com.ssafy.daero.ui.adapter.sns.ArticleWriteTripStampAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import java.text.SimpleDateFormat
import java.util.*

class ArticleWriteThumbnailFragment : BaseFragment<FragmentArticleWriteThumbnailBinding>(R.layout.fragment_article_write_thumbnail) {

    private val articleWriteViewModel: ArticleWriteViewModel by viewModels()
    private lateinit var articleWriteThumbnailAdapter: ArticleWriteThumbnailAdapter

    override fun init() {
        initViews()
        observeData()
        setOnClickListeners()
    }

    private fun initViews(){
        articleWriteViewModel.getTripStamps()
    }

    private fun observeData(){
        articleWriteViewModel.responseState.observe(viewLifecycleOwner) { state ->
            when(state) {
                SUCCESS -> {
                    setBinding()
                    articleWriteViewModel.responseState.value = DEFAULT
                }
                FAIL -> {

                    articleWriteViewModel.responseState.value = DEFAULT
                }
            }
        }
    }

    private fun setBinding(){
        articleWriteThumbnailAdapter = ArticleWriteThumbnailAdapter().apply {
            articleTripStampData = articleWriteViewModel.articleTripStampData
        }
        binding.recyclerArticleWriteThumbnailTripStamp.apply {
            adapter = articleWriteThumbnailAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

    }

    private fun setOnClickListeners(){
        binding.buttonArticleWrite.setOnClickListener {
            //todo : 완료처리 전에 데이터 처리 어떻게? -> ViewModel에 api 연동

        }

        binding.imgArticleWriteBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}