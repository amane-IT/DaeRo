package com.ssafy.daero.ui.adapter.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.search.ArticleItem
import com.ssafy.daero.data.dto.trip.TripPopularResponseDto
import com.ssafy.daero.databinding.ItemSearchArticleBinding
import com.ssafy.daero.databinding.ItemTripPopularBinding
import com.ssafy.daero.ui.adapter.trip.TripPopularAdapter

class SearchArticleAdapter
    : RecyclerView.Adapter<SearchArticleAdapter.SearchArticleViewHolder>() {
    var resultList : List<ArticleItem> = emptyList()
    lateinit var onItemClickListener: (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArticleAdapter.SearchArticleViewHolder {
        return SearchArticleViewHolder(
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
        holder.bind(resultList[position])
    }

    override fun getItemCount(): Int = resultList.size

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
}