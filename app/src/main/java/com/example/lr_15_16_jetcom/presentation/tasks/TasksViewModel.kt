package com.example.lr_15_16_jetcom.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lr_15_16_jetcom.domain.usecase.AddTaskUseCase
import com.example.lr_15_16_jetcom.domain.usecase.GetTasksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksViewModel(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    fun loadTasks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getTasksUseCase()
                .onSuccess { tasks ->
                    _uiState.update { it.copy(tasks = tasks, isLoading = false, error = null) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
                }
        }
    }

    fun addTask(title: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            val task = com.example.lr_15_16_jetcom.domain.model.Task(
                id = 0L,
                title = title,
                isCompleted = false,
                createdAt = System.currentTimeMillis()
            )
            addTaskUseCase(task)
                .onSuccess { loadTasks() }
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message ?: "Ошибка добавления") }
                }
        }
    }
}