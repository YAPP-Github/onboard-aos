package com.yapp.bol.presentation.view.home.rank.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ItemRankGamePaddingBinding

class UserRankGamePaddingViewHolder(
    private val binding: ItemRankGamePaddingBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        val lp = binding.root.layoutParams.apply {
            width = getPaddingSize()
        }
        binding.root.layoutParams = lp
    }

    private fun getDeviceWidth(): Int = binding.root.resources.displayMetrics.widthPixels

    private fun getPaddingSize(): Int {
        val gameLeftPaddingDp = binding.root.resources.getDimension(R.dimen.home_game_padding)
        val gameRightPaddingDp = binding.root.resources.getDimension(R.dimen.home_game_padding)
        val gameIVgDp = binding.root.resources.getDimension(R.dimen.home_game_image_size)
        val gameViewDp = gameLeftPaddingDp + gameIVgDp + gameRightPaddingDp
        return (getDeviceWidth() - gameViewDp.toInt()) / 2
    }

    companion object {
        fun create(parent: ViewGroup): UserRankGamePaddingViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemRankGamePaddingBinding.inflate(inflater, parent, false)
            return UserRankGamePaddingViewHolder(binding)
        }
    }
}
