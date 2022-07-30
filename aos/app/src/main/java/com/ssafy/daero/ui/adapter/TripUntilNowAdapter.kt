package com.ssafy.daero.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.trip.TripPopularResponseDto
import com.ssafy.daero.databinding.ItemTripPopularBinding

class TripUntilNowAdapter : RecyclerView.Adapter<TripUntilNowAdapter.TripUntilNowViewAdapter>() {
    var tripPlaces: List<TripPopularResponseDto> = emptyList()
    lateinit var onItemClickListener: (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripUntilNowAdapter.TripUntilNowViewAdapter {
        return TripUntilNowAdapter.TripUntilNowViewAdapter(
            ItemTripPopularBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: TripUntilNowAdapter.TripUntilNowViewAdapter, position: Int) {
        holder.bind(tripPlaces[position])
    }

    override fun getItemCount(): Int = tripPlaces.size

    class TripUntilNowViewAdapter(private val binding: ItemTripPopularBinding) :
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