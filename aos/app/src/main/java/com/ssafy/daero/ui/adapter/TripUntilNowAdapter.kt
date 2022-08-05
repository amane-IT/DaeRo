package com.ssafy.daero.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.databinding.ItemUntilTripstampBinding

class TripUntilNowAdapter : RecyclerView.Adapter<TripUntilNowAdapter.TripUntilNowViewAdapter>() {
    var tripStamps: List<TripStamp> = emptyList()
    lateinit var onItemClickListener: (View, Int) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TripUntilNowAdapter.TripUntilNowViewAdapter {
        return TripUntilNowAdapter.TripUntilNowViewAdapter(
            ItemUntilTripstampBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(
        holder: TripUntilNowAdapter.TripUntilNowViewAdapter,
        position: Int
    ) {
        holder.bind(tripStamps[position])
    }

    override fun getItemCount(): Int = tripStamps.size

    class TripUntilNowViewAdapter(private val binding: ItemUntilTripstampBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tripStamp: TripStamp) {
            binding.tripStamp = tripStamp
        }

        fun bindOnItemClickListener(onItemClickListener: (View, Int) -> Unit) {
            binding.root.setOnClickListener {
                onItemClickListener(it, binding.tripStamp?.id ?: 0)
            }
        }
    }
}