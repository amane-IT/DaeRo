package com.ssafy.daero.ui.adapter.signup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.signup.TripPreferenceResponseDto
import com.ssafy.daero.databinding.ItemTripPreferenceBinding

class TripPreferenceAdapter: RecyclerView.Adapter<TripPreferenceAdapter.ItemViewHolder>() {
    var dataList: List<TripPreferenceResponseDto> = emptyList()
    lateinit var onItemClickListener : (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ItemViewHolder {
        return ItemViewHolder(
            ItemTripPreferenceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount() = dataList.size

    class ItemViewHolder(
        private val binding: ItemTripPreferenceBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(tripPreferenceResponseDto: TripPreferenceResponseDto) {
            binding.tripPreference = tripPreferenceResponseDto
        }

        fun bindOnItemClickListener(onItemClickListener: (View, Int) -> Unit) {
            binding.root.setOnClickListener{
                onItemClickListener(it, bindingAdapterPosition)
            }
        }
    }
}