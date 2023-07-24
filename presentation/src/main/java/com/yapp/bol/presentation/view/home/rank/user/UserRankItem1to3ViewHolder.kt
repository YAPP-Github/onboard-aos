package com.yapp.bol.presentation.view.home.rank.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.domain.model.UserRankItem
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ItemRank1To3Binding
import com.yapp.bol.presentation.databinding.ViewRank1stBinding
import com.yapp.bol.presentation.databinding.ViewRank2ndBinding
import com.yapp.bol.presentation.databinding.ViewRank3rdBinding
import com.yapp.bol.presentation.utils.Converter.convertPlayCount
import com.yapp.bol.presentation.utils.Converter.convertWinRate

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

    private val USER_NOT_FULL = 2

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
            } else { tvRank.text = "-" }
            tvName.text = name
            tvPlayCount.text = playCount.convertPlayCount()
            tvWinRate.text = winRate.convertWinRate()
            ivRecentUser.visibility = if (isChangeRecent) {
                View.VISIBLE
            } else { View.GONE }
        }
    }

    private fun ViewRank2ndBinding.setItems(userRankItem: UserRankItem, rank: Ordinal) {
        userRankItem.apply {
            if (this.rank != null) {
                tvRank.text = rank.presentData
            } else { tvRank.text = "-" }
            tvName.text = name
            tvPlayCount.text = playCount.convertPlayCount()
            tvWinRate.text = winRate.convertWinRate()
            ivRecentUser.visibility = if (isChangeRecent) {
                View.VISIBLE
            } else { View.GONE }
        }
    }

    private fun ViewRank3rdBinding.setItems(userRankItem: UserRankItem?, rank: Ordinal) {
        userRankItem?.let {
            if (it.rank != null) {
                tvRank.text = rank.presentData
            } else { tvRank.text = "-" }
            tvName.text = it.name
            tvPlayCount.text = it.playCount.convertPlayCount()
            tvWinRate.text = it.winRate.convertWinRate()
            ivRecentUser.visibility = if (it.isChangeRecent) {
                View.VISIBLE
            } else { View.GONE }
        } ?: kotlin.run {
            tvRank.text = "-"
            tvName.text = "-"
            tvPlayCount.text = "-"
            tvWinRate.text = "-"
            ivRecentUser.visibility = View.GONE
        }
    }

    sealed class Ordinal(val index: Int, val presentData: String)
    object First : Ordinal(0, "1st")
    object Second : Ordinal(1, "2nd")
    object Third : Ordinal(2, "3rd")

    companion object {
        fun create(parent: ViewGroup): UserRankItem1to3ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_rank_1_to_3, parent, false)
            val binding = ItemRank1To3Binding.bind(view)
            return UserRankItem1to3ViewHolder(binding)
        }
    }
}
