package com.yapp.bol.presentation.view.login.splash

import android.content.Intent
import androidx.activity.viewModels
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseActivity
import com.yapp.bol.presentation.databinding.ActivitySplashBinding
import com.yapp.bol.presentation.utils.setStatusBarColor
import com.yapp.bol.presentation.view.home.HomeActivity
import com.yapp.bol.presentation.view.login.LoginActivity
import com.yapp.bol.presentation.view.login.NetworkErrorActivity
import com.yapp.bol.presentation.view.setting.UpgradeActivity
import com.yapp.bol.designsystem.R as DR
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreateAction() {
        subscribeObservables()
        setStatusBarColor(
            this@SplashActivity,
            R.color.Gray_15,
            isIconBlack = false
        )
    }

    private fun subscribeObservables() {
        splashViewModel.animationState.observe(this) {
            if (it.not()) return@observe
            splashViewModel.getMyGroupList()
        }

        splashViewModel.myGroupList.observe(this) {
            if (it == null) return@observe
            if (it.isEmpty()) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                overridePendingTransition(DR.anim.fadein, DR.anim.fadeout)
            } else {
                HomeActivity.startActivity(binding.root.context, it.first().id)
            }
            finish()
        }

        splashViewModel.networkState.observe(this) {
            if(it.not()) return@observe
            startActivity(Intent(this, NetworkErrorActivity::class.java))
        }

        splashViewModel.upgradeState.observe(this) {
            if (it) {
                UpgradeActivity.startActivity(this@SplashActivity)
                finish()
            }
        }
    }
}
