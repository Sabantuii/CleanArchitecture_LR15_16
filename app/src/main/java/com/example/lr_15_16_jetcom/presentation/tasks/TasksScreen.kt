package com.example.lr_15_16_jetcom.presentation.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lr_15_16_jetcom.domain.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    viewModel: TasksViewModel
) {
    // Подписка на единое состояние экрана
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Переменная для текста новой задачи (локальное состояние UI)
    var newTaskTitle by remember { mutableStateOf("") }

    // Загрузка данных при первом запуске экрана
    LaunchedEffect(Unit) {
        viewModel.onEvent(TasksEvent.LoadTasks)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Задачи (MVVM)") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // 1. Поле ввода новой задачи
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newTaskTitle,
                    onValueChange = { newTaskTitle = it },
                    label = { Text("Что нужно сделать?") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (newTaskTitle.isNotBlank()) {
                            viewModel.onEvent(TasksEvent.AddTask(newTaskTitle))
                            newTaskTitle = ""
                        }
                    },
                    enabled = !uiState.isLoading
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Добавить")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Отображение контента в зависимости от состояния
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    uiState.isLoading && uiState.tasks.isEmpty() -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    uiState.error != null -> {
                        Text(
                            text = "Ошибка: ${uiState.error}",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    uiState.tasks.isEmpty() -> {
                        Text(
                            text = "Список задач пуст",
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(uiState.tasks) { task ->
                                TaskCard(task = task)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskCard(task: Task) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = task.title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = null // Пока только чтение
            )
        }
    }
}