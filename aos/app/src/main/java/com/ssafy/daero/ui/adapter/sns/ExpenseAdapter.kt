package com.ssafy.daero.ui.adapter.sns

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.article.Expense
import com.ssafy.daero.databinding.ItemArticleExpenseBinding
import java.text.DecimalFormat

class ExpenseAdapter : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    var expense: List<Expense> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        return ExpenseViewHolder(
            ItemArticleExpenseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(expense[position])
    }

    override fun getItemCount() = expense.size

    class ExpenseViewHolder(private val binding: ItemArticleExpenseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Expense) {
            binding.viewArticleExpenseTitle.text = data.expenses_name
            val decimal = DecimalFormat("#,###")
            binding.viewArticleExpenseContent.text = decimal.format(data.expenses.toInt())+"원"
        }
    }
}