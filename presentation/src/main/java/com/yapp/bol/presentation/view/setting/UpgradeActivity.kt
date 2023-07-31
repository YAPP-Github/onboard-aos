package com.yapp.bol.presentation.view.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseActivity
import com.yapp.bol.presentation.databinding.ActivityUpgradeBinding

class UpgradeActivity : BaseActivity<ActivityUpgradeBinding>(R.layout.activity_upgrade) {
    override fun onCreateAction() {
        super.onCreateAction()
        bindUpgradeButton()
    }

    private fun bindUpgradeButton() {
        binding.btnUpgrade.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "http://play.google.com/store/search?q=onboard&c=apps"
                )
                setPackage("com.android.vending")
            }
            startActivity(intent)
        }
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(
                Intent(context, UpgradeActivity::class.java)
            )
        }
    }
}
