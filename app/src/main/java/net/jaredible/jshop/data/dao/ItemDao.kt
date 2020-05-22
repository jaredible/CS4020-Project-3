package net.jaredible.jshop.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import net.jaredible.jshop.data.entity.Item

@Dao
interface ItemDao {

    @Query("SELECT * FROM item WHERE group_id = :groupId ORDER BY name")
    fun getItems(groupId: Long): LiveData<List<Item>>

    @Query("SELECT * FROM item WHERE id = :itemId AND group_id = :groupId")
    fun getItem(itemId: Long, groupId: Long): LiveData<Item>

    @Query("UPDATE item SET name = :name WHERE id = :itemId AND group_id = :groupId")
    suspend fun updateName(itemId: Long, groupId: Long, name: String)

    @Query("UPDATE item SET price = :price WHERE id = :itemId AND group_id = :groupId")
    suspend fun updatePrice(itemId: Long, groupId: Long, price: Int)

    @Query("UPDATE item SET quantity = :quantity WHERE id = :itemId AND group_id = :groupId")
    suspend fun updateQuantity(itemId: Long, groupId: Long, quantity: Int)

    @Query("UPDATE item SET checked = :checked WHERE id = :itemId AND group_id = :groupId")
    suspend fun updateChecked(itemId: Long, groupId: Long, checked: Boolean)

    @Query("DELETE FROM item WHERE id = :id")
    suspend fun delete(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

}