package com.yapp.bol.presentation.view.home.rank

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ItemRank1To3Binding
import com.yapp.bol.presentation.databinding.ItemRankAfter4Binding

class UserRankViewHolderFactory {

    private var userRankItem1to3ViewHolder: UserRankItem1to3ViewHolder? = null
    private val userRank1To3ViewType = R.layout.item_rank_1_to_3
    private val userRankAfter4ViewType = R.layout.item_rank_after_4

    fun createViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            userRank1To3ViewType -> {
                userRankItem1to3ViewHolder ?: run {
                    createUserRankItem1to3VH(parent)
                }
            }
            else -> {
                createUserRankAfter4VH(parent)
            }
        }

    private fun createUserRankItem1to3VH(parent: ViewGroup): UserRankItem1to3ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_rank_1_to_3, parent, false)
        val binding = ItemRank1To3Binding.bind(view)
        return UserRankItem1to3ViewHolder(binding)
    }

    private fun createUserRankAfter4VH(parent: ViewGroup): UserRankItemAfter4ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_rank_after_4, parent, false)
        val binding = ItemRankAfter4Binding.bind(view)
        return UserRankItemAfter4ViewHolder(binding)
    }
}
