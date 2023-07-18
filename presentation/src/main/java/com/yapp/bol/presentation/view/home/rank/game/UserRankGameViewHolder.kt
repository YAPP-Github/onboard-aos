package com.yapp.bol.presentation.view.home.rank.game

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.domain.model.GameItem
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ItemRankGameListBinding
import com.yapp.bol.presentation.model.GameItemWithSelected
import com.yapp.bol.presentation.utils.createScaleAnimator
import com.yapp.bol.presentation.utils.loadImage
import com.yapp.bol.designsystem.R as designR


class UserRankGameViewHolder(
    private val binding: ItemRankGameListBinding,
    private val onClick: (position: Int, gameId: Long) -> Unit,
    private val scrollAnimation: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(gameItemWithSelected: GameItemWithSelected) {
        showGameItemData(gameItemWithSelected.gameItem)

        startAnimationBySelectState(gameItemWithSelected.isSelected)

        binding.viewGame.root.setOnClickListener {
            onClick(layoutPosition, gameItemWithSelected.gameItem.id)
        }

    }

    private fun showGameItemData(gameItem: GameItem) {
        binding.apply {
            binding.viewGame.tvGame.text = gameItem.name
            binding.viewGame.ivGame.loadImage(gameItem.img)
        }
    }

    fun showGameItemBySelectState(isSelected: Boolean) {
        if (isSelected) {
            binding.setLayoutWhenSelected()
        } else {
            binding.setLayoutWhenUnselected()
        }
    }

    private fun startAnimationBySelectState(isSelected: Boolean) {
        if (isSelected) {
            binding.startAnimationWhenSelected()
        } else {
            binding.startAnimationWhenUnselected()
        }
    }

    private fun ItemRankGameListBinding.setLayoutWhenSelected() {
        val selectedPx = root.context.resources.getDimensionPixelOffset(R.dimen.home_game_selected_iv)
        val lp = viewGame.ivGame.layoutParams
        lp.apply {
            width = selectedPx
            height = selectedPx
        }.also { viewGame.ivGame.layoutParams = lp }

        val color = ContextCompat.getColor(root.context, com.yapp.bol.designsystem.R.color.Gray_1)
        viewGame.tvGame.setTextColor(ColorStateList.valueOf(color))

        viewGame.ivOverlap.visibility = View.GONE
        TextViewCompat.setTextAppearance(viewGame.tvGame, designR.style.Typography_Title4)
        viewGame.tvGame.setTextColor(ContextCompat.getColor(binding.root.context, designR.color.Gray_1))
    }

    private fun ItemRankGameListBinding.setLayoutWhenUnselected() {
        val unSelectedPx = root.context.resources.getDimensionPixelOffset(R.dimen.home_game_unselected_iv)
        val lp = viewGame.ivGame.layoutParams
        lp.apply {
            width = unSelectedPx
            height = unSelectedPx
        }.also { viewGame.ivGame.layoutParams = lp }

        val color = ContextCompat.getColor(root.context, com.yapp.bol.designsystem.R.color.Gray_9)
        viewGame.tvGame.setTextColor(ColorStateList.valueOf(color))

        viewGame.ivOverlap.visibility = View.VISIBLE
        TextViewCompat.setTextAppearance(viewGame.tvGame, designR.style.Typography_Body5_R)
        viewGame.tvGame.setTextColor(ContextCompat.getColor(binding.root.context, designR.color.Gray_9))
    }

    private fun ItemRankGameListBinding.startAnimationWhenSelected() {
        val currentPx = binding.viewGame.ivGame.layoutParams.width
        val endPx = binding.root.context.resources.getDimensionPixelOffset(R.dimen.home_game_selected_iv)
        viewGame.ivGame.createScaleAnimator(currentPx, endPx, 200L, scrollAnimation).start()

        val color = ContextCompat.getColor(binding.root.context, com.yapp.bol.designsystem.R.color.Gray_1)
        viewGame.tvGame.setTextColor(ColorStateList.valueOf(color))
    }

    private fun ItemRankGameListBinding.startAnimationWhenUnselected() {
        val endPx = binding.root.context.resources.getDimensionPixelOffset(R.dimen.home_game_unselected_iv)
        val currentPx = binding.viewGame.ivGame.layoutParams.width
        viewGame.ivGame.createScaleAnimator(currentPx, endPx, 200L, null).start()

        val color = ContextCompat.getColor(binding.root.context, com.yapp.bol.designsystem.R.color.Gray_9)
        viewGame.tvGame.setTextColor(ColorStateList.valueOf(color))
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClick: (position: Int, gameId: Long) -> Unit,
            scrollAnimation: () -> Unit,
        ): UserRankGameViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemRankGameListBinding.inflate(inflater, parent, false)
            return UserRankGameViewHolder(binding, onClick, scrollAnimation)
        }
    }
}
