package net.jaredible.jshop.ui.group.edit

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditGroupModelFactory(private val application: Application, private val groupId: Long) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditGroupViewModel::class.java)) return EditGroupViewModel(application, groupId) as T
        throw IllegalArgumentException("Unknown ViewModel class!")
    }

}