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
import com.ssafy.daero.ui.adapter.sns.ArticleWriteExpenseAdapter
import com.ssafy.daero.ui.adapter.sns.ArticleWriteTripStampAdapter
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import java.text.SimpleDateFormat
import java.util.*

class ArticleWriteExpenseFragment : BaseFragment<FragmentArticleWriteExpenseBinding>(R.layout.fragment_article_write_expense) {
    private lateinit var articleWriteExpenseAdapter: ArticleWriteExpenseAdapter
    private var idxList = mutableListOf<Int>()

    override fun init() {
        initViews()
        setOnClickListeners()
    }

    private fun initViews(){
        binding.ratingArticleWriteSatisfaction.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            binding.ratingArticleWriteSatisfaction.rating = rating
        }
    }



    private fun setOnClickListeners(){
        binding.buttonArticleWriteNext.setOnClickListener {
            //todo : thumbnail로 이동 -> 데이터 처리 어떻게? -> articleWriteExpenseAdapter.title, articleWriteExpenseAdapter.expense 활용
            //todo : rating 점수도 처리
        }
        binding.imgArticleWritePlus.setOnClickListener {
            articleWriteExpenseAdapter = ArticleWriteExpenseAdapter().apply {
                index = idxList
            }
            binding.recyclerArticleWriteExpense.apply {
                adapter = articleWriteExpenseAdapter
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            }
        }
        binding.imgArticleWriteBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}