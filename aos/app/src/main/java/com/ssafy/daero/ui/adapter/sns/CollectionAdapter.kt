package com.ssafy.daero.ui.adapter.sns

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.collection.CollectionItem
import com.ssafy.daero.databinding.ItemCollectionBinding

class CollectionAdapter: PagingDataAdapter<CollectionItem, CollectionAdapter.CollectionViewHolder>(
    COMPARATOR
) {
    lateinit var onItemClickListener: (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionAdapter.CollectionViewHolder {
        return CollectionAdapter.CollectionViewHolder(
            ItemCollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: CollectionAdapter.CollectionViewHolder, position: Int) {
        getItem(position)?.let{
            holder.bind(it)
        }
    }

    class CollectionViewHolder(private val binding: ItemCollectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(collection : CollectionItem){
            binding.collection = collection
        }

        fun bindOnItemClickListener(onItemClickListener: (View, Int) -> Unit){
            binding.root.setOnClickListener{
                onItemClickListener(it, binding.collection!!.article_seq)
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