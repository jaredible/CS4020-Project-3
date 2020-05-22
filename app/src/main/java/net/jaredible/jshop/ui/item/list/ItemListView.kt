package net.jaredible.jshop.ui.item.list

interface ItemListView {

    fun showAddItemDialog()

    fun showEditItemDialog(itemId: Long)

    fun onItemChecked(itemId: Long, checked: Boolean)

}