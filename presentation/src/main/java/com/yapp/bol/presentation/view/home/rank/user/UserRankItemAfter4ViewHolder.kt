package com.yapp.bol.presentation.view.home.rank.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.domain.model.UserRankItem
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ItemRankAfter4Binding
import com.yapp.bol.presentation.utils.Converter.convertPlayCount
import com.yapp.bol.presentation.utils.Converter.convertScore

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
        userRankItem.apply {
            if (rank != null) {
                tvRank.text = rank.toString()
            } else { tvRank.text = "-" }
            tvName.text = name
            tvPlayCount.text = playCount.convertPlayCount()
            tvWinRate.text = score.convertScore()
            ivRecentUser.visibility = if (isChangeRecent) {
                View.VISIBLE
            } else { View.GONE }
        }
    }

    companion object {
        fun create(parent: ViewGroup): UserRankItemAfter4ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_rank_after_4, parent, false)
            val binding = ItemRankAfter4Binding.bind(view)
            return UserRankItemAfter4ViewHolder(binding)
        }
    }
}
