package net.jaredible.jshop.ui.group.list

import android.app.Application
import net.jaredible.jshop.data.AppDatabase
import net.jaredible.jshop.data.repository.GroupRepository
import net.jaredible.jshop.ui.base.BaseViewModel

class GroupListViewModel(application: Application) : BaseViewModel(application) {

    private val groupRepository = GroupRepository(AppDatabase.getDatabase(application).groupDao())

    fun getGroups() = groupRepository.getGroups()

}