package com.ssafy.daero.ui.root.sns

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.daero.R
import com.ssafy.daero.base.BaseFragment
import com.ssafy.daero.data.dto.article.Expense
import com.ssafy.daero.databinding.FragmentArticleEditExpenseBinding
import com.ssafy.daero.ui.adapter.sns.ArticleWriteExpenseAdapter

class ArticleEditExpenseFragment :
    BaseFragment<FragmentArticleEditExpenseBinding>(R.layout.fragment_article_edit_expense) {

    private val articleEditViewModel: ArticleEditViewModel by viewModels({ requireParentFragment() })
    private lateinit var expenseAdapter: ArticleWriteExpenseAdapter

    override fun init() {
        initViews()
        initAdapter()
        setOnClickListeners()
        setOtherListeners()
    }

    private fun initViews() {
        binding.ratingArticleEditSatisfaction.rating =
            articleEditViewModel.articleEditRequest?.rating?.toFloat() ?: 0F
    }

    private val removeExpenseListener: (Int) -> Unit = { index ->
        articleEditViewModel.expenses.removeAt(index)
        expenseAdapter.apply {
            expenses = articleEditViewModel.expenses
            notifyDataSetChanged()
        }
    }

    private val expenseChangeListener: (Int, String) -> Unit = { index, expense ->
        requireActivity().runOnUiThread {
            articleEditViewModel.expenses[index].expenses = expense
        }
    }

    private val nameChangeListener: (Int, String) -> Unit = { index, name ->
        requireActivity().runOnUiThread {
            articleEditViewModel.expenses[index].expenses_name = name
        }
    }

    private fun initAdapter() {
        expenseAdapter = ArticleWriteExpenseAdapter(
            removeExpenseListener,
            expenseChangeListener,
            nameChangeListener
        ).apply {
            expenses = articleEditViewModel.expenses
        }
        binding.recyclerArticleEditExpense.adapter = expenseAdapter
    }

    private fun setOnClickListeners() {
        binding.buttonArticleEditNext.setOnClickListener {
            findNavController().navigate(R.id.action_articleEditExpenseFragment_to_articleEditThumbnailFragment)
        }
        binding.imageArticleEditPlus.setOnClickListener {
            articleEditViewModel.expenses.add(Expense("", ""))
            expenseAdapter.notifyDataSetChanged()
        }
        binding.imageArticleEditBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun setOtherListeners() {
        binding.ratingArticleEditSatisfaction.setOnRatingBarChangeListener { _, rating, fromUser ->
            if (fromUser) {
                articleEditViewModel.articleEditRequest?.rating = rating.toInt()
            }
        }
    }
}