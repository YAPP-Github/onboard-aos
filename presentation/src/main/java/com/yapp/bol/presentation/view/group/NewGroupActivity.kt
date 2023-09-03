package com.yapp.bol.presentation.view.group

import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseActivity
import com.yapp.bol.presentation.databinding.ActivityNewGroupBinding
import com.yapp.bol.presentation.firebase.GA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewGroupActivity : BaseActivity<ActivityNewGroupBinding>(R.layout.activity_new_group) {
    override fun getScreenName(): String = GA.Screen.NEW_GROUP
}
