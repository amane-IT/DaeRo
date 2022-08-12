package com.ssafy.daero.ui.adapter.sns

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ssafy.daero.R
import com.ssafy.daero.data.dto.article.CommentItem
import com.ssafy.daero.databinding.ItemCommentBinding
import com.ssafy.daero.ui.root.sns.CommentListener

class CommentAdapter(
    private val articleSeq: Int
    , listener: CommentListener
    ) : PagingDataAdapter<CommentItem, CommentAdapter.CommentViewHolder>(COMPARATOR) {

    lateinit var onItemClickListener : (View, Int, Int, Int, String) -> Unit
    var mCallback = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            articleSeq,
            mCallback
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class CommentViewHolder(
        private val binding: ItemCommentBinding,
        private val articleSeq: Int,
        private val mCallback: CommentListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var userSeq: Int? = null
        private var replySeq: Int? = null
        private var modified: Char? = null
        private var replyCount: Int? = null

        fun bind(data: CommentItem) {
            binding.recyclerCommentReComment.visibility=View.GONE
            Glide.with(binding.imgCommentItemUser)
                .load(data.profile_url)
                .override(200,200)
                .placeholder(R.drawable.img_user)
                .apply(RequestOptions().centerCrop())
                .error(R.drawable.img_user)
                .into(binding.imgCommentItemUser)

             binding.tvCommentUser.text = data.nickname
            binding.tvCommentCreateAt.text = data.created_at
            binding.tvCommentContent.text = data.content
            if(data.rereply_count > 0){
                binding.tvCommentCount.text = "답글 "+data.rereply_count.toString()+"개 더 보기"
                binding.LinearCommentReComment.visibility = View.VISIBLE
                replyCount = data.rereply_count
            }
            replySeq = data.reply_seq
            userSeq = data.user_seq
            modified = data.modified
        }
        fun bindOnItemClickListener(onItemClickListener: (View, Int, Int, Int, String) -> Unit) {
            binding.LinearCommentReComment.setOnClickListener {
                var adapter: ReCommentAdapter = mCallback.reCommentSelect(articleSeq,replySeq!!,ReCommentAdapter(onItemClickListener))
                binding.LinearCommentReComment.visibility = View.GONE
                binding.progressBarCommentLoading.visibility = View.VISIBLE
                Handler().postDelayed({
                    binding.recyclerCommentReComment.visibility = View.VISIBLE
                    binding.recyclerCommentReComment.apply {
                        this.adapter = adapter
                        layoutManager = LinearLayoutManager(
                            binding.root.context,
                            RecyclerView.VERTICAL,
                            false
                        )
                    }
                    binding.progressBarCommentLoading.visibility = View.GONE
                }, 500L)
            }
            binding.imgCommentMenu.setOnClickListener {
                onItemClickListener(it, replySeq!!, 1, userSeq!!, binding.tvCommentContent.text.toString())
            }
            binding.imgCommentItemUser.setOnClickListener {
                onItemClickListener(it, userSeq!!, 2, userSeq!!, "")
            }
            binding.tvCommentReCommentAdd.setOnClickListener {
                mCallback.reCommentAdd(replySeq!!)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<CommentItem>() {
            override fun areItemsTheSame(oldItem: CommentItem, newItem: CommentItem): Boolean {
                return oldItem.reply_seq == newItem.reply_seq
            }

            override fun areContentsTheSame(
                oldItem: CommentItem,
                newItem: CommentItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}