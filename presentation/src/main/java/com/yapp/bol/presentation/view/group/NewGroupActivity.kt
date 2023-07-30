package com.yapp.bol.presentation.view.group

import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseActivity
import com.yapp.bol.presentation.databinding.ActivityNewGroupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewGroupActivity : BaseActivity<ActivityNewGroupBinding>(R.layout.activity_new_group) {
    override fun onBackPressed() {
        val fragments = supportFragmentManager.fragments
        for(fragment in fragments) {
            if(fragment is onBackPressedListener) {
                (fragment as onBackPressedListener).onBackPressed()
                return
            } else{
                super.onBackPressed()
            }
        }
    }
}
