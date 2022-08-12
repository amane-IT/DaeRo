package com.ssafy.daero.ui.adapter.sns

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.article.TripStamp
import com.ssafy.daero.databinding.ItemArticleEditDayTripStampBinding

class ArticleEditTripStampAdapter :
    RecyclerView.Adapter<ArticleEditTripStampAdapter.ArticleEditTripStampViewHolder>() {

    var tripStamps: List<TripStamp> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleEditTripStampViewHolder {
        return ArticleEditTripStampViewHolder(
            ItemArticleEditDayTripStampBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleEditTripStampViewHolder, position: Int) {
        holder.bind(tripStamps[position])
    }

    override fun getItemCount() = tripStamps.size

    class ArticleEditTripStampViewHolder(
        private val binding: ItemArticleEditDayTripStampBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TripStamp) {
            binding.tripStamp = data
        }
    }
}