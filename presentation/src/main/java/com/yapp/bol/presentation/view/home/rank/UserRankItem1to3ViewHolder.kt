package com.yapp.bol.presentation.view.home.rank

import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.domain.model.UserRankItem
import com.yapp.bol.presentation.databinding.ItemRank1To3Binding
import com.yapp.bol.presentation.databinding.ViewRank1stBinding
import com.yapp.bol.presentation.databinding.ViewRank2ndBinding
import com.yapp.bol.presentation.databinding.ViewRank3rdBinding

class UserRankItem1to3ViewHolder(
    private val binding: ItemRank1To3Binding
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.viewRank1.root.setOnClickListener { }
        binding.viewRank2.root.setOnClickListener { }
        binding.viewRank3.root.setOnClickListener { }
    }

    fun bind(userRankItem: UserRankItem) {
        when (userRankItem.rank) {
            First.data -> binding.viewRank1.setItems(userRankItem, First)
            Second.data -> binding.viewRank2.setItems(userRankItem, Second)
            else -> binding.viewRank3.setItems(userRankItem, Third)
        }
    }

    private fun ViewRank1stBinding.setItems(userRankItem: UserRankItem, rank: Ordinal) {
        tvRank.text = rank.presentData
        tvName.text = userRankItem.name
        tvPlayCount.text = userRankItem.playCount.toString()
        tvWinRate.text = userRankItem.winRate.toString()
    }

    private fun ViewRank2ndBinding.setItems(userRankItem: UserRankItem, rank: Ordinal) {
        tvRank.text = rank.presentData
        tvName.text = userRankItem.name
        tvPlayCount.text = userRankItem.playCount.toString()
        tvWinRate.text = userRankItem.winRate.toString()
    }

    private fun ViewRank3rdBinding.setItems(userRankItem: UserRankItem, rank: Ordinal) {
        tvRank.text = rank.presentData
        tvName.text = userRankItem.name
        tvPlayCount.text = userRankItem.playCount.toString()
        tvWinRate.text = userRankItem.winRate.toString()
    }

    companion object {
        sealed class Ordinal(val data: Int, val presentData: String)
        object First : Ordinal(1, "1st")
        object Second : Ordinal(2, "2nd")
        object Third : Ordinal(3, "3rd")
    }
}
