package com.ssafy.daero.ui.adapter.sns

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ssafy.daero.R
import com.ssafy.daero.data.dto.article.CommentResponseDto
import com.ssafy.daero.databinding.ItemCommentBinding
import com.ssafy.daero.ui.root.sns.CommentViewModel

class CommentAdapter(private val articleSeq: Int, private val commentViewModel: CommentViewModel) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    var commentData: List<CommentResponseDto> = emptyList()
    lateinit var onItemClickListener : (View, Int, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),commentViewModel,articleSeq
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(commentData[position]!!)
        Log.d("데이터",position.toString())
    }

    override fun getItemCount() = commentData.size

    class CommentViewHolder(
        private val binding: ItemCommentBinding,
        private val commentViewModel: CommentViewModel,
        private val articleSeq: Int
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var userSeq: Int? = null
        private var replySeq: Int? = null
        private var modified: Char? = null
        private var replyCount: Int? = null

        fun bind(data: CommentResponseDto) {
            Glide.with(binding.imgCommentItemUser)
                .load(data.profile_url)
                .placeholder(R.drawable.ic_back)
                .apply(RequestOptions().centerCrop())
                .error(R.drawable.ic_back)
                .into(binding.imgCommentItemUser)

             binding.tvCommentUser.text = data.nickname
            binding.tvCommentCreateAt.text = data.created_at
            binding.tvCommentContent.text = data.content
            if(data.rereply_count > 0){
                binding.tvCommentCount.text = data.rereply_count.toString()
                binding.LinearCommentReComment.visibility = View.VISIBLE
                replyCount = data.rereply_count
            }
            replySeq = data.reply_seq
            userSeq = data.user_seq
            modified = data.modified
        }
        fun bindOnItemClickListener(onItemClickListener: (View, Int, Int) -> Unit) {
            if(replyCount!! > 0) {
                binding.LinearCommentReComment.setOnClickListener {
                    commentViewModel.reCommentSelect(articleSeq,replySeq!!,10)
                    binding.LinearCommentReComment.visibility = View.GONE
                    binding.progressBarCommentLoading.visibility = View.VISIBLE
                    binding.recyclerCommentReComment.visibility = View.VISIBLE
                    binding.recyclerCommentReComment.apply {
                        adapter = ReCommentAdapter(onItemClickListener).apply {
                            this.reComment = commentViewModel.reCommentData
                        }
                        layoutManager = LinearLayoutManager(
                            binding.root.context,
                            RecyclerView.HORIZONTAL,
                            false
                        )
                        binding.progressBarCommentLoading.visibility = View.GONE
                    }
                }
            }
            binding.imgCommentMenu.setOnClickListener {
                onItemClickListener(it, replySeq!!, 1)
            }
            binding.imgCommentItemUser.setOnClickListener {
                onItemClickListener(it, replySeq!!, 2)
            }
        }
    }
}