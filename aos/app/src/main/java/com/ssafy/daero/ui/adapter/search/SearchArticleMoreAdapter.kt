package com.ssafy.daero.ui.adapter.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.search.ArticleMoreItem
import com.ssafy.daero.databinding.ItemSearchArticleMoreBinding

class SearchArticleMoreAdapter(
    private val onArticleClickListener: (Int) -> Unit, // 게시글 클릭
    private val onUserClickListener: (Int) -> Unit, // 유저 클릭
    private val onCommentClickListener: (Int, Int) -> Unit, // 코멘트 클릭
    private val onLikeClickListener: (Int, Int) -> Unit, // 좋아요 클릭
    private val onMenuClickListener: (Int) -> Unit // 더보기 클릭
): PagingDataAdapter<ArticleMoreItem, SearchArticleMoreAdapter.SearchArticleMoreViewHolder>(
    COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArticleMoreAdapter.SearchArticleMoreViewHolder {
        return SearchArticleMoreAdapter.SearchArticleMoreViewHolder(
            ItemSearchArticleMoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnArticleClickListener(onArticleClickListener)
            bindOnUserClickListener(onUserClickListener)
            bindOnCommentClickListener(onCommentClickListener)
            bindOnLikeClickListener(onLikeClickListener)
            bindOnMenuClickListener(onMenuClickListener)
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

        fun bindOnArticleClickListener(onArticleClickListener:(Int) -> Unit) {
            binding.imageViewArticleMoreTripStamp.setOnClickListener{
                onArticleClickListener(binding.articleMore?.article_seq ?: 0)
            }
        }

        fun bindOnUserClickListener(onUserClickListener: (Int) -> Unit) {
            binding.imageArticleMoreUserImg.setOnClickListener{
                onUserClickListener(binding.articleMore?.user_seq ?: 0)
            }
        }
        fun bindOnCommentClickListener(onCommentClickListener: (Int, Int) -> Unit) {
            // TODO: 코멘트 클릭 이벤트 추가
            binding.imageViewArticleMoreComment.setOnClickListener {
                onCommentClickListener(
                    binding.articleMore?.article_seq ?: 0,
                    binding.articleMore?.comments ?: 0
                )
            }

            binding.textArticleMoreComment.setOnClickListener {
                onCommentClickListener(
                    binding.articleMore?.article_seq ?: 0,
                    binding.articleMore?.comments ?: 0
                )
            }
        }

        fun bindOnLikeClickListener(onLikeClickListener: (Int, Int) -> Unit) {
            // TODO: 좋아요 클릭 이벤트 추가
        }

        fun bindOnMenuClickListener(onMenuClickListener: (Int) -> Unit){
            // TODO: 더보기 메뉴 클릭 이벤트 추가
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