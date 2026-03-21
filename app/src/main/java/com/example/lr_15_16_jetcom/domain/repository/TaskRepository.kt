package com.example.lr_15_16_jetcom.domain.repository

import com.example.lr_15_16_jetcom.domain.model.Task

interface TaskRepository {
    suspend fun getTasks(): Result<List<Task>>
    suspend fun addTask(task: Task): Result<Unit>
}