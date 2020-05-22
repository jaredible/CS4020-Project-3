package net.jaredible.jshop.ui.splash

import android.os.Bundle
import net.jaredible.jshop.ui.base.BaseActivity
import net.jaredible.jshop.ui.group.list.GroupListActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        openGroupListScreen()
    }

    private fun openGroupListScreen() {
        val intent = GroupListActivity.getStartIntent(this)
        startActivity(intent)
        finish()
    }

}