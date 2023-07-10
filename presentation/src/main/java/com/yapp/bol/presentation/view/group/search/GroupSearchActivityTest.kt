package com.yapp.bol.presentation.view.group.search

import androidx.fragment.app.commit
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseActivity
import com.yapp.bol.presentation.databinding.ActivityGroupSearchTestBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 테스트용으로 만들어둔 파일입니다.
 */
@AndroidEntryPoint
class GroupSearchActivityTest : BaseActivity<ActivityGroupSearchTestBinding>(R.layout.activity_group_search_test) {

    override fun onCreateAction() {
        super.onCreateAction()
        supportFragmentManager.commit {
            replace(R.id.fl_test, GroupSearchFragment())
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }
}
