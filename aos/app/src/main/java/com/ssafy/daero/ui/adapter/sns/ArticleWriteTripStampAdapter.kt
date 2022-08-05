package com.ssafy.daero.ui.adapter.sns

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.entity.TripStamp
import com.ssafy.daero.databinding.ItemArticleWriteDayTripStampBinding

class ArticleWriteTripStampAdapter : RecyclerView.Adapter<ArticleWriteTripStampAdapter.ArticleWriteTripStampViewHolder>() {

    var articleTripStampData: List<TripStamp> = emptyList()
    lateinit var onItemClickListener : (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleWriteTripStampViewHolder {
        return ArticleWriteTripStampViewHolder(
            ItemArticleWriteDayTripStampBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ArticleWriteTripStampViewHolder, position: Int) {
        holder.bind(articleTripStampData[position]!!)
        Log.d("데이터",position.toString())
    }

    override fun getItemCount() = articleTripStampData.size

    class ArticleWriteTripStampViewHolder(
        private val binding: ItemArticleWriteDayTripStampBinding,
        onItemClickListener: (View, Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TripStamp) {
            binding.tripStamp = data
        }
    }
}