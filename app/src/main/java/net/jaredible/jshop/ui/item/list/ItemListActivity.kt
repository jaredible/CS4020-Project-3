package net.jaredible.jshop.ui.item.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_item_list.*
import net.jaredible.jshop.R
import net.jaredible.jshop.data.entity.Item
import net.jaredible.jshop.ui.base.BaseActivity
import net.jaredible.jshop.ui.item.add.AddItemDialog
import net.jaredible.jshop.ui.item.edit.EditItemDialog
import net.jaredible.jshop.util.CurrencyUtil
import net.jaredible.jshop.util.observeOnce

class ItemListActivity : BaseActivity(), ItemListView {

    companion object {
        private const val ARGUMENT_GROUP_ID = "ARGUMENT_GROUP_ID"

        fun getStartIntent(context: Context, groupId: Long): Intent {
            val intent = Intent(context, ItemListActivity::class.java)
            intent.putExtra(ARGUMENT_GROUP_ID, groupId)
            return intent
        }
    }

    private lateinit var viewAdapter: ItemListAdapter
    private lateinit var viewModel: ItemListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        val viewManager = LinearLayoutManager(this)
        viewAdapter = ItemListAdapter(this)
        vItems.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(this.context, viewManager.orientation))
        }

        val groupId = intent.getLongExtra(ARGUMENT_GROUP_ID, 0)
        val viewModelFactory = ItemListViewModelFactory(application, groupId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ItemListViewModel::class.java)

        vAddItem.setOnClickListener { showAddItemDialog() }

        viewModel.getItems().observe(this, Observer { items ->
            vEmpty.visibility = if (items.isEmpty()) View.VISIBLE else View.INVISIBLE
            viewAdapter.setItems(items)
            displayTotals(items)
        })

        viewModel.getGroup().observeOnce(this, Observer { group ->
            vTitle.text = group.title
        })
    }

    private fun displayTotals(items: List<Item>) {
        val totalPrice = items.map { it.price * it.quantity }.sum()
        val totalQuantity = items.map { it.quantity }.sum()

        vTotalPrice.text = getString(R.string.currency_format, CurrencyUtil.format(totalPrice))
        vTotalQuantity.text = totalQuantity.toString()
    }

    override fun showAddItemDialog() {
        val dialog = AddItemDialog.newInstance(viewModel.getGroupId())
        dialog.show(supportFragmentManager, AddItemDialog.TAG)
    }

    override fun showEditItemDialog(itemId: Long) {
        val dialog = EditItemDialog.newInstance(viewModel.getGroupId(), itemId)
        dialog.show(supportFragmentManager, EditItemDialog.TAG)
    }

    override fun onItemChecked(itemId: Long, checked: Boolean) {
        viewModel.updateChecked(itemId, checked)
    }

}