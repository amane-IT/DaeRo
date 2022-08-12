package com.ssafy.daero.ui.adapter.sns

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ssafy.daero.R
import com.ssafy.daero.data.dto.article.ReCommentItem
import com.ssafy.daero.databinding.ItemReCommentBinding

class ReCommentAdapter(onItemClickListener : (View, Int, Int, Int, String) -> Unit) :
    PagingDataAdapter<ReCommentItem, ReCommentAdapter.ReCommentViewHolder>
        (COMPARATOR) {

    var onItemClickListener = onItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReCommentViewHolder {
        return ReCommentViewHolder(
            ItemReCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: ReCommentViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class ReCommentViewHolder(private val binding: ItemReCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var userSeq: Int? = null
        private var replySeq: Int? = null
        private var modified: Char? = null

        fun bind(data: ReCommentItem) {
            Glide.with(binding.imgReCommentUser)
                .load(data.profile_url)
                .override(200,200)
                .placeholder(R.drawable.img_user)
                .apply(RequestOptions().centerCrop())
                .error(R.drawable.img_user)
                .into(binding.imgReCommentUser)

            binding.tvCommentUser.text = data.nickname
            binding.tvCommentCreateAt.text = data.created_at
            binding.tvCommentContent.text = data.content
            replySeq = data.reply_seq
            userSeq = data.user_seq
            modified = data.modified
        }

        fun bindOnItemClickListener(onItemClickListener: (View, Int, Int, Int, String) -> Unit ) {
            binding.imgReCommentMenu.setOnClickListener {
                onItemClickListener(it, replySeq!!, 1, userSeq!!, binding.tvCommentContent.text.toString())
            }
            binding.imgReCommentUser.setOnClickListener {
                onItemClickListener(it, replySeq!!, 2, userSeq!!, "")
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ReCommentItem>() {
            override fun areItemsTheSame(oldItem: ReCommentItem, newItem: ReCommentItem): Boolean {
                return oldItem.reply_seq == newItem.reply_seq
            }

            override fun areContentsTheSame(
                oldItem: ReCommentItem,
                newItem: ReCommentItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}