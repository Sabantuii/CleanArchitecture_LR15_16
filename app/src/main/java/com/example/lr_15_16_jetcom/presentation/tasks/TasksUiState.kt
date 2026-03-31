package com.example.lr_15_16_jetcom.presentation.tasks

import com.example.lr_15_16_jetcom.domain.model.Task

data class TasksUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
sealed interface TasksEvent {
    data object LoadTasks : TasksEvent
    data class AddTask(val title: String) : TasksEvent
}