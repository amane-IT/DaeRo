package com.ssafy.daero.ui.adapter.sns

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.collection.CollectionItem
import com.ssafy.daero.databinding.ItemCollectionBinding

class CollectionAdapter(
    private val onUserClickListener: (Int) -> Unit, // 유저 프로필 선택
    private val onLikeClickListener: (Int, Int) -> Unit, // 좋아요 버튼 클릭
    private val onImageClickListener: (Int) -> Unit // 이미지 클릭
): PagingDataAdapter<CollectionItem, CollectionAdapter.CollectionViewHolder>(
    COMPARATOR
) {
    var idx = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionAdapter.CollectionViewHolder {
        return CollectionAdapter.CollectionViewHolder(
            ItemCollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnLikeClickListener(onLikeClickListener)
            bindOnUserClickListener(onUserClickListener)
            bindOnImageClickListener(onImageClickListener)
        }
    }

    fun getPosition(): Int{
        return idx
    }

    fun setPosition(position: Int) {
        idx = position
    }
    override fun onBindViewHolder(holder: CollectionAdapter.CollectionViewHolder, position: Int) {
        getItem(position)?.let{
            holder.bind(it)
            setPosition(position)
        }
    }

    class CollectionViewHolder(private val binding: ItemCollectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(collection : CollectionItem){
            binding.collection = collection
        }

        fun bindOnUserClickListener(onUserClickListener: (Int) -> Unit) {
            binding.imageItemCollectionUserImg.setOnClickListener {
                onUserClickListener(binding.collection?.user_seq ?: 0)
            }
        }

        fun bindOnLikeClickListener(onLikeClickListener: (Int, Int) -> Unit) {
            binding.imageItemCollectionLike.setOnClickListener {
                onLikeClickListener(binding.collection?.article_seq ?: 0, bindingAdapterPosition)
            }
        }

        fun bindOnImageClickListener(onImageClickListener: (Int) -> Unit) {
            binding.imageItemCollectionImg.setOnClickListener {
                onImageClickListener(binding.collection?.article_seq ?: 0)
            }
        }
    }

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<CollectionItem>(){
            override fun areItemsTheSame(oldItem: CollectionItem, newItem: CollectionItem): Boolean {
                return oldItem.user_seq == newItem.user_seq
            }

            override fun areContentsTheSame(
                oldItem: CollectionItem,
                newItem: CollectionItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}