package net.jaredible.shopper.ui.item.list

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import net.jaredible.shopper.R
import net.jaredible.shopper.data.entity.Item
import net.jaredible.shopper.ui.base.BaseAdapter
import net.jaredible.shopper.ui.base.BaseViewHolder
import net.jaredible.shopper.util.CurrencyUtil

class ItemListAdapter(private val itemListView: ItemListView) : BaseAdapter<ItemListAdapter.ListGroupViewHolder>() {

    private val items = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListGroupViewHolder {
        val itemGroup = LayoutInflater.from(parent.context).inflate(R.layout.item_item, parent, false)
        return ListGroupViewHolder(itemGroup)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ListGroupViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setItems(items: List<Item>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListGroupViewHolder(private val view: View) : BaseViewHolder(view) {

        private val checkboxCheck: CheckBox = view.findViewById(R.id.checkbox_check)
        private val textName: TextView = view.findViewById(R.id.text_name)
        private val textPrice: TextView = view.findViewById(R.id.text_price)
        private val textQuantity: TextView = view.findViewById(R.id.text_quantity)
        private val buttonEdit: ImageButton = view.findViewById(R.id.button_edit)

        override fun bind(position: Int) {
            val item = items[position]

            textName.paintFlags = if (item.checked) Paint.STRIKE_THRU_TEXT_FLAG else 0

            checkboxCheck.isChecked = item.checked
            textName.text = item.name
            textPrice.text = view.context.getString(R.string.item_item_text_price, CurrencyUtil.format(item.price))
            textQuantity.text = item.quantity.toString()

            view.setOnClickListener(onItemClicked(item))
            checkboxCheck.setOnClickListener(onItemChecked(item))
            buttonEdit.setOnClickListener(onEditItemClicked(item))
        }

        private fun onItemClicked(item: Item): View.OnClickListener {
            return View.OnClickListener {
                itemListView.onItemChecked(item.id, !item.checked)
            }
        }

        private fun onItemChecked(item: Item): View.OnClickListener {
            return View.OnClickListener {
                itemListView.onItemChecked(item.id, checkboxCheck.isChecked)
            }
        }

        private fun onEditItemClicked(item: Item): View.OnClickListener {
            return View.OnClickListener {
                itemListView.showEditItemDialog(item.id)
            }
        }

    }

}