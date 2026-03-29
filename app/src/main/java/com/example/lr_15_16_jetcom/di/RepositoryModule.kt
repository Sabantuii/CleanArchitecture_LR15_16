package com.example.lr_15_16_jetcom.di

import com.example.lr_15_16_jetcom.data.remote.api.TaskApi
import com.example.lr_15_16_jetcom.data.repository.TaskRepositoryImpl
import com.example.lr_15_16_jetcom.domain.repository.TaskRepository
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
    fun provideTaskRepository(
        api: TaskApi,
        @IoDispatcher ioDispatcher: kotlinx.coroutines.CoroutineDispatcher  // Добавляем dispatcher
    ): TaskRepository {
        return TaskRepositoryImpl(api, ioDispatcher)  // Передаём в конструктор
    }
}