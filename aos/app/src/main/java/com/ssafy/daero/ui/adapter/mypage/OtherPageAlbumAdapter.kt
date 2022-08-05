package com.ssafy.daero.ui.adapter.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.trip.TripAlbumItem
import com.ssafy.daero.databinding.ItemOtherPageAlbumBinding

class OtherPageAlbumAdapter :
    PagingDataAdapter<TripAlbumItem, OtherPageAlbumAdapter.OtherPageAlbumViewHolder>(
        COMPARATOR
    ) {
    lateinit var onItemClickListener: (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherPageAlbumViewHolder {
        return OtherPageAlbumViewHolder(
            ItemOtherPageAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: OtherPageAlbumViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class OtherPageAlbumViewHolder(private val binding: ItemOtherPageAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(album: TripAlbumItem) {
            binding.album = album
        }

        fun bindOnItemClickListener(onItemClickListener: (View, Int) -> Unit) {
            binding.root.setOnClickListener {
                onItemClickListener(it, binding.album!!.trip_seq)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<TripAlbumItem>() {
            override fun areItemsTheSame(oldItem: TripAlbumItem, newItem: TripAlbumItem): Boolean {
                return oldItem.trip_seq == newItem.trip_seq
            }

            override fun areContentsTheSame(
                oldItem: TripAlbumItem,
                newItem: TripAlbumItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}