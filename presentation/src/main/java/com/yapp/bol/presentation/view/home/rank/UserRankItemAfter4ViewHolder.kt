package com.yapp.bol.presentation.view.home.rank

import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.domain.model.UserRankItem
import com.yapp.bol.presentation.databinding.ItemRankAfter4Binding

class UserRankItemAfter4ViewHolder(
    private val binding: ItemRankAfter4Binding
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            // TODO : MVP 개발 단계일지 모르지만 각 유저 클릭 시 액션
        }
    }

    fun bind(userRankItem: UserRankItem) {
        binding.setItems(userRankItem)
    }

    private fun ItemRankAfter4Binding.setItems(userRankItem: UserRankItem) {
        tvRank.text = userRankItem.rank.toString()
        tvName.text = userRankItem.name
        tvPlayCount.text = userRankItem.playCount.toString()
        tvWinRate.text = userRankItem.winRate.toString()
    }
}
