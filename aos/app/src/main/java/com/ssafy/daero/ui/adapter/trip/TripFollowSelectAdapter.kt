package com.ssafy.daero.ui.adapter.trip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.trip.TripFollowSelectResponseDto
import com.ssafy.daero.databinding.ItemTripFollowSelectBinding

class TripFollowSelectAdapter : RecyclerView.Adapter<TripFollowSelectAdapter.TripFollowSelectViewHolder>() {

    var tripFollowSelectResponseDto: List<TripFollowSelectResponseDto> = emptyList()
    lateinit var onItemClickListener: (View, Int, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripFollowSelectViewHolder {
        return TripFollowSelectViewHolder(
            ItemTripFollowSelectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: TripFollowSelectViewHolder, position: Int) {
        holder.bind(tripFollowSelectResponseDto[position])
    }

    override fun getItemCount() = tripFollowSelectResponseDto.size

    class TripFollowSelectViewHolder(private val binding: ItemTripFollowSelectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TripFollowSelectResponseDto) {
            binding.tripFollowSelect = data
        }

        fun bindOnItemClickListener(onItemClickListener: (View, Int, Int) -> Unit ) {
            binding.root.setOnClickListener {
                onItemClickListener(it, binding.tripFollowSelect!!.trip_place_seq, bindingAdapterPosition)
            }
        }
    }
}