package net.jaredible.shopper.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.jaredible.shopper.data.entity.Item
import net.jaredible.shopper.data.dao.ItemDao
import net.jaredible.shopper.data.dao.GroupDao
import net.jaredible.shopper.data.entity.Group

@Database(entities = [Group::class, Item::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "shopper.db")
                    .createFromAsset("database/shopper.db")
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

    abstract fun groupDao(): GroupDao

    abstract fun itemDao(): ItemDao

}