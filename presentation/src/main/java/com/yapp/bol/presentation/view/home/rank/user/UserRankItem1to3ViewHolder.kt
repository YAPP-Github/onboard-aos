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

    fun bind(userRankItemList: List<UserRankItem>) {
        userRankItemList.map {
            when (it.rank) {
                First.data -> binding.viewRank1.setItems(it, First)
                Second.data -> binding.viewRank2.setItems(it, Second)
                Third.data -> binding.viewRank3.setItems(it, Third)
                else -> binding.viewRank1.setItems(it, First)
            }
        }
    }

    private fun ViewRank1stBinding.setItems(userRankItem: UserRankItem, rank: Ordinal) {
        tvRank.text = rank.presentData
        userRankItem.apply {
            tvName.text = name
            tvPlayCount.text = playCount.convertPlayCount()
            tvWinRate.text = winRate.convertWinRate()
            ivRecentUser.visibility = if (isChangeRecent) {
                View.VISIBLE
            } else { View.GONE }
        }
    }

    private fun ViewRank2ndBinding.setItems(userRankItem: UserRankItem, rank: Ordinal) {
        tvRank.text = rank.presentData
        userRankItem.apply {
            tvName.text = name
            tvPlayCount.text = playCount.convertPlayCount()
            tvWinRate.text = winRate.convertWinRate()
            ivRecentUser.visibility = if (isChangeRecent) {
                View.VISIBLE
            } else { View.GONE }
        }
    }

    private fun ViewRank3rdBinding.setItems(userRankItem: UserRankItem, rank: Ordinal) {
        tvRank.text = rank.presentData
        userRankItem.apply {
            tvName.text = name
            tvPlayCount.text = playCount.convertPlayCount()
            tvWinRate.text = winRate.convertWinRate()
            ivRecentUser.visibility = if (isChangeRecent) {
                View.VISIBLE
            } else { View.GONE }
        }
    }

    companion object {
        sealed class Ordinal(val data: Int, val presentData: String)
        object First : Ordinal(1, "1st")
        object Second : Ordinal(2, "2nd")
        object Third : Ordinal(3, "3rd")

        fun create(parent: ViewGroup): UserRankItem1to3ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_rank_1_to_3, parent, false)
            val binding = ItemRank1To3Binding.bind(view)
            return UserRankItem1to3ViewHolder(binding)
        }
    }
}
