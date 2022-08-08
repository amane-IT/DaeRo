package com.ssafy.daero.ui.adapter.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.service.InquiryResponseDto
import com.ssafy.daero.databinding.ItemInquiryBinding

class InquiryAdapter : RecyclerView.Adapter<InquiryAdapter.InquiryViewHolder>() {
    var inquiry: List<InquiryResponseDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InquiryViewHolder {
        return InquiryViewHolder(
            ItemInquiryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            setOnClickListeners()
        }
    }

    override fun onBindViewHolder(holder: InquiryViewHolder, position: Int) {
        holder.bind(inquiry[position])
    }

    override fun getItemCount(): Int = inquiry.size

    inner class InquiryViewHolder(private val binding: ItemInquiryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(inquiry: InquiryResponseDto) {
            binding.inquiry = inquiry
            if(inquiry.answer_yn.equals('y')){
                binding.textInquiryAnswer.text = "답변완료"
            }else{
                binding.textInquiryAnswer.text = ""
            }

        }

        fun setOnClickListeners() {
            binding.root.setOnClickListener {
                inquiry[bindingAdapterPosition].isShow = !inquiry[bindingAdapterPosition].isShow
                notifyItemChanged(bindingAdapterPosition)
            }
        }
    }
}