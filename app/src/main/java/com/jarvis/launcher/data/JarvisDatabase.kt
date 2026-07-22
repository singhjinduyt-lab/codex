package com.jarvis.launcher.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao interface ConversationDao { @Query("SELECT * FROM conversations ORDER BY createdAt ASC") fun observe(): Flow<List<ConversationEntity>>; @Insert suspend fun insert(entity: ConversationEntity); @Query("DELETE FROM conversations") suspend fun clear() }
@Dao interface AppUsageDao { @Query("SELECT * FROM app_usage ORDER BY favorite DESC, label COLLATE NOCASE") fun observe(): Flow<List<AppUsageEntity>>; @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun upsert(entity: AppUsageEntity) }
@Database(entities = [ConversationEntity::class, AppUsageEntity::class], version = 1)
abstract class JarvisDatabase : RoomDatabase() { abstract fun conversationDao(): ConversationDao; abstract fun appUsageDao(): AppUsageDao }
