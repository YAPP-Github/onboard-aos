package com.yapp.bol.presentation.view.group.join.component

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.GroupItemUserTitleBinding
import com.yapp.bol.presentation.utils.getDimen
import com.yapp.bol.presentation.utils.inflate

class GroupItemUserTitle(
    context: Context,
    attrs: AttributeSet,
) : LinearLayout(context, attrs) {

    private val binding by lazy {
        inflate<GroupItemUserTitleBinding>(R.layout.group_item_user_title, true)
    }

    init {
        bindAttributes(context, attrs)
        setBackgroundResource(R.drawable.group_explan_bg)
        orientation = HORIZONTAL
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            setPadding(
                context.getDimen(R.dimen.group_join_item_user_title_horizontal).toInt(),
                context.getDimen(R.dimen.group_join_item_user_title_vertical).toInt(),
                context.getDimen(R.dimen.group_join_item_user_title_horizontal).toInt(),
                context.getDimen(R.dimen.group_join_item_user_title_vertical).toInt(),
            )
        }
    }

    private fun bindAttributes(context: Context, attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.GroupItemUserTitle, 0, 0).apply {
            getString(R.styleable.GroupItemUserTitle_title)?.let { title ->
                setGroupItemTitle(title)
            }

            getString(R.styleable.GroupItemUserTitle_detailTitle)?.let { title ->
                setGroupItemDetailTitle(title)
            }
        }.recycle()
    }

    fun setGroupItemTitle(title: String) {
        binding.tvTitle.text = title
    }

    fun setGroupItemDetailTitle(title: String) {
        binding.tvDetailTitle.text = title
    }
}
