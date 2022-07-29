package com.ssafy.daero.ui.adapter.sns

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.data.dto.article.Record
import com.ssafy.daero.databinding.ItemArticleDayBinding

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    var articleData: List<Record> = emptyList()
    lateinit var onItemClickListener : (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ItemArticleDayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articleData[position]!!)
        Log.d("데이터",position.toString())
    }

    override fun getItemCount() = articleData.size

    class ArticleViewHolder(
        private val binding: ItemArticleDayBinding,
        onItemClickListener: (View, Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.recyclerArticleTripStamp.apply {
                adapter = TripStampAdapter(onItemClickListener)
                layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
            }
        }

        fun bind(data: Record) {
            binding.record = data
            binding.textArticleDay.text = "Day"+(bindingAdapterPosition+1).toString()
            (binding.recyclerArticleTripStamp.adapter as TripStampAdapter).apply {
                addTripStamp(data.trip_stamps)
            }
        }
    }
}