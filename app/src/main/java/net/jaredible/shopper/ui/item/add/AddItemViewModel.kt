package net.jaredible.shopper.ui.item.add

import android.app.Application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.jaredible.shopper.data.AppDatabase
import net.jaredible.shopper.data.entity.Item
import net.jaredible.shopper.data.repository.ItemRepository
import net.jaredible.shopper.ui.base.BaseViewModel

class AddItemViewModel(application: Application, private val groupId: Long) : BaseViewModel(application) {

    private val itemRepository = ItemRepository(AppDatabase.getDatabase(application).itemDao())

    fun addItem(name: String, price: String, quantity: String) {
        viewModelScope.launch {
            itemRepository.insert(Item(0, groupId, name, if (price.isNotBlank()) (price.toFloat() * 100).toInt() else 0, quantity.toInt(), false))
        }
    }

}