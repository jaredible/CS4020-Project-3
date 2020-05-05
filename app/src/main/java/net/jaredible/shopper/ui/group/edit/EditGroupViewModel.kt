package net.jaredible.shopper.ui.group.edit

import android.app.Application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.jaredible.shopper.data.AppDatabase
import net.jaredible.shopper.data.repository.GroupRepository
import net.jaredible.shopper.ui.base.BaseViewModel

class EditGroupViewModel(application: Application, private val groupId: Long) : BaseViewModel(application) {

    private val groupRepository = GroupRepository(AppDatabase.getDatabase(application).groupDao())

    fun getGroup() = groupRepository.getGroup(groupId)

    fun updateGroupTitle(title: String) {
        viewModelScope.launch {
            groupRepository.updateTitle(groupId, title)
        }
    }

    fun deleteGroup() {
        viewModelScope.launch {
            groupRepository.delete(groupId)
        }
    }

}