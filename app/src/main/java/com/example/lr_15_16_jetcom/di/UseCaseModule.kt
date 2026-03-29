package com.example.lr_15_16_jetcom.di

import com.example.lr_15_16_jetcom.domain.repository.TaskRepository
import com.example.lr_15_16_jetcom.domain.usecase.AddTaskUseCase
import com.example.lr_15_16_jetcom.domain.usecase.GetTasksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// [ЛР 17] Модуль для UseCase
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetTasksUseCase(
        repository: TaskRepository
    ): GetTasksUseCase {
        return GetTasksUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddTaskUseCase(
        repository: TaskRepository
    ): AddTaskUseCase {
        return AddTaskUseCase(repository)
    }
}