package com.example.lr_15_16_jetcom.presentation.tasks

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TasksScreen(
    viewModel: TasksViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadTasks()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }
            uiState.error != null -> {
                Text("Ошибка: ${uiState.error}", color = MaterialTheme.colorScheme.error)
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.tasks) { task ->
                        Card {
                            Text(
                                text = "${task.title} — ${if (task.isCompleted) "✓" else "○"}",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
        // Простая форма добавления для теста
        Spacer(modifier = Modifier.height(16.dp))
        var newTaskTitle by remember { mutableStateOf("") }
        OutlinedTextField(
            value = newTaskTitle,
            onValueChange = { newTaskTitle = it },
            label = { Text("Новая задача") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            viewModel.addTask(newTaskTitle)
            newTaskTitle = ""
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Добавить")
        }
    }
}