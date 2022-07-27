package com.ssafy.daero.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.trip.TripAlbumResponseDto
import com.ssafy.daero.databinding.ItemMyPageAlbumBinding

class MyPageAlbumAdapter : RecyclerView.Adapter<MyPageAlbumAdapter.MyPageAlbumViewHolder>() {
    var albums: List<TripAlbumResponseDto> = emptyList()
    lateinit var onItemClickListener: (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageAlbumViewHolder {
        return MyPageAlbumViewHolder(
            ItemMyPageAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: MyPageAlbumViewHolder, position: Int) {
        holder.bind(albums[position])
    }

    override fun getItemCount(): Int = albums.size

    class MyPageAlbumViewHolder(private val binding: ItemMyPageAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(album: TripAlbumResponseDto) {
            binding.album = album
        }

        fun bindOnItemClickListener(onItemClickListener: (View, Int) -> Unit) {
            binding.root.setOnClickListener {
                onItemClickListener(it, binding.album!!.trip_seq)
            }
        }
    }
}