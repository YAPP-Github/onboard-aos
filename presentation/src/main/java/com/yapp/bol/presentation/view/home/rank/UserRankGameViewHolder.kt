package com.yapp.bol.presentation.view.home.rank

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.domain.model.GameItem
import com.yapp.bol.presentation.databinding.ItemRankGameListBinding
import com.yapp.bol.presentation.utils.loadImage

class UserRankGameViewHolder(private val binding: ItemRankGameListBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(gameItem: GameItem) {
        binding.apply {
            binding.viewGame.tvGame.text = gameItem.name
            binding.viewGame.ivGame.loadImage(gameItem.img)
        }
    }

    companion object {
        fun create(parent: ViewGroup): UserRankGameViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemRankGameListBinding.inflate(inflater, parent, false)
            return UserRankGameViewHolder(binding)
        }
    }
}
