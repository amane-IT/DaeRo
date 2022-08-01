package com.ssafy.daero.ui.adapter.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.sns.UserNameItem
import com.ssafy.daero.data.repository.paging.SearchUserNameDataSource
import com.ssafy.daero.databinding.ItemSearchUserBinding
import com.ssafy.daero.ui.root.search.SearchViewModel

class SearchUserNameAdapter : PagingDataAdapter<UserNameItem, SearchUserNameAdapter.SearchUserNameViewHolder>(
    COMPARATOR
) {
    lateinit var onItemClickListener: (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserNameViewHolder{
        return SearchUserNameViewHolder(
            ItemSearchUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: SearchUserNameViewHolder, position: Int) {
        getItem(position)?.let{
            holder.bind(it)
        }
    }

    class SearchUserNameViewHolder(private val binding: ItemSearchUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(users : UserNameItem){
            binding.users = users
        }

        fun bindOnItemClickListener(onItemClickListener: (View, Int) -> Unit){
            binding.root.setOnClickListener{
                onItemClickListener(it, binding.users!!.user_seq)
            }
        }
    }

    companion object{
        private val COMPARATOR = object :DiffUtil.ItemCallback<UserNameItem>(){
            override fun areItemsTheSame(oldItem: UserNameItem, newItem: UserNameItem): Boolean {
                return oldItem.user_seq == newItem.user_seq
            }

            override fun areContentsTheSame(
                oldItem: UserNameItem,
                newItem: UserNameItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}