package com.yapp.bol.presentation.view.login

import android.content.Intent
import androidx.activity.viewModels
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseActivity
import com.yapp.bol.presentation.databinding.ActivityNetworkErrorBinding
import com.yapp.bol.presentation.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NetworkErrorActivity : BaseActivity<ActivityNetworkErrorBinding>(R.layout.activity_network_error) {

    private val viewModel: NetworkErrorViewModel by viewModels()

    override fun onCreateAction() {
        super.onCreateAction()
        binding.btnRequestApi.setOnClickListener {
            viewModel.getMyGroupList()
        }

        viewModel.myGroupList.observe(this) {
            if (it == null) return@observe
            if (it.isEmpty()) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                HomeActivity.startActivity(binding.root.context, it.first().id)
            }
            finish()
        }
    }
}
