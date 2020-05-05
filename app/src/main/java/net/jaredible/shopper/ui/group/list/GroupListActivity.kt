package net.jaredible.shopper.ui.group.list

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.jaredible.shopper.R
import net.jaredible.shopper.ui.base.BaseActivity
import net.jaredible.shopper.ui.group.add.AddGroupDialog
import net.jaredible.shopper.ui.group.edit.EditGroupDialog
import net.jaredible.shopper.ui.item.list.ItemListActivity

class GroupListActivity : BaseActivity(), GroupListView {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: GroupListAdapter
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var viewModel: GroupListViewModel

    private lateinit var textTitle: TextView
    private lateinit var textEmptyList: TextView
    private lateinit var buttonAddGroup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grouplist)

        viewManager = LinearLayoutManager(this)
        viewAdapter = GroupListAdapter(this)
        recyclerView = findViewById<RecyclerView>(R.id.list_groups).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(this.context, viewManager.orientation))
        }

        viewModel = ViewModelProvider(this).get(GroupListViewModel::class.java)

        textTitle = findViewById(R.id.text_title)
        textEmptyList = findViewById(R.id.text_empty_list)
        buttonAddGroup = findViewById(R.id.button_add_group)

        buttonAddGroup.setOnClickListener { showAddGroupDialog() }

        viewModel.getGroups().observe(this, Observer {
            textEmptyList.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
            viewAdapter.setGroups(it)
        })
    }

    override fun showAddGroupDialog() {
        val dialog = AddGroupDialog()
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