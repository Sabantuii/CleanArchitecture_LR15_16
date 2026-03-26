package com.example.lr_15_16_jetcom.di

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
    fun provideNotesRepository(dao: NoteDao): NotesRepository {
        // Hilt автоматически подставит NoteDao из предыдущего модуля
        return NotesRepositoryImpl(dao)
    }
}