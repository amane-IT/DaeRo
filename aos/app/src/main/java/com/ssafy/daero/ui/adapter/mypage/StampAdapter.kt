package com.ssafy.daero.ui.adapter.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.badge.BadgeItem
import com.ssafy.daero.databinding.ItemStampBinding

class StampAdapter : RecyclerView.Adapter<StampAdapter.StampViewHolder>() {
    var badgeList: List<BadgeItem> = emptyList()
    lateinit var onItemClickListener: (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StampAdapter.StampViewHolder {
        return StampAdapter.StampViewHolder(
            ItemStampBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StampViewHolder, position: Int) {
        holder.bind(badgeList[position])
    }

    override fun getItemCount(): Int = if(badgeList.size >= 3) 3 else badgeList.size

    class StampViewHolder(private val binding: ItemStampBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(stamp: BadgeItem) {
            binding.stamps = stamp
        }
    }
}