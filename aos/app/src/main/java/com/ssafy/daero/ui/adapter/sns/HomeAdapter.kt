package com.ssafy.daero.ui.adapter.sns

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.ssafy.daero.R
import com.ssafy.daero.data.dto.article.ArticleHomeItem
import com.ssafy.daero.databinding.ItemHomeBinding
import com.ssafy.daero.utils.view.setTint

class HomeAdapter(
    private val onLikeClickListener: (Int, Boolean) -> Unit,     // 좋아요 버튼 클릭
    private val onLikesClickListener: (Int, Int) -> Unit,    // 좋아요 갯수 클릭 (좋아요 리스트 보여주기)
    private val onCommentClickListener: (Int, Int) -> Unit,  // 댓글 버튼 클릭 (댓글 리스트 보여주기)
    private val onMoreClickListener: (Int, Int, Int) -> Unit,     // 더보기 버튼 클릭
    private val onArticleClickListener: (Int) -> Unit,  // 게시글 클릭 (게시글 상세로 이동)
    private val onUserClickListener: (Int) -> Unit,     // 유저 클릭
    private val context: Context,
    private val activity: Activity
) : PagingDataAdapter<ArticleHomeItem, HomeAdapter.HomeViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context,
            activity
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

    class HomeViewHolder(private val binding: ItemHomeBinding, val context: Context, val activity: Activity) :
        RecyclerView.ViewHolder(binding.root) {

        var likes: Int = 0

        fun bind(article: ArticleHomeItem) {
            binding.article = article
            likes = article.likes
            Log.d("좋아요 상태",article.toString())
        }

        fun bindLikeClickListener(onLikeClickListener: (Int, Boolean) -> Unit) {
            binding.imageHomeItemLike.setOnClickListener {
                when(binding.article?.like_yn){
                    'y' -> onLikeClickListener(binding.article?.article_seq ?: 0, true).apply {
                        if (binding.textHomeItemLike.text.toString().toInt() > 0) {
                            likes -= 1
                            binding.textHomeItemLike.text = likes.toString()
                        }
                        binding.article?.like_yn = 'n'
                        binding.imageHomeItemLike.setImageResource(R.drawable.ic_like)
                        binding.imageHomeItemLike.setTint(Color.WHITE)
                        binding.imageHomeItemLike.invalidate()
                    }
                    'n' -> onLikeClickListener(binding.article?.article_seq ?: 0, false).apply {
                        likes += 1
                        binding.textHomeItemLike.text = likes.toString()
                        binding.article?.like_yn = 'y'
                        binding.imageHomeItemLike.setImageResource(R.drawable.ic_like_full)
                        binding.imageHomeItemLike.colorFilter = null
                        var fadeScale: Animation = AnimationUtils.loadAnimation(context, R.anim.scale)
                        binding.imageHomeItemLike.startAnimation(fadeScale)
                    }
                }
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

        fun bindMoreClickListener(onMoreClickListener: (Int, Int, Int) -> Unit) {
            binding.imageHomeItemMenu.setOnClickListener {
                onMoreClickListener(binding.article?.article_seq ?: 0, binding.article?.user_seq ?: 0, bindingAdapterPosition)
            }
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