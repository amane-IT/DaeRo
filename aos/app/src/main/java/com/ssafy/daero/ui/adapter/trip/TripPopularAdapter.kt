package com.ssafy.daero.ui.adapter.trip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.trip.TripPopularResponseDto
import com.ssafy.daero.databinding.ItemTripPopularBinding

class TripPopularAdapter : RecyclerView.Adapter<TripPopularAdapter.TripPopularViewHolder>() {
    var tripPlaces: List<TripPopularResponseDto> = emptyList()
    lateinit var onItemClickListener: (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripPopularViewHolder {
        return TripPopularViewHolder(
            ItemTripPopularBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: TripPopularViewHolder, position: Int) {
        holder.bind(tripPlaces[position])
    }

    override fun getItemCount(): Int = tripPlaces.size

    class TripPopularViewHolder(private val binding: ItemTripPopularBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tripPlace: TripPopularResponseDto) {
            binding.tripPlace = tripPlace
        }

        fun bindOnItemClickListener(onItemClickListener: (View, Int) -> Unit) {
            binding.root.setOnClickListener {
                onItemClickListener(it, binding.tripPlace!!.trip_place_seq)
            }
        }
    }
}