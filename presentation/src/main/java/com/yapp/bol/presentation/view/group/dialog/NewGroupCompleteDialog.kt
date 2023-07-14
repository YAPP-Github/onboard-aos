package com.yapp.bol.presentation.view.group.dialog

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager.LayoutParams
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.yapp.bol.domain.model.NewGroupItem
import com.yapp.bol.presentation.databinding.NewGroupCompleteDialogBinding
import com.yapp.bol.presentation.utils.convertPxToDp
import com.yapp.bol.presentation.utils.dialogWidthResize
import com.yapp.bol.presentation.utils.loadImage

class NewGroupCompleteDialog(
    private val context: Context,
    private val newGroup: NewGroupItem,
    private val moveHome: (Int) -> Unit,
) : Dialog(context) {

    private lateinit var binding: NewGroupCompleteDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = NewGroupCompleteDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        context.dialogWidthResize(this, 0.9f)
        this.setCancelable(false)
        binding.setText()
        binding.setImage()
        binding.setOnClick()

    }

    private fun NewGroupCompleteDialogBinding.setText() {
        this.tvGroupTitle.text = newGroup.name
        this.tvGroupDescription.text = newGroup.description
        this.tvGroupAccessCodeValue.text = newGroup.accessCode
        this.tvGroupOrganizationValue.text = newGroup.organization
        this.tvGroupOwnerValue.text = newGroup.owner
    }

    private fun NewGroupCompleteDialogBinding.setImage() {
        val params = ConstraintLayout.LayoutParams(LayoutParams.MATCH_PARENT, context.convertPxToDp(462))
        this.ivGroupImage.loadImage(newGroup.imageUrl)
        this.ivGroupImage.layoutParams = params
        this.viewGroupImage.layoutParams = params
    }

    private fun generateCopy(accessCode: String) {
        val clipboard: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(COPY_ACCESS_CODE_KEY, accessCode)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, COPY_SUCCESS_MESSAGE, Toast.LENGTH_SHORT).show()
    }

    private fun NewGroupCompleteDialogBinding.setOnClick() {
        this.btnGroupComplete.setOnClickListener {
            dismiss()
            moveHome(newGroup.id)
        }

        this.tvGroupAccessCodeValue.setOnClickListener {
            generateCopy(newGroup.accessCode)
        }
        this.ibCopyBtn.setOnClickListener {
            generateCopy(newGroup.accessCode)
        }
    }

    companion object {
        const val COPY_ACCESS_CODE_KEY = "AccessCode"
        const val COPY_SUCCESS_MESSAGE = "참여 코드가 복사되었습니다."
    }
}
