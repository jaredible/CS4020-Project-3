package net.jaredible.shopper.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import net.jaredible.shopper.data.entity.Group

@Dao
interface GroupDao {

    @Query("SELECT * FROM `group` ORDER BY title")
    fun getGroups(): LiveData<List<Group>>

    @Query("SELECT * FROM `group` WHERE id = :id")
    fun getGroup(id: Long): LiveData<Group>

    @Query("UPDATE `group` SET title = :title WHERE id = :id")
    suspend fun updateTitle(id: Long, title: String)

    @Query("DELETE FROM `group` WHERE id = :id")
    suspend fun delete(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(group: Group)

    @Update
    suspend fun update(group: Group)

    @Delete
    suspend fun delete(group: Group)

}