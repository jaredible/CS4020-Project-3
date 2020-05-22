package net.jaredible.jshop.ui.group.add

import android.app.Application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.jaredible.jshop.data.AppDatabase
import net.jaredible.jshop.data.entity.Group
import net.jaredible.jshop.data.repository.GroupRepository
import net.jaredible.jshop.ui.base.BaseViewModel

class AddGroupViewModel(application: Application) : BaseViewModel(application) {

    private val groupRepository = GroupRepository(AppDatabase.getDatabase(application).groupDao())

    fun addGroup(title: String) {
        viewModelScope.launch {
            groupRepository.insert(Group(0, title))
        }
    }

}