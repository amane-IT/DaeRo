package com.ssafy.daero.ui.adapter.sns

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.article.TripStamp
import com.ssafy.daero.databinding.ItemArticleDayTripStampBinding

class TripStampAdapter(onItemClickListener : (View, Int) -> Unit) : RecyclerView.Adapter<TripStampAdapter.TripStampViewHolder>() {

    var tripStamp: List<TripStamp> = emptyList()
    var onItemClickListener = onItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripStampViewHolder {
        return TripStampViewHolder(
            ItemArticleDayTripStampBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: TripStampViewHolder, position: Int) {
        holder.bind(tripStamp[position])
    }

    override fun getItemCount() = tripStamp.size

    fun addTripStamp(dataList: List<TripStamp>) {
        tripStamp = dataList
        notifyDataSetChanged()
    }

    class TripStampViewHolder(private val binding: ItemArticleDayTripStampBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TripStamp) {
            binding.tripStamp = data
        }

        fun bindOnItemClickListener(onItemClickListener: (View, Int) -> Unit ) {
            binding.root.setOnClickListener {
                onItemClickListener(it, binding.tripStamp!!.trip_stamp_seq)
            }
        }
    }
}