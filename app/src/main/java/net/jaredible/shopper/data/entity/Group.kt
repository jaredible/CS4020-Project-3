package net.jaredible.shopper.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "group")
data class Group(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String
)