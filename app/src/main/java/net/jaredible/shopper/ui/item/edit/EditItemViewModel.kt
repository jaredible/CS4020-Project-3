package net.jaredible.shopper.ui.item.edit

import android.app.Application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.jaredible.shopper.data.AppDatabase
import net.jaredible.shopper.data.repository.ItemRepository
import net.jaredible.shopper.ui.base.BaseViewModel

class EditItemViewModel(application: Application, private val groupId: Long, private val itemId: Long) : BaseViewModel(application) {

    private val itemRepository = ItemRepository(AppDatabase.getDatabase(application).itemDao())

    fun getItem() = itemRepository.getItem(itemId, groupId)

    fun updateItemName(name: String) {
        viewModelScope.launch {
            itemRepository.updateName(itemId, groupId, name)
        }
    }

    fun updateItemPrice(price: String) {
        viewModelScope.launch {
            itemRepository.updatePrice(itemId, groupId, if (price.isNotBlank()) (price.toFloat() * 100).toInt() else 0)
        }
    }

    fun updateItemQuantity(quantity: String) {
        viewModelScope.launch {
            itemRepository.updateQuantity(itemId, groupId, quantity.toInt())
        }
    }

    fun deleteItem() {
        viewModelScope.launch {
            itemRepository.delete(itemId)
        }
    }

}