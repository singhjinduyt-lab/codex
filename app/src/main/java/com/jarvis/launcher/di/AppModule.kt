package com.jarvis.launcher.di

import android.content.Context
import androidx.room.Room
import com.jarvis.launcher.data.JarvisDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton fun database(@ApplicationContext context: Context) = Room.databaseBuilder(context, JarvisDatabase::class.java, "jarvis.db").build()
    @Provides fun conversations(db: JarvisDatabase) = db.conversationDao()
    @Provides fun appUsage(db: JarvisDatabase) = db.appUsageDao()
}
