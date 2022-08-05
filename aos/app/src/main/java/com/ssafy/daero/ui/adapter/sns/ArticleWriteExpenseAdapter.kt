package com.ssafy.daero.ui.adapter.sns

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.databinding.ItemArticleWriteExpenseBinding

class ArticleWriteExpenseAdapter : RecyclerView.Adapter<ArticleWriteExpenseAdapter.ArticleWriteExpenseViewHolder>() {

    var index: List<Int> = emptyList()
    var title = mutableMapOf<Int,String>()
    var expense = mutableMapOf<Int,String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleWriteExpenseViewHolder {
        return ArticleWriteExpenseViewHolder(
            ItemArticleWriteExpenseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            title,
            expense
        )
    }

    override fun onBindViewHolder(holder: ArticleWriteExpenseViewHolder, position: Int) {
        holder.bind(index[position]!!)
    }

    override fun getItemCount() = index.size

    class ArticleWriteExpenseViewHolder(
        private val binding: ItemArticleWriteExpenseBinding,
        val title: MutableMap<Int, String>,
        val expense: MutableMap<Int, String>
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Int) {
            binding.viewArticleExpenseTitle.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    title[data] = binding.viewArticleExpenseTitle.text.toString()
                }
            })
            binding.viewArticleExpenseContent.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    expense[data] = binding.viewArticleExpenseContent.text.toString()
                }
            })
        }
    }
}