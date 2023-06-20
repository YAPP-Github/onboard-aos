package com.yapp.bol.presentation.view.match.game_result

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.RvMemberMatchItemBinding
import com.yapp.bol.presentation.databinding.RvMemberMatchRankItemBinding
import com.yapp.bol.presentation.model.MemberItem

class GameResultRankAdapter(
    private val context: Context,
    private val listSize: Int,
) : RecyclerView.Adapter<GameResultRankAdapter.GameResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameResultViewHolder {
        val binding =
            RvMemberMatchRankItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameResultViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: GameResultViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = listSize

    class GameResultViewHolder(
        private val binding: RvMemberMatchRankItemBinding,
        private val context: Context,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.tvMemberRank.text = String.format(context.resources.getString(R.string.game_result_rank), position + 1)
        }
    }
}
