package net.jaredible.shopper.ui.group.list

import android.app.Application
import net.jaredible.shopper.data.AppDatabase
import net.jaredible.shopper.data.repository.GroupRepository
import net.jaredible.shopper.ui.base.BaseViewModel

class GroupListViewModel(application: Application) : BaseViewModel(application) {

    private val groupRepository = GroupRepository(AppDatabase.getDatabase(application).groupDao())

    fun getGroups() = groupRepository.getGroups()

}