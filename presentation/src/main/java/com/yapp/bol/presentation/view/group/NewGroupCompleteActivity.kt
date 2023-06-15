package com.yapp.bol.presentation.view.group

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yapp.bol.domain.model.NewGroupItem
import com.yapp.bol.presentation.databinding.ActivityNewGroupCompleteBinding
import com.yapp.bol.presentation.utils.isInputTextValid
import com.yapp.bol.presentation.view.group.NewGroupActivity.Companion.ACCESS_CODE_KEY
import com.yapp.bol.presentation.view.match.MatchActivity


class NewGroupCompleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewGroupCompleteBinding

    private val newGroupItem by lazy {
        intent.getSerializableExtra(ACCESS_CODE_KEY) as NewGroupItem
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewGroupCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNewGroupInfo()
        setClickListener()
    }

    private fun setNewGroupInfo() {
        binding.tvGroupTitle.text = newGroupItem.name
        binding.tvGroupDescription.text = newGroupItem.description
        binding.tvGroupOwnerValue.text = newGroupItem.owner
        binding.tvGroupAccessCodeValue.text = newGroupItem.accessCode

        checkedOrganization(newGroupItem.organization)
    }

    private fun setClickListener() {
        binding.ibCopyBtn.setOnClickListener {
            generateCopy(newGroupItem.accessCode)
        }
        binding.tvGroupAccessCodeValue.setOnClickListener {
            generateCopy(newGroupItem.accessCode)
        }
        binding.btnGroupComplete.setOnClickListener {
            moveMatchActivity()
        }
    }

    private fun moveMatchActivity() {
        val intent = Intent(this, MatchActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkedOrganization(organization: String) {
        if (isInputTextValid(organization)) {
            binding.tvGroupOrganizationValue.text = organization
        } else {
            binding.tvGroupOrganizationValue.visibility = View.INVISIBLE
            binding.tvGroupOrganization.visibility = View.INVISIBLE
        }
    }

    private fun generateCopy(accessCode: String) {
        val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(COPY_ACCESS_CODE_KEY, accessCode)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, COPY_SUCCESS_MESSAGE, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val COPY_ACCESS_CODE_KEY = "AccessCode"
        const val COPY_SUCCESS_MESSAGE = "참여 코드가 복사되었습니다."
    }
}
