package com.ssafy.daero.ui.adapter.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.badge.BadgeItem
import com.ssafy.daero.databinding.ItemStampBinding

class StampDetailAdapter : RecyclerView.Adapter<StampDetailAdapter.StampDetailViewHolder>() {
    var badgeList: List<BadgeItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StampDetailAdapter.StampDetailViewHolder {
        return StampDetailAdapter.StampDetailViewHolder(
            ItemStampBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StampDetailViewHolder, position: Int) {
        holder.bind(badgeList[position])
    }

    override fun getItemCount(): Int = badgeList.size

    class StampDetailViewHolder(private val binding: ItemStampBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(stamp: BadgeItem) {
            binding.stamps = stamp
        }
    }
}