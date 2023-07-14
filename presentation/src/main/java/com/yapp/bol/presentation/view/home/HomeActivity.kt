package com.yapp.bol.presentation.view.home

import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseActivity
import com.yapp.bol.presentation.databinding.ActivityHomeBinding
import com.yapp.bol.presentation.view.home.rank.HomeRankFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private lateinit var homeRankFragment: HomeRankFragment

    override fun onCreateAction() {
        super.onCreateAction()

        homeRankFragment = HomeRankFragment()
    }
}
