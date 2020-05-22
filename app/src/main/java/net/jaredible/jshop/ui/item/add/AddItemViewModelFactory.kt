package net.jaredible.jshop.ui.item.add

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddItemViewModelFactory(private val application: Application, private val groupId: Long) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddItemViewModel::class.java)) return AddItemViewModel(application, groupId) as T
        throw IllegalArgumentException("Unknown ViewModel class!")
    }

}