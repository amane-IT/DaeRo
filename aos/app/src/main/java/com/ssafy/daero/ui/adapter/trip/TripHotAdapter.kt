package com.ssafy.daero.ui.adapter.trip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.trip.TripHotResponseDto
import com.ssafy.daero.databinding.ItemTripHotBinding

class TripHotAdapter : RecyclerView.Adapter<TripHotAdapter.TripHotViewHolder>() {
    var tripHots: List<TripHotResponseDto> = emptyList()
    lateinit var onItemClickListener: (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripHotViewHolder {
        return TripHotViewHolder(
            ItemTripHotBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: TripHotViewHolder, position: Int) {
        holder.bind(tripHots[position])
    }

    override fun getItemCount(): Int = tripHots.size

    class TripHotViewHolder(private val binding: ItemTripHotBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tripPlace: TripHotResponseDto) {
            binding.tripHot = tripPlace
        }

        fun bindOnItemClickListener(onItemClickListener: (View, Int) -> Unit) {
            binding.root.setOnClickListener {
                onItemClickListener(it, binding.tripHot!!.article_seq)
            }
        }
    }
}