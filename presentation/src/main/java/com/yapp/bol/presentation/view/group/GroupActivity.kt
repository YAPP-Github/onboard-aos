package com.yapp.bol.presentation.view.group

import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseActivity
import com.yapp.bol.presentation.databinding.ActivityGroupBinding
import com.yapp.bol.presentation.firebase.GA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupActivity : BaseActivity<ActivityGroupBinding>(R.layout.activity_group) {
    override fun getScreenName(): String = GA.Screen.GROUP
}
