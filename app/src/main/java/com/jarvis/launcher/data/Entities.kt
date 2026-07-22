package com.jarvis.launcher.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversations")
data class ConversationEntity(@PrimaryKey(autoGenerate = true) val id: Long = 0, val role: String, val content: String, val createdAt: Long = System.currentTimeMillis())

@Entity(tableName = "app_usage")
data class AppUsageEntity(@PrimaryKey val packageName: String, val label: String, val launches: Int = 0, val favorite: Boolean = false, val hidden: Boolean = false, val lastOpened: Long = 0)
