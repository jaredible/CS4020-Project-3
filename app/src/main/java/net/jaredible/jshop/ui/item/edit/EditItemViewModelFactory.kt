package net.jaredible.jshop.ui.item.edit

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditItemViewModelFactory(private val application: Application, private val groupId: Long, private val itemId: Long) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditItemViewModel::class.java)) return EditItemViewModel(application, groupId, itemId) as T
        throw IllegalArgumentException("Unknown ViewModel class!")
    }

}