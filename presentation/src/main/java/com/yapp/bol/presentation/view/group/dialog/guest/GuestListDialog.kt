package com.yapp.bol.presentation.view.group.dialog.guest

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.GuestListDialogBinding
import com.yapp.bol.presentation.utils.dialogWidthResize

class GuestListDialog(
    private val context: Context,
    private val clearGuest: () -> Unit,
    private val getNextGuest: () -> Unit,
    private val joinedGroup: (Int, String, GuestListDialog) -> Unit,
) : Dialog(context) {

    private lateinit var binding: GuestListDialogBinding

    val guestListAdapter by lazy {
        GuestListAdapter { nickname ->
            val isEnabled = nickname.isNotEmpty()

            binding.btnJoinedGroup.isEnabled = isEnabled
            val title = if (isEnabled) {
                String.format(context.resources.getString(R.string.group_guest_join_title), nickname)
            } else {
                String.format(context.resources.getString(R.string.group_join_title))
            }
            binding.btnJoinedGroup.text = title
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = GuestListDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        context.dialogWidthResize(this, 0.9f)

        binding.groupEmptyGuest.isVisible = guestListAdapter.currentList.size == 0
        if (guestListAdapter.currentList.size > 0) setGuestAdapter()

        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                getNextMember(recyclerView)
            }
        }
        binding.rvGuestMembers.addOnScrollListener(scrollListener)
    }

    private fun setGuestAdapter() = with(binding) {
        rvGuestMembers.adapter = guestListAdapter

        btnJoinedGroup.setOnClickListener {
            val selectGuest = guestListAdapter.currentList[guestListAdapter.selectedGuest ?: return@setOnClickListener]
            joinedGroup(selectGuest.id, selectGuest.nickname, this@GuestListDialog)
        }
    }

    private fun getNextMember(recyclerView: RecyclerView) {
        val newPagePointItemVisible =
            (recyclerView.layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition() ?: 0

        val itemTotalCount = guestListAdapter.itemCount - 1

        if (newPagePointItemVisible == itemTotalCount) {
            getNextGuest()
        }
    }

    override fun dismiss() {
        super.dismiss()
        clearGuest()
    }
}
