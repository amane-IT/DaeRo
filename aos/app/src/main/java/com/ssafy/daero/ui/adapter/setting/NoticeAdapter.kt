package com.ssafy.daero.ui.adapter.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.service.NoticeResponseDto
import com.ssafy.daero.databinding.ItemNoticeBinding

class NoticeAdapter : RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>() {
    var notices: List<NoticeResponseDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        return NoticeViewHolder(
            ItemNoticeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            setOnClickListeners()
        }
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(notices[position])
    }

    override fun getItemCount(): Int = notices.size

    inner class NoticeViewHolder(private val binding: ItemNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notice: NoticeResponseDto) {
            binding.notice = notice
        }

        fun setOnClickListeners() {
            binding.root.setOnClickListener {
                notices[bindingAdapterPosition].isShow = !notices[bindingAdapterPosition].isShow
                notifyItemChanged(bindingAdapterPosition)
            }
        }
    }
}