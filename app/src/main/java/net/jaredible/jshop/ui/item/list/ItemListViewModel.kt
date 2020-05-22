package net.jaredible.jshop.ui.item.list

import android.app.Application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.jaredible.jshop.data.AppDatabase
import net.jaredible.jshop.data.repository.GroupRepository
import net.jaredible.jshop.data.repository.ItemRepository
import net.jaredible.jshop.ui.base.BaseViewModel

class ItemListViewModel(application: Application, private val groupId: Long) : BaseViewModel(application) {

    private val groupRepository = GroupRepository(AppDatabase.getDatabase(application).groupDao())
    private val itemRepository = ItemRepository(AppDatabase.getDatabase(application).itemDao())

    fun getGroupId() = groupId

    fun getGroup() = groupRepository.getGroup(groupId)

    fun getItems() = itemRepository.getItems(groupId)

    fun updateChecked(itemId: Long, checked: Boolean) {
        viewModelScope.launch {
            itemRepository.updateChecked(itemId, groupId, checked)
        }
    }

}