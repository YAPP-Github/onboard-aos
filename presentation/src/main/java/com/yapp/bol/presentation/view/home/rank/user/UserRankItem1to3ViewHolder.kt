package com.yapp.bol.presentation.view.home.rank.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.domain.model.Role
import com.yapp.bol.domain.model.UserRankItem
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ItemRank1To3Binding
import com.yapp.bol.presentation.databinding.ViewRank1stBinding
import com.yapp.bol.presentation.databinding.ViewRank2ndBinding
import com.yapp.bol.presentation.databinding.ViewRank3rdBinding
import com.yapp.bol.presentation.model.HomeUserRankItem
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

    fun bind(itemList: List<HomeUserRankItem>) {

        if (itemList.size == USER_NOT_FULL) {
            binding.viewRank3.setItems(null, false, Second)
        }

        itemList.forEachIndexed { index, item ->
            when (index) {
                First.index -> binding.viewRank1.setItems(item.userRankItem, item.isMe, First)
                Second.index -> binding.viewRank2.setItems(item.userRankItem, item.isMe, Second)
                Third.index -> binding.viewRank3.setItems(item.userRankItem, item.isMe, Third)
            }
        }
    }

    private fun ViewRank1stBinding.setItems(userRankItem: UserRankItem, isMe: Boolean, rank: Ordinal) {
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
                imgDice.visibility = View.INVISIBLE
                imgDiceGuest.visibility = View.VISIBLE
            } else {
                imgDice.visibility = View.VISIBLE
                imgDiceGuest.visibility = View.INVISIBLE
            }
        }

        if (isMe) {
            viewMe.root.visibility = View.VISIBLE
        } else {
            viewMe.root.visibility = View.GONE
        }
    }

    private fun ViewRank2ndBinding.setItems(userRankItem: UserRankItem, isMe: Boolean, rank: Ordinal) {
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
                imgDice.visibility = View.INVISIBLE
                imgDiceGuest.visibility = View.VISIBLE
            } else {
                imgDice.visibility = View.VISIBLE
                imgDiceGuest.visibility = View.INVISIBLE
            }
        }

        if (isMe) {
            viewMe.root.visibility = View.VISIBLE
        } else {
            viewMe.root.visibility = View.GONE
        }
    }

    private fun ViewRank3rdBinding.setItems(userRankItem: UserRankItem?, isMe: Boolean = false, rank: Ordinal) {
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
                imgDice.visibility = View.INVISIBLE
                imgDiceGuest.visibility = View.VISIBLE
            } else {
                imgDice.visibility = View.VISIBLE
                imgDiceGuest.visibility = View.INVISIBLE
            }
        } ?: kotlin.run {
            tvRank.text = EMPTY_TEXT
            tvName.text = EMPTY_TEXT
            tvPlayCount.text = EMPTY_TEXT
            tvWinRate.text = EMPTY_TEXT
            ivRecentUser.visibility = View.GONE
        }

        if (isMe) {
            viewMe.root.visibility = View.VISIBLE
        } else {
            viewMe.root.visibility = View.GONE
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
