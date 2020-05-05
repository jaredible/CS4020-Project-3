package net.jaredible.shopper.ui.splash

import android.content.Intent
import android.os.Bundle
import net.jaredible.shopper.ui.base.BaseActivity
import net.jaredible.shopper.ui.group.list.GroupListActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        openGroupListScreen()
    }

    private fun openGroupListScreen() {
        val intent = Intent(this, GroupListActivity::class.java)
        startActivity(intent)
        finish()
    }

}