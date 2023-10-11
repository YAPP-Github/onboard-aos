package com.yapp.bol.presentation.view.login.splash

import android.content.Intent
import androidx.activity.viewModels
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseActivity
import com.yapp.bol.presentation.databinding.ActivitySplashBinding
import com.yapp.bol.presentation.utils.setStatusBarColor
import com.yapp.bol.presentation.view.group.GroupActivity
import com.yapp.bol.presentation.view.home.HomeActivity
import com.yapp.bol.presentation.view.login.LoginActivity
import com.yapp.bol.presentation.view.login.NetworkErrorActivity
import com.yapp.bol.presentation.view.setting.UpgradeActivity
import dagger.hilt.android.AndroidEntryPoint
import com.yapp.bol.designsystem.R as DR

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreateAction() {
        subscribeObservables()
        setStatusBarColor(
            this@SplashActivity,
            R.color.Gray_15,
            isIconBlack = false,
        )
    }

    private fun subscribeObservables() {
        splashViewModel.animationState.observe(this) {
            if (it.not()) return@observe
            splashViewModel.getMyGroupList()
        }
        splashViewModel.onBoardingState.observe(this) {
            if (it == null) return@observe

            when {
                it.onBoarding.isNotEmpty() -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(DR.anim.fadein, DR.anim.fadeout)
                    finish()
                }
                it.mainGroupId == null -> {
                    val intent = Intent(this, GroupActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> {
                    HomeActivity.startActivity(binding.root.context, it.mainGroupId.toLong())
                    finish()
                }
            }
        }

        splashViewModel.onBoardingErrorState.observe(this) {
            if (it.not()) return@observe
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(DR.anim.fadein, DR.anim.fadeout)
            finish()
        }

        splashViewModel.networkState.observe(this) {
            if (it.not()) return@observe
            startActivity(Intent(this, NetworkErrorActivity::class.java))
            finish()
        }

        splashViewModel.upgradeState.observe(this) {
            if (it) {
                UpgradeActivity.startActivity(this@SplashActivity)
                finish()
            }
        }
    }
}
