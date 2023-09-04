package com.yapp.bol.presentation.view.group.join.component

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.GroupItemGameBinding
import com.yapp.bol.presentation.utils.inflate

class GroupItemGame(
    context: Context,
    attrs: AttributeSet,
) : LinearLayout(context, attrs) {

    private val binding by lazy {
        inflate<GroupItemGameBinding>(R.layout.group_item_game, true)
    }

    init {
        orientation = VERTICAL
        bindAttributes(context, attrs)
    }

    private fun bindAttributes(context: Context, attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.GroupItemGame, 0, 0).apply {
            getString(R.styleable.GroupItemGame_imageUrl)?.let { url ->
                setImageUrl(url)
            }
            getString(R.styleable.GroupItemGame_gameTitle)?.let { title ->
                setGameTitle(title)
            }
        }.recycle()
    }

    fun setGameTitle(gameTitle: String?) {
        binding.tvGroupGameTitle.text = gameTitle
    }

    fun setImageUrl(url: String?) {
        Glide.with(this)
            .load(url)
            .into(binding.ivGroupGroup)
    }
}
