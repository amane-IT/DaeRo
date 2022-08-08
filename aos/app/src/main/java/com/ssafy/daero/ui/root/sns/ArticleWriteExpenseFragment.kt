package com.ssafy.daero.ui.root.sns

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.databinding.FragmentArticleWriteExpenseBinding
import com.ssafy.daero.ui.adapter.sns.ArticleWriteExpenseAdapter

class ArticleWriteExpenseFragment :
    BaseFragment<FragmentArticleWriteExpenseBinding>(R.layout.fragment_article_write_expense) {
    private val articleWriteViewModel: ArticleWriteViewModel by viewModels({ requireParentFragment() })
    private lateinit var articleWriteExpenseAdapter: ArticleWriteExpenseAdapter

    override fun init() {
        initViews()
        initAdapter()
        setOnClickListeners()
        setOtherListeners()
    }

    private fun initViews() {
        binding.ratingArticleWriteSatisfaction.rating =
            articleWriteViewModel.articleWriteRequest?.rating?.toFloat() ?: 0F
    }

    private val removeExpenseListener: (Int) -> Unit = { index ->
        articleWriteViewModel.expenses.removeAt(index)
        articleWriteExpenseAdapter.apply {
            expenses = articleWriteViewModel.expenses
            notifyDataSetChanged()
        }
    }

    private val expenseChangeListener: (Int, String) -> Unit = { index, expense ->
        requireActivity().runOnUiThread {
            articleWriteViewModel.expenses[index].expenses = expense
        }
    }

    private val nameChangeListener: (Int, String) -> Unit = { index, name ->
        requireActivity().runOnUiThread {
            articleWriteViewModel.expenses[index].expense_name = name
        }
    }

    private fun initAdapter() {
        articleWriteExpenseAdapter = ArticleWriteExpenseAdapter(
            removeExpenseListener,
            expenseChangeListener,
            nameChangeListener
        ).apply {
            expenses = articleWriteViewModel.expenses
        }
        binding.recyclerArticleWriteExpense.adapter = articleWriteExpenseAdapter
    }

    private fun setOnClickListeners() {
        binding.buttonArticleWriteNext.setOnClickListener {
            findNavController().navigate(R.id.action_articleWriteExpenseFragment_to_articleWriteThumbnailFragment)
        }
        binding.imgArticleWritePlus.setOnClickListener {
            articleWriteViewModel.expenses.add(ExpenseDto("", ""))
            articleWriteExpenseAdapter.notifyDataSetChanged()
        }
        binding.imgArticleWriteBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setOtherListeners() {
        binding.ratingArticleWriteSatisfaction.setOnRatingBarChangeListener { _, rating, fromUser ->
            if (fromUser) {
                articleWriteViewModel.articleWriteRequest?.rating = rating.toInt()
            }
        }
    }
}