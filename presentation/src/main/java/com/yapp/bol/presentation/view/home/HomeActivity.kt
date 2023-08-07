package com.yapp.bol.presentation.view.home

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseActivity
import com.yapp.bol.presentation.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateAction() {
        super.onCreateAction()
        viewModel.groupId = intent.getLongExtra(HOME_GROUP_ID_KEY, -1L)
    }

    companion object {
        const val HOME_GROUP_ID_KEY = "HOME_GROUP_ID"

        fun startActivity(context: Context, groupId: Long) {
            context.startActivity(
                Intent(context, HomeActivity::class.java)
                    .putExtra(HOME_GROUP_ID_KEY, groupId)
            )
        }
    }
}
