package com.example.calmtask.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val dueDateMillis: Long? = null,
    val reminderTimeMillis: Long? = null,
    val priority: String = "normal",
    val status: String = "active",
    val isFocus: Boolean = false,
    val snoozeCount: Int = 0,
    val ignoreCount: Int = 0,
    val createdAtMillis: Long = System.currentTimeMillis(),
    val updatedAtMillis: Long = System.currentTimeMillis()
)
