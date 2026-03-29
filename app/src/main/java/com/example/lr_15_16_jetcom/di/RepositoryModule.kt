package com.example.lr_15_16_jetcom.di

import com.example.lr_15_16_jetcom.data.remote.api.TaskApi
import com.example.lr_15_16_jetcom.data.repository.TaskRepositoryImpl
import com.example.lr_15_16_jetcom.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// [ЛР 17, Задание 3] Модуль для репозитория
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    // [ЛР 18, Задание 1]
    @Provides
    @Singleton
    fun provideTaskRepository(
        api: TaskApi  // Hilt сам подставит TaskApi из NetworkModule
    ): TaskRepository {
        return TaskRepositoryImpl(api)
    }
}