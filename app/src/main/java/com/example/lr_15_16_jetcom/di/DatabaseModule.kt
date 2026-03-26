package com.example.lr_15_16_jetcom.di

import android.content.Context
import com.example.lr_15_16_jetcom.data.AppDatabase
import com.example.lr_15_16_jetcom.data.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// [ЛР 17] @Module - контейнер для зависимостей
// [ЛР 17] @InstallIn(SingletonComponent::class) - живёт всё время приложения
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // [ЛР 17] @Provides - говорит Hilt, как создать объект
    // [ЛР 17/18] @Singleton - один экземпляр на всё приложение
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideNoteDao(database: AppDatabase): NoteDao {
        return database.noteDao()
    }
}