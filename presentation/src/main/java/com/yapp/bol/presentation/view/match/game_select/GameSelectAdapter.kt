package com.yapp.bol.presentation.view.match.game_select

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.databinding.RvGameItemBinding
import com.yapp.bol.presentation.model.GameItem

class GameSelectAdapter(
    private val gameClickListener: (String) -> Unit
) : ListAdapter<GameItem, GameSelectAdapter.ProductItemViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val binding =
            RvGameItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductItemViewHolder(binding, gameClickListener)
    }

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class ProductItemViewHolder(
        private val binding: RvGameItemBinding,
        private val gameClickListener: (String) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GameItem) {
            binding.tvGameName.text = item.name
            binding.root.setOnClickListener {
                gameClickListener(item.name)
            }
        }
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<GameItem>() {
            override fun areItemsTheSame(oldItem: GameItem, newItem: GameItem): Boolean {
                return oldItem.imageUrl == newItem.imageUrl
            }

            override fun areContentsTheSame(oldItem: GameItem, newItem: GameItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
