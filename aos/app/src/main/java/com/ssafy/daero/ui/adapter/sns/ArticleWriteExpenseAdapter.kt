package com.ssafy.daero.ui.adapter.sns

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.article.Expense
import com.ssafy.daero.databinding.ItemArticleWriteExpenseBinding
import java.util.*
import kotlin.concurrent.schedule

class ArticleWriteExpenseAdapter(
    private val removeExpenseListener: (Int) -> Unit,
    private val expenseChangeListener: (Int, String) -> Unit,
    private val nameChangeListener: (Int, String) -> Unit
) : RecyclerView.Adapter<ArticleWriteExpenseAdapter.ArticleWriteExpenseViewHolder>() {
    var expenses = mutableListOf<Expense>()
    private var timer1 = Timer()
    private var timer2 = Timer()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleWriteExpenseViewHolder {
        return ArticleWriteExpenseViewHolder(
            ItemArticleWriteExpenseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindListener(
                removeExpenseListener,
                expenseChangeListener,
                nameChangeListener
            )
        }
    }

    override fun onBindViewHolder(holder: ArticleWriteExpenseViewHolder, position: Int) {
        holder.bind(expenses[position])
    }

    override fun getItemCount() = expenses.size

    inner class ArticleWriteExpenseViewHolder(
        private val binding: ItemArticleWriteExpenseBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindListener(
            removeExpenseListener: (Int) -> Unit,
            expenseChangeListener: (Int, String) -> Unit,
            nameChangeListener: (Int, String) -> Unit
        ) {
            binding.imageItemArticleWriteExpenseRemove.setOnClickListener {
                removeExpenseListener(bindingAdapterPosition)
            }
            binding.editTextArticleExpenseContent.addTextChangedListener {
                timer2.cancel()

                timer2 = Timer()
                timer2.schedule(1000L) {
                    expenseChangeListener(bindingAdapterPosition, it.toString())
                }
            }
            binding.editTextArticleExpenseTitle.doAfterTextChanged {
                timer1.cancel()

                timer1 = Timer()
                timer1.schedule(1000L) {
                    nameChangeListener(bindingAdapterPosition, it.toString())
                }
            }
        }

        fun bind(data: Expense) {
            binding.expense = data
        }
    }
}