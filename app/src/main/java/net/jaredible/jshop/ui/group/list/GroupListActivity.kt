package net.jaredible.jshop.ui.group.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_group_list.*
import net.jaredible.jshop.R
import net.jaredible.jshop.ui.base.BaseActivity
import net.jaredible.jshop.ui.group.add.AddGroupDialog
import net.jaredible.jshop.ui.group.edit.EditGroupDialog
import net.jaredible.jshop.ui.item.list.ItemListActivity

class GroupListActivity : BaseActivity(), GroupListView {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, GroupListActivity::class.java)
        }
    }

    private lateinit var viewAdapter: GroupListAdapter
    private lateinit var viewModel: GroupListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_list)

        val viewManager = LinearLayoutManager(this)
        viewAdapter = GroupListAdapter(this)
        vGroups.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(this.context, viewManager.orientation))
        }

        viewModel = ViewModelProvider(this).get(GroupListViewModel::class.java)

        vAddGroup.setOnClickListener { showAddGroupDialog() }

        viewModel.getGroups().observe(this, Observer { groups ->
            vEmpty.visibility = if (groups.isEmpty()) View.VISIBLE else View.INVISIBLE
            viewAdapter.setGroups(groups)
        })
    }

    override fun showAddGroupDialog() {
        val dialog = AddGroupDialog.newInstance()
        dialog.show(supportFragmentManager, AddGroupDialog.TAG)
    }

    override fun showEditGroupDialog(groupId: Long) {
        val dialog = EditGroupDialog.newInstance(groupId)
        dialog.show(supportFragmentManager, EditGroupDialog.TAG)
    }

    override fun openItemListScreen(groupId: Long) {
        val intent = ItemListActivity.getStartIntent(this, groupId)
        startActivity(intent)
    }

}