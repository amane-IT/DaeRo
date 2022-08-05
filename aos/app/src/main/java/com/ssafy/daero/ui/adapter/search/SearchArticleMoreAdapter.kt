package com.ssafy.daero.ui.adapter.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.search.ArticleMoreItem
import com.ssafy.daero.databinding.ItemSearchArticleMoreBinding

class SearchArticleMoreAdapter: PagingDataAdapter<ArticleMoreItem, SearchArticleMoreAdapter.SearchArticleMoreViewHolder>(
    COMPARATOR
) {
    lateinit var onItemClickListener: (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArticleMoreAdapter.SearchArticleMoreViewHolder {
        return SearchArticleMoreAdapter.SearchArticleMoreViewHolder(
            ItemSearchArticleMoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: SearchArticleMoreViewHolder, position: Int) {
        getItem(position)?.let{
            holder.bind(it)
        }
    }

    class SearchArticleMoreViewHolder(private val binding: ItemSearchArticleMoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(articleMore : ArticleMoreItem){
            binding.articleMore = articleMore
        }

        fun bindOnItemClickListener(onItemClickListener: (View, Int) -> Unit){
            binding.root.setOnClickListener{
                onItemClickListener(it, binding.articleMore!!.article_seq)
            }
        }
    }

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<ArticleMoreItem>(){
            override fun areItemsTheSame(oldItem: ArticleMoreItem, newItem: ArticleMoreItem): Boolean {
                return oldItem.user_seq == newItem.user_seq
            }

            override fun areContentsTheSame(
                oldItem: ArticleMoreItem,
                newItem: ArticleMoreItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}