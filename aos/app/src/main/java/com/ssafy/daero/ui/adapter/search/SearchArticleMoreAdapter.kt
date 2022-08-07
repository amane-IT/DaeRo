package com.ssafy.daero.ui.adapter.search

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.ssafy.daero.R
import com.ssafy.daero.data.dto.search.ArticleMoreItem
import com.ssafy.daero.databinding.ItemSearchArticleMoreBinding

class SearchArticleMoreAdapter(
    private val onArticleClickListener: (Int) -> Unit, // 게시글 클릭
    private val onUserClickListener: (Int) -> Unit, // 유저 클릭
    private val onCommentClickListener: (Int, Int) -> Unit, // 코멘트 클릭
    private val onLikeClickListener: (Int, Boolean) -> Unit, // 좋아요 버튼 클릭
    private val onLikeTextClickListener: (Int, Int) -> Unit, // 좋아요 텍스트 클릭
    private val onMenuClickListener: (Int, Int) -> Unit // 더보기 클릭
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
            bindOnLikeTextClickListener(onLikeTextClickListener)
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
        var likes: Int = 0

        fun bind(articleMore : ArticleMoreItem){
            binding.articleMore = articleMore
            likes = articleMore.likes
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
                    binding.articleMore?.replies ?: 0
                )
            }

            binding.textArticleMoreComment.setOnClickListener {
                onCommentClickListener(
                    binding.articleMore?.article_seq ?: 0,
                    binding.articleMore?.replies ?: 0
                )
            }
        }

        fun bindOnLikeClickListener(onLikeClickListener: (Int, Boolean) -> Unit) {
            binding.imageViewArticleMoreLike.setOnClickListener {
                when(binding.articleMore?.like_yn) {
                    'y' -> onLikeClickListener(binding.articleMore?.article_seq ?: 0, true).apply {
                        if(binding.textArticleMoreLike.text.toString().toInt() > 0) {
                            likes--
                            binding.textArticleMoreLike.text = likes.toString()
                        }
                        binding.articleMore?.like_yn = 'n'
                        binding.imageViewArticleMoreLike.setImageResource(R.drawable.ic_like)
                        binding.imageViewArticleMoreLike.setColorFilter(Color.WHITE)
                        binding.imageViewArticleMoreLike.invalidate()
                    }

                    'n' -> onLikeClickListener(binding.articleMore?.article_seq ?: 0, true).apply {
                        likes++
                        binding.textArticleMoreLike.text = likes.toString()
                        binding.articleMore?.like_yn = 'y'
                        binding.imageViewArticleMoreLike.setImageResource(R.drawable.ic_like_full)
                        binding.imageViewArticleMoreLike.setColorFilter(Color.RED)
                        var fadeScale: Animation = AnimationUtils.loadAnimation(context, R.anim.scale)
                        binding.imageViewArticleMoreLike.startAnimation(fadeScale)
                        binding.imageViewArticleMoreLike.invalidate()
                    }
                }
            }
        }

        fun bindOnLikeTextClickListener(onLikeTextClickListener: (Int, Int) -> Unit) {
            binding.textArticleMoreLike.setOnClickListener{
                onLikeTextClickListener(binding.articleMore?.article_seq ?: 0, binding.articleMore?.likes ?: 0)
            }
        }

        fun bindOnMenuClickListener(onMenuClickListener: (Int, Int) -> Unit) {
            binding.imageViewArticleMoreMenu.setOnClickListener {
                onMenuClickListener(binding.articleMore?.article_seq ?: 0, binding.articleMore?.user_seq ?: 0)
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