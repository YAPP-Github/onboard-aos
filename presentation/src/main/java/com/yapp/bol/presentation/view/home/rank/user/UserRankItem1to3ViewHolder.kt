package com.yapp.bol.presentation.view.home.rank.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.domain.model.Role
import com.yapp.bol.domain.model.UserRankItem
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ItemRank1To3Binding
import com.yapp.bol.presentation.databinding.ViewRank1stBinding
import com.yapp.bol.presentation.databinding.ViewRank2ndBinding
import com.yapp.bol.presentation.databinding.ViewRank3rdBinding
import com.yapp.bol.presentation.utils.Converter.convertPlayCount
import com.yapp.bol.presentation.utils.Converter.convertScore

class UserRankItem1to3ViewHolder(
    private val binding: ItemRank1To3Binding
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.viewRank1.root.setOnClickListener {
            // TODO : MVP 개발 단계일지 모르지만 각 유저 클릭 시 액션
        }
        binding.viewRank2.root.setOnClickListener {
            // TODO : MVP 개발 단계일지 모르지만 각 유저 클릭 시 액션
        }
        binding.viewRank3.root.setOnClickListener {
            // TODO : MVP 개발 단계일지 모르지만 각 유저 클릭 시 액션
        }
    }

    fun bind(userRankItemList: List<UserRankItem>) {

        if (userRankItemList.size == USER_NOT_FULL) {
            binding.viewRank3.setItems(null, Second)
        }

        userRankItemList.forEachIndexed { index, userRankItem ->
            when (index) {
                First.index -> binding.viewRank1.setItems(userRankItem, First)
                Second.index -> binding.viewRank2.setItems(userRankItem, Second)
                Third.index -> binding.viewRank3.setItems(userRankItem, Third)
            }
        }
    }

    private fun ViewRank1stBinding.setItems(userRankItem: UserRankItem, rank: Ordinal) {
        userRankItem.apply {
            if (this.rank != null) {
                tvRank.text = rank.presentData
            } else { tvRank.text = EMPTY_TEXT }
            tvName.text = name
            tvPlayCount.text = playCount.convertPlayCount()
            tvWinRate.text = score.convertScore()
            ivRecentUser.visibility = if (isChangeRecent) {
                View.VISIBLE
            } else { View.GONE }
            if (role is Role.GUEST) {
                imgDice.setImageDrawable(AppCompatResources.getDrawable(root.context, R.drawable.img_dice_empty_small))
                imgDice.scaleType = ImageView.ScaleType.CENTER_INSIDE
            } else {
                imgDice.setImageDrawable(
                    AppCompatResources.getDrawable(root.context, R.drawable.img_dice)
                )
            }
        }
    }

    private fun ViewRank2ndBinding.setItems(userRankItem: UserRankItem, rank: Ordinal) {
        userRankItem.apply {
            if (this.rank != null) {
                tvRank.text = rank.presentData
            } else { tvRank.text = "-" }
            tvName.text = name
            tvPlayCount.text = playCount.convertPlayCount()
            tvWinRate.text = score.convertScore()
            ivRecentUser.visibility = if (isChangeRecent) {
                View.VISIBLE
            } else { View.GONE }
            if (role is Role.GUEST) {
                imgDice.setImageDrawable(AppCompatResources.getDrawable(root.context, R.drawable.img_dice_empty_small))
                imgDice.scaleType = ImageView.ScaleType.CENTER_INSIDE
            } else {
                imgDice.setImageDrawable(AppCompatResources.getDrawable(root.context, R.drawable.img_dice))
            }
        }
    }

    private fun ViewRank3rdBinding.setItems(userRankItem: UserRankItem?, rank: Ordinal) {
        userRankItem?.let {
            if (it.rank != null) {
                tvRank.text = rank.presentData
            } else { tvRank.text = EMPTY_TEXT }
            tvName.text = it.name
            tvPlayCount.text = it.playCount.convertPlayCount()
            tvWinRate.text = it.score.convertScore()
            ivRecentUser.visibility = if (it.isChangeRecent) {
                View.VISIBLE
            } else { View.GONE }
            if (it.role is Role.GUEST) {
                imgDice.setImageDrawable(AppCompatResources.getDrawable(root.context, R.drawable.img_dice_empty_small))
                imgDice.scaleType = ImageView.ScaleType.CENTER_INSIDE
            } else {
                imgDice.setImageDrawable(
                    AppCompatResources.getDrawable(root.context, R.drawable.img_dice)
                )
            }
        } ?: kotlin.run {
            tvRank.text = EMPTY_TEXT
            tvName.text = EMPTY_TEXT
            tvPlayCount.text = EMPTY_TEXT
            tvWinRate.text = EMPTY_TEXT
            ivRecentUser.visibility = View.GONE
        }
    }

    sealed class Ordinal(val index: Int, val presentData: String)
    object First : Ordinal(0, "1st")
    object Second : Ordinal(1, "2nd")
    object Third : Ordinal(2, "3rd")

    companion object {

        private const val USER_NOT_FULL = 2
        private const val EMPTY_TEXT = "-"

        fun create(parent: ViewGroup): UserRankItem1to3ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_rank_1_to_3, parent, false)
            val binding = ItemRank1To3Binding.bind(view)
            return UserRankItem1to3ViewHolder(binding)
        }
    }
}
