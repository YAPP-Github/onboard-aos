package com.yapp.bol.presentation.view.match.game_result

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.databinding.RvMemberMatchItemBinding
import com.yapp.bol.presentation.model.MemberItem

class GameResultAdapter(
    private val list: MutableList<MemberItem>,
    private val getTargetPosition: (Int,Int) -> Int,
    private val updateValue: (Int,Int) -> Unit
) : RecyclerView.Adapter<GameResultAdapter.GameResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameResultViewHolder {
        val binding =
            RvMemberMatchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameResultViewHolder(
            binding = binding,
            items = ArrayList(list),
            getTargetPosition,
            updateValue
        )
    }

    override fun onBindViewHolder(holder: GameResultViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    inner class GameResultViewHolder(
        private val binding: RvMemberMatchItemBinding,
        private val items: ArrayList<MemberItem>,
        private val getTargetPosition: (Int,Int) -> Int,
        private val updateValue: (Int,Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MemberItem, position: Int) {
            binding.tvMemberName.text = item.name

            binding.etGameScore.doOnTextChanged { text, _, _, _ ->
                updateValue(position,text?.toString()?.toInt() ?: 0)
            }

            binding.root.setOnClickListener {
                val score = binding.etGameScore.text.toString()
                val target = getTargetPosition(position, if(score.isEmpty()) 0 else score.toInt())
                if(target == -1) return@setOnClickListener
                swipePosition(target)(binding.root)
            }
        }

        private fun swipePosition(target: Int): (View) -> Unit = {
            if(layoutPosition > 0 && layoutPosition < items.size ) {
                items.removeAt(layoutPosition).also { items.add(target, it) }
                notifyItemMoved(layoutPosition, target)
            }
        }
    }
}
