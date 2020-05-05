package net.jaredible.shopper.ui.group.add

import android.app.Application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.jaredible.shopper.data.AppDatabase
import net.jaredible.shopper.data.entity.Group
import net.jaredible.shopper.data.repository.GroupRepository
import net.jaredible.shopper.ui.base.BaseViewModel

class AddGroupViewModel(application: Application) : BaseViewModel(application) {

    private val groupRepository = GroupRepository(AppDatabase.getDatabase(application).groupDao())

    fun addGroup(title: String) {
        viewModelScope.launch {
            groupRepository.insert(Group(0, title))
        }
    }

}