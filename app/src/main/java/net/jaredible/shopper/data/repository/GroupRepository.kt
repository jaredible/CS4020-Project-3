package net.jaredible.shopper.data.repository

import net.jaredible.shopper.data.entity.Group
import net.jaredible.shopper.data.dao.GroupDao

class GroupRepository(private val groupDao: GroupDao) {

    fun getGroups() = groupDao.getGroups()

    fun getGroup(id: Long) = groupDao.getGroup(id)

    suspend fun updateTitle(id: Long, title: String) = groupDao.updateTitle(id, title)

    suspend fun delete(id: Long) = groupDao.delete(id)

    suspend fun insert(group: Group) = groupDao.insert(group)

    suspend fun update(group: Group) = groupDao.update(group)

    suspend fun delete(group: Group) = groupDao.delete(group)

}