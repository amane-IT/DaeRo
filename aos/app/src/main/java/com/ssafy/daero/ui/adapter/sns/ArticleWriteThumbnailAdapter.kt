package com.ssafy.daero.ui.adapter.sns

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.R
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.databinding.ItemArticleWriteThumbnailBinding

class ArticleWriteThumbnailAdapter : RecyclerView.Adapter<ArticleWriteThumbnailAdapter.ArticleWriteThumbnailViewHolder>() {

    var articleTripStampData: List<TripStamp> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleWriteThumbnailViewHolder {
        return ArticleWriteThumbnailViewHolder(
            ItemArticleWriteThumbnailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleWriteThumbnailViewHolder, position: Int) {
        holder.bind(articleTripStampData[position]!!)
        Log.d("데이터",position.toString())
    }

    override fun getItemCount() = articleTripStampData.size

    class ArticleWriteThumbnailViewHolder(
        private val binding: ItemArticleWriteThumbnailBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TripStamp) {
            binding.tripStamp = data
            binding.imgArticleCheck.setOnClickListener {
                binding.imgArticleCheck.setImageResource(R.drawable.ic_check)
                binding.imgArticleCheck.setBackgroundResource(R.color.primaryLightColor)
            }
        }
    }
}