package com.ssafy.daero.ui.adapter.sns

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.article.ArticleHomeItem
import com.ssafy.daero.databinding.ItemHomeBinding

class HomeAdapter(
    private val onLikeClickListener: (Int) -> Unit,     // 좋아요 버튼 클릭
    private val onLikesClickListener: (Int, Int) -> Unit,    // 좋아요 갯수 클릭 (좋아요 리스트 보여주기)
    private val onCommentClickListener: (Int, Int) -> Unit,  // 댓글 버튼 클릭 (댓글 리스트 보여주기)
    private val onMoreClickListener: (Int) -> Unit,     // 더보기 버튼 클릭
    private val onArticleClickListener: (Int) -> Unit,  // 게시글 클릭 (게시글 상세로 이동)
    private val onUserClickListener: (Int) -> Unit,     // 유저 클릭
) : PagingDataAdapter<ArticleHomeItem, HomeAdapter.HomeViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindLikeClickListener(onLikeClickListener)
            bindLikesClickListener(onLikesClickListener)
            bindCommentClickListener(onCommentClickListener)
            bindMoreClickListener(onMoreClickListener)
            bindArticleClickListener(onArticleClickListener)
            bindUserClickListener(onUserClickListener)
        }
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(article = it)
        }
    }

    class HomeViewHolder(private val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticleHomeItem) {
            binding.article = article
        }

        fun bindLikeClickListener(onLikeClickListener: (Int) -> Unit) {
            binding.imageHomeItemLike.setOnClickListener {
                // todo: 좋아요 기능
            }
        }

        fun bindLikesClickListener(onLikesClickListener: (Int, Int) -> Unit) {
            binding.textHomeItemLike.setOnClickListener {
                onLikesClickListener(binding.article?.article_seq ?: 0, binding.article?.likes ?: 0)
            }
        }

        fun bindCommentClickListener(onCommentClickListener: (Int, Int) -> Unit) {
            binding.imageHomeItemComment.setOnClickListener {
                onCommentClickListener(
                    binding.article?.article_seq ?: 0,
                    binding.article?.replies ?: 0
                )
            }
            binding.textHomeItemComment.setOnClickListener {
                onCommentClickListener(
                    binding.article?.article_seq ?: 0,
                    binding.article?.replies ?: 0
                )
            }
        }

        fun bindArticleClickListener(onArticleClickListener: (Int) -> Unit) {
            binding.imageHomeItemThumbnail.setOnClickListener {
                onArticleClickListener(binding.article?.article_seq ?: 0)
            }
        }

        fun bindMoreClickListener(onMoreClickListener: (Int) -> Unit) {

        }

        fun bindUserClickListener(onUserClickListener: (Int) -> Unit) {
            binding.imageHomeItemUser.setOnClickListener {
                onUserClickListener(binding.article?.user_seq ?: 0)
            }
            binding.textHomeItemNickname.setOnClickListener {
                onUserClickListener(binding.article?.user_seq ?: 0)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ArticleHomeItem>() {
            override fun areItemsTheSame(
                oldItem: ArticleHomeItem,
                newItem: ArticleHomeItem
            ): Boolean {
                return oldItem.article_seq == newItem.article_seq
            }

            override fun areContentsTheSame(
                oldItem: ArticleHomeItem,
                newItem: ArticleHomeItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}