package net.jaredible.shopper.ui.item.list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ItemListViewModelFactory(private val application: Application, private val groupId: Long) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemListViewModel::class.java)) return ItemListViewModel(application, groupId) as T
        throw IllegalArgumentException("Unknown ViewModel class!")
    }

}