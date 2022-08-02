package com.ssafy.daero.ui.adapter.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.service.FAQResponseDto
import com.ssafy.daero.databinding.ItemFaqBinding

class FAQAdapter : RecyclerView.Adapter<FAQAdapter.FAQViewHolder>() {
    var faqs: List<FAQResponseDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
        return FAQViewHolder(
            ItemFaqBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            setOnClickListeners()
        }
    }

    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        holder.bind(faqs[position])
    }

    override fun getItemCount(): Int = faqs.size

    inner class FAQViewHolder(private val binding: ItemFaqBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(faq: FAQResponseDto) {
            binding.faq = faq
        }

        fun setOnClickListeners() {
            binding.root.setOnClickListener {
                faqs[bindingAdapterPosition].isShow = !faqs[bindingAdapterPosition].isShow
                notifyItemChanged(bindingAdapterPosition)
            }
        }
    }
}