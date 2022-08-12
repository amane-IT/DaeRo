package com.ssafy.daero.ui.adapter.sns

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.databinding.ItemArticleWriteDayTripStampBinding
import com.ssafy.daero.ui.root.sns.TripStampDto

class ArticleWriteTripStampAdapter :
    RecyclerView.Adapter<ArticleWriteTripStampAdapter.ArticleWriteTripStampViewHolder>() {

    var tripStamps: List<TripStampDto> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleWriteTripStampViewHolder {
        return ArticleWriteTripStampViewHolder(
            ItemArticleWriteDayTripStampBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleWriteTripStampViewHolder, position: Int) {
        holder.bind(tripStamps[position]!!)
    }

    override fun getItemCount() = tripStamps.size

    class ArticleWriteTripStampViewHolder(
        private val binding: ItemArticleWriteDayTripStampBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TripStampDto) {
            binding.tripStamp = data
        }
    }
}