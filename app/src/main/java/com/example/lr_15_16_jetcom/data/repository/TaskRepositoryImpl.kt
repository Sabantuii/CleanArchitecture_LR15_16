package com.example.lr_15_16_jetcom.data.repository

import com.example.lr_15_16_jetcom.data.remote.api.TaskApi
import com.example.lr_15_16_jetcom.data.remote.mapper.toDomain
import com.example.lr_15_16_jetcom.data.remote.mapper.toDto
import com.example.lr_15_16_jetcom.domain.model.Task
import com.example.lr_15_16_jetcom.domain.repository.TaskRepository
import kotlinx.coroutines.*
import java.io.IOException

class TaskRepositoryImpl(
    private val api: TaskApi
) : TaskRepository {

    override suspend fun getTasks(): Result<List<Task>> = withContext(Dispatchers.IO) {
        try {
            val dtos = api.getTasks()
            Result.success(dtos.map { it.toDomain() })
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    override suspend fun addTask(task: Task): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            api.addTask(task.toDto())
            Result.success(Unit)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}