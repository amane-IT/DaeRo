package com.ssafy.daero.ui.adapter.sns

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.article.LikeItem
import com.ssafy.daero.databinding.ItemLikeBinding

class LikeAdapter : PagingDataAdapter<LikeItem, LikeAdapter.LikeViewHolder>(
    COMPARATOR
) {
    lateinit var onItemClickListener: (Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeViewHolder {
        return LikeViewHolder(
            ItemLikeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: LikeViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }


    class LikeViewHolder(private val binding: ItemLikeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: LikeItem) {
            binding.user = user
        }

        fun bindOnItemClickListener(onItemClickListener: (Int) -> Unit) {
            binding.imgLikeItemUser.setOnClickListener {
                onItemClickListener(binding.user!!.user_seq)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<LikeItem>() {
            override fun areItemsTheSame(oldItem: LikeItem, newItem: LikeItem): Boolean {
                return oldItem.user_seq == newItem.user_seq
            }

            override fun areContentsTheSame(oldItem: LikeItem, newItem: LikeItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}