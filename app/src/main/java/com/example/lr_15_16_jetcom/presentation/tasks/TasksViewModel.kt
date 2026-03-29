package com.example.lr_15_16_jetcom.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lr_15_16_jetcom.di.IoDispatcher
import com.example.lr_15_16_jetcom.domain.model.Task
import com.example.lr_15_16_jetcom.domain.usecase.AddTaskUseCase
import com.example.lr_15_16_jetcom.domain.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// [ЛР 17, Задание 4] @HiltViewModel - Hilt управляет жизненным циклом ViewModel
@HiltViewModel
class TasksViewModel @Inject constructor(  // [ЛР 17] @Inject говорит Hilt заполнять параметры
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher  // [ЛР 18] Внедряем dispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    fun loadTasks() {
        viewModelScope.launch(ioDispatcher) {  // [ЛР 18] Используем внедренный dispatcher
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
        viewModelScope.launch(ioDispatcher) {
            val task = Task(
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