package net.jaredible.jshop.ui.group.list

interface GroupListView {

    fun showAddGroupDialog()

    fun showEditGroupDialog(groupId: Long)

    fun openItemListScreen(groupId: Long)

}