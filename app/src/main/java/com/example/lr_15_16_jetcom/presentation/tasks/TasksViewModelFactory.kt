package com.example.lr_15_16_jetcom.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lr_15_16_jetcom.domain.usecase.AddTaskUseCase
import com.example.lr_15_16_jetcom.domain.usecase.GetTasksUseCase

class TasksViewModelFactory(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass != TasksViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel")
        }
        return TasksViewModel(getTasksUseCase, addTaskUseCase) as T
    }
}