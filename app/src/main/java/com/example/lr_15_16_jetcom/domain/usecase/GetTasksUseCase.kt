package com.example.lr_15_16_jetcom.domain.usecase

import com.example.lr_15_16_jetcom.domain.model.Task
import com.example.lr_15_16_jetcom.domain.repository.TaskRepository

class GetTasksUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(): Result<List<Task>> = repository.getTasks()
}