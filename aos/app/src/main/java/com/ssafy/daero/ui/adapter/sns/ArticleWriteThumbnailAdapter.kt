package com.ssafy.daero.ui.adapter.sns

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.R
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.databinding.ItemArticleWriteThumbnailBinding
import com.ssafy.daero.ui.root.sns.TripStampDto

class ArticleWriteThumbnailAdapter(private val itemCheckListener: (Int) -> Unit) :
    RecyclerView.Adapter<ArticleWriteThumbnailAdapter.ArticleWriteThumbnailViewHolder>() {

    var tripStamps: List<TripStampDto> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleWriteThumbnailViewHolder {
        return ArticleWriteThumbnailViewHolder(
            ItemArticleWriteThumbnailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindClickListener(itemCheckListener)
        }
    }

    override fun onBindViewHolder(holder: ArticleWriteThumbnailViewHolder, position: Int) {
        holder.bind(tripStamps[position])
    }

    override fun getItemCount() = tripStamps.size

    class ArticleWriteThumbnailViewHolder(
        private val binding: ItemArticleWriteThumbnailBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TripStampDto) {
            binding.tripStamp = data
        }

        fun bindClickListener(itemCheckListener: (Int) -> Unit) {
            binding.root.setOnClickListener {
                itemCheckListener(bindingAdapterPosition)
            }
        }
    }
}