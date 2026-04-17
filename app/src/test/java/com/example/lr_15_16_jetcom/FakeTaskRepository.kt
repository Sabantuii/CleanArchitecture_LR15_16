package com.example.lr_15_16_jetcom

import com.example.lr_15_16_jetcom.domain.model.Task
import com.example.lr_15_16_jetcom.domain.repository.TaskRepository

// Исправленный FakeTaskRepository
class FakeTaskRepository : TaskRepository {
    private val tasks = mutableListOf<Task>()

    fun addFakeTask(task: Task) {
        tasks.add(task)
    }

    // Отдельный метод для тестов (не конфликтует с интерфейсом)
    fun getTasksList(): List<Task> {
        return tasks.toList()
    }

    // Правильная реализация интерфейса - suspend функция
    override suspend fun getTasks(): Result<List<Task>> {
        return Result.success(tasks.toList())
    }

    override suspend fun addTask(task: Task): Result<Unit> {
        tasks.add(task)
        return Result.success(Unit)
    }
}