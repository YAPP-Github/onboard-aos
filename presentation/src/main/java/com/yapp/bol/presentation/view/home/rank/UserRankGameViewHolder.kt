package com.yapp.bol.presentation.view.home.rank

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.domain.model.GameItem
import com.yapp.bol.presentation.databinding.ItemRankGameListBinding
import com.yapp.bol.presentation.utils.loadImage

class UserRankGameViewHolder(
    private val binding: ItemRankGameListBinding,
    private val onClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.viewGame.root.setOnClickListener {
            onClick.invoke(layoutPosition)
            binding.setGameItemLayoutParamsWhenSelected()
        }
    }

    fun bind(gameItem: GameItem, isSelected: Boolean) {
        showGameItemData(gameItem)
        showGameItemBySelectState(isSelected)
    }

    private fun showGameItemData(gameItem: GameItem) {
        binding.apply {
            binding.viewGame.tvGame.text = gameItem.name
            binding.viewGame.ivGame.loadImage(gameItem.img)
        }
    }

    private fun showGameItemBySelectState(isSelected: Boolean) {
        if (isSelected) {
            binding.setGameItemLayoutParamsWhenSelected()
        } else {
            binding.setGameItemLayoutParamsWhenUnselected()
        }
    }

    private fun ItemRankGameListBinding.setGameItemLayoutParamsWhenSelected() {
        val ivGameLP = viewGame.ivGame.layoutParams.apply {
            val px = dpToPx(
                binding.root.context, 68f
            )
            width = px
            height = px
        }
        viewGame.ivGame.layoutParams = ivGameLP

        val paddingLP = viewStartPadding.layoutParams.apply {
            val px = dpToPx(binding.root.context, 11f)
            width = px
        }
        binding.viewStartPadding.layoutParams = paddingLP
        binding.viewEndPadding.layoutParams = paddingLP
    }

    private fun ItemRankGameListBinding.setGameItemLayoutParamsWhenUnselected() {
        val ivGameLP = viewGame.ivGame.layoutParams
        ivGameLP.apply {
            val px = dpToPx(
                binding.root.context, 52f
            )
            width = px
            height = px
        }
        viewGame.ivGame.layoutParams = ivGameLP

        val paddingLP = viewStartPadding.layoutParams
        paddingLP.apply {
            val px = dpToPx(binding.root.context, 7f)
            width = px
        }
        viewStartPadding.layoutParams = paddingLP
        viewEndPadding.layoutParams = paddingLP
    }

    private fun dpToPx(context: Context, dp: Float): Int {
        val displayMetrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics).toInt()
    }

    companion object {
        fun create(parent: ViewGroup, onClick: (Int) -> Unit): UserRankGameViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemRankGameListBinding.inflate(inflater, parent, false)
            return UserRankGameViewHolder(binding, onClick)
        }
    }
}
