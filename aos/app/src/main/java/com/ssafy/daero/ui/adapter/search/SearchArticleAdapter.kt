package com.ssafy.daero.ui.adapter.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.sns.ArticleItem
import com.ssafy.daero.databinding.ItemSearchArticleBinding

class SearchArticleAdapter : PagingDataAdapter<ArticleItem, SearchArticleAdapter.SearchArticleViewHolder>(
    COMPARATOR
) {
    lateinit var onItemClickListener: (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArticleAdapter.SearchArticleViewHolder {
        return SearchArticleAdapter.SearchArticleViewHolder(
            ItemSearchArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: SearchArticleAdapter.SearchArticleViewHolder, position: Int) {
        getItem(position)?.let{
            holder.bind(it)
        }
    }

    class SearchArticleViewHolder(private val binding: ItemSearchArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(articles : ArticleItem){
            binding.article = articles
        }

        fun bindOnItemClickListener(onItemClickListener: (View, Int) -> Unit){
            binding.root.setOnClickListener{
                onItemClickListener(it, binding.article!!.article_seq)
            }
        }
    }

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<ArticleItem>(){
            override fun areItemsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
                return oldItem.article_seq == newItem.article_seq
            }

            override fun areContentsTheSame(
                oldItem: ArticleItem,
                newItem: ArticleItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}