package com.example.lr_15_16_jetcom.data.repository

import com.example.lr_15_16_jetcom.data.remote.api.TaskApi
import com.example.lr_15_16_jetcom.data.remote.mapper.toDomain
import com.example.lr_15_16_jetcom.data.remote.mapper.toDto
import com.example.lr_15_16_jetcom.di.IoDispatcher
import com.example.lr_15_16_jetcom.domain.model.Task
import com.example.lr_15_16_jetcom.domain.repository.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(  // [ЛР 17] @Inject для внедрения
    private val api: TaskApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher  // [ЛР 18] Внедряем dispatcher
) : TaskRepository {

    override suspend fun getTasks(): Result<List<Task>> = withContext(ioDispatcher) {  // [ЛР 18] Используем внедренный
        try {
            val dtos = api.getTasks()
            Result.success(dtos.map { it.toDomain() })
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addTask(task: Task): Result<Unit> = withContext(ioDispatcher) {
        try {
            api.addTask(task.toDto())
            Result.success(Unit)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}