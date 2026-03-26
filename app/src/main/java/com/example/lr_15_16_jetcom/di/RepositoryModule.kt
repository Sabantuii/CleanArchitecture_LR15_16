package com.example.lr_15_16_jetcom.di

import com.example.lr_15_16_jetcom.data.NotesRepository
import com.example.lr_15_16_jetcom.data.NotesRepositoryImpl
import com.example.lr_15_16_jetcom.data.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNotesRepository(
        @LocalDataSource localDao: NoteDao,
        // [ЛР 18] Можно добавить RemoteDataSource для офлайн-режима
        // @RemoteDataSource remoteDataSource: RemoteDataSource
    ): NotesRepository {
        return NotesRepositoryImpl(localDao)
    }
}