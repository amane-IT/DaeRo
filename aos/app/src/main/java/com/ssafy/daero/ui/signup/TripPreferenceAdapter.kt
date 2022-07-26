package com.ssafy.daero.ui.signup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.daero.R
import com.ssafy.daero.data.dto.signup.TripPreferenceResponseDto

class TripPreferenceAdapter(
    private val context: Context, private val dataList: List<TripPreferenceResponseDto>
): RecyclerView.Adapter<TripPreferenceAdapter.ItemViewHolder>() {
    var preferenceList = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item_trip_preference, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position], context)

        holder.itemView.setOnClickListener{ view ->
            if(preferenceList.contains(dataList[position].place_seq)){
                holder.itemView.setBackgroundResource(R.drawable.imagebutton_regular)
                preferenceList.remove(dataList[position].place_seq)
            } else{
                if(preferenceList.size < 5){
                    holder.itemView.setBackgroundResource(R.drawable.imagebutton_selected)
                    preferenceList.add(dataList[position].place_seq)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ItemViewHolder(imageView: View): RecyclerView.ViewHolder(imageView){
        private val placePhoto = itemView.findViewById<ImageView>(R.id.imageView_itemTripPreference_place)

        fun bind(tripPreferenceResponseDto: TripPreferenceResponseDto, context: Context) {
            if(tripPreferenceResponseDto.image_url.isNotEmpty()){
                Glide.with(context)
                    .load(tripPreferenceResponseDto.image_url)
                    .placeholder(R.drawable.ic_downloading)
                    .into(placePhoto)
            } else {
                placePhoto.setImageResource(R.drawable.ic_downloading)
            }
        }

    }
}