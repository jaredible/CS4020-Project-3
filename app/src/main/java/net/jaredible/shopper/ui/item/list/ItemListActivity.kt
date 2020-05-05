package net.jaredible.shopper.ui.item.list

import android.content.Context
import android.content.Intent
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
import net.jaredible.shopper.data.entity.Item
import net.jaredible.shopper.ui.base.BaseActivity
import net.jaredible.shopper.ui.item.add.AddItemDialog
import net.jaredible.shopper.ui.item.edit.EditItemDialog
import net.jaredible.shopper.util.CurrencyUtil

class ItemListActivity : BaseActivity(), ItemListView {

    companion object {
        private const val ARGUMENT_GROUP_ID = "ARGUMENT_GROUP_ID"

        fun getStartIntent(context: Context, groupId: Long): Intent {
            val intent = Intent(context, ItemListActivity::class.java)
            intent.putExtra(ARGUMENT_GROUP_ID, groupId)
            return intent
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ItemListAdapter
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var viewModel: ItemListViewModel

    private lateinit var textTitle: TextView
    private lateinit var textTotalPrice: TextView
    private lateinit var textTotalQuantity: TextView
    private lateinit var textEmptyList: TextView
    private lateinit var buttonAddItem: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itemlist)

        viewManager = LinearLayoutManager(this)
        viewAdapter = ItemListAdapter(this)
        recyclerView = findViewById<RecyclerView>(R.id.list_items).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(this.context, viewManager.orientation))
        }

        val groupId = intent.getLongExtra(ARGUMENT_GROUP_ID, 0)
        val viewModelFactory = ItemListViewModelFactory(application, groupId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ItemListViewModel::class.java)

        textTitle = findViewById(R.id.text_title)
        textTotalPrice = findViewById(R.id.text_total_price)
        textTotalQuantity = findViewById(R.id.text_total_quantity)
        textEmptyList = findViewById(R.id.text_empty_list)
        buttonAddItem = findViewById(R.id.button_add_item)

        buttonAddItem.setOnClickListener { showAddItemDialog() }

        viewModel.getItems().observe(this, Observer {
            textEmptyList.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
            viewAdapter.setItems(it)
            displayTotals(it)
        })

        viewModel.getGroup().observe(this, Observer {
            textTitle.text = it.title
        })
    }

    private fun displayTotals(items: List<Item>) {
        val totalPrice = items.map { it.price }.sum()
        val totalQuantity = items.map { it.quantity }.sum()

        textTotalPrice.text = getString(R.string.item_item_text_price, CurrencyUtil.convert(totalPrice))
        textTotalQuantity.text = totalQuantity.toString()
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