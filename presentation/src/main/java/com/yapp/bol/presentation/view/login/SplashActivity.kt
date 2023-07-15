package com.yapp.bol.presentation.view.login

import android.content.Intent
import androidx.activity.viewModels
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseActivity
import com.yapp.bol.presentation.databinding.ActivitySplashBinding
import com.yapp.bol.presentation.view.group.search.GroupSearchActivityTest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreateAction() {
        subscribeObservables()
    }

    private fun subscribeObservables() {
        splashViewModel.accessToken.observe(this) {
            if (it == null) return@observe
            val targetActivity = if (it.isEmpty()) LoginActivity::class.java else GroupSearchActivityTest::class.java
            startActivity(Intent(this, targetActivity))
            finish()
        }
    }
}
