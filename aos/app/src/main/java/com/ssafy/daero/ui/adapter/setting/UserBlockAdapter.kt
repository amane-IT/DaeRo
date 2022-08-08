package com.ssafy.daero.ui.adapter.setting

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.daero.R
import com.ssafy.daero.data.dto.user.UserBlockResponseDto
import com.ssafy.daero.databinding.ItemUserBlockBinding
import com.ssafy.daero.ui.setting.BlockUserListener

class UserBlockAdapter(listener: BlockUserListener) : RecyclerView.Adapter<UserBlockAdapter.UserBlockViewHolder>() {

    var userBlockResponseDto: List<UserBlockResponseDto> = emptyList()
    lateinit var onItemClickListener: (View, Int) -> Unit
    var mCallback = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserBlockViewHolder {
        return UserBlockViewHolder(
            ItemUserBlockBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            mCallback
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: UserBlockViewHolder, position: Int) {
        holder.bind(userBlockResponseDto[position])
    }

    override fun getItemCount() = userBlockResponseDto.size

    class UserBlockViewHolder(private val binding: ItemUserBlockBinding, private val mCallback: BlockUserListener) :
        RecyclerView.ViewHolder(binding.root) {

        private var followYn: Boolean = true
        private var userSeq: Int? = null

        fun bind(data: UserBlockResponseDto) {
            binding.userBlack = data
            binding.tvUserBlock.text = data.nickname
            editFollow()
            userSeq = data.user_seq
        }
        fun bindOnItemClickListener(onItemClickListener: (View, Int) -> Unit) {
            binding.buttonFollow.setOnClickListener {
                if(followYn){
                    mCallback.blockDelete(userSeq!!)
                }else{
                    mCallback.blockAdd(userSeq!!)
                }
                followYn=!followYn
                editFollow()
            }
            binding.imgUserBlock.setOnClickListener {
                onItemClickListener(it,userSeq!!)
            }
            binding.tvUserBlock.setOnClickListener {
                onItemClickListener(it,userSeq!!)
            }
        }
        private fun editFollow(){
            if(followYn){
                binding.buttonFollow.setBackgroundResource(R.drawable.button_unfollow)
                binding.buttonFollow.text = "차단 해제"
                binding.buttonFollow.setTextColor(Color.WHITE)
            }else{
                binding.buttonFollow.setBackgroundResource(R.drawable.button_follow)
                binding.buttonFollow.text = "차단하기"
                binding.buttonFollow.setTextColor(Color.WHITE)
            }
        }
    }
}