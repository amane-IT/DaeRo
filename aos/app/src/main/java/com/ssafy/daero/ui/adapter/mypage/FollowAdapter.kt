package com.ssafy.daero.ui.adapter.mypage

import android.graphics.Color
import android.os.Handler
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
import com.ssafy.daero.data.dto.user.FollowResponseDto
import com.ssafy.daero.databinding.ItemFollowBinding
import com.ssafy.daero.ui.adapter.sns.ReCommentAdapter
import com.ssafy.daero.ui.root.mypage.FollowListener

class FollowAdapter(listener: FollowListener
) : PagingDataAdapter<FollowResponseDto, FollowAdapter.FollowViewHolder>(COMPARATOR) {

    lateinit var onItemClickListener : (View, Int) -> Unit
    var mCallback = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        return FollowViewHolder(
            ItemFollowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            mCallback
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class FollowViewHolder(
        private val binding: ItemFollowBinding,
        private val mCallback: FollowListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var followYn: Boolean? = null
        private var userSeq: Int? = null

        fun bind(data: FollowResponseDto) {
            binding.follow = data
            binding.tvFollowUser.text = data.nickname
            followYn = data.follow_yn=='y'
            editFollow()
            userSeq = data.user_seq
        }
        fun bindOnItemClickListener(onItemClickListener: (View, Int) -> Unit) {
            binding.buttonFollow.setOnClickListener {
                if(followYn==true){
                    mCallback.unFollow(userSeq!!)
                }else{
                    mCallback.follow(userSeq!!)
                }
                followYn=!followYn!!
                editFollow()
            }
            binding.imgFollowUser.setOnClickListener {
                onItemClickListener(it,userSeq!!)
            }
            binding.tvFollowUser.setOnClickListener {
                onItemClickListener(it,userSeq!!)
            }
        }
        private fun editFollow(){
            if(followYn==true){
                binding.buttonFollow.setBackgroundResource(R.drawable.button_unfollow)
                binding.buttonFollow.text = "팔로잉"
                binding.buttonFollow.setTextColor(Color.GRAY)
            }else{
                binding.buttonFollow.setBackgroundResource(R.drawable.button_follow)
                binding.buttonFollow.text = "팔로우"
                binding.buttonFollow.setTextColor(Color.WHITE)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<FollowResponseDto>() {
            override fun areItemsTheSame(oldItem: FollowResponseDto, newItem: FollowResponseDto): Boolean {
                return oldItem.user_seq == newItem.user_seq
            }

            override fun areContentsTheSame(
                oldItem: FollowResponseDto,
                newItem: FollowResponseDto
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}