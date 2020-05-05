package net.jaredible.shopper.data.repository

import net.jaredible.shopper.data.dao.ItemDao
import net.jaredible.shopper.data.entity.Item

class ItemRepository(private val itemDao: ItemDao) {

    fun getItems(groupId: Long) = itemDao.getItems(groupId)

    fun getItem(itemId: Long, groupId: Long) = itemDao.getItem(itemId, groupId)

    suspend fun updateName(itemId: Long, groupId: Long, name: String) = itemDao.updateName(itemId, groupId, name)

    suspend fun updatePrice(itemId: Long, groupId: Long, price: Int) = itemDao.updatePrice(itemId, groupId, price)

    suspend fun updateQuantity(itemId: Long, groupId: Long, quantity: Int) = itemDao.updateQuantity(itemId, groupId, quantity)

    suspend fun updateChecked(itemId: Long, groupId: Long, checked: Boolean) = itemDao.updateChecked(itemId, groupId, checked)

    suspend fun delete(id: Long) = itemDao.delete(id)

    suspend fun insert(item: Item) = itemDao.insert(item)

    suspend fun update(item: Item) = itemDao.update(item)

    suspend fun delete(item: Item) = itemDao.delete(item)

}