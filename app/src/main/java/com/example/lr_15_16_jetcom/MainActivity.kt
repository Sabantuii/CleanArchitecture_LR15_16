package com.example.lr_15_16_jetcom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.lr_15_16_jetcom.data.remote.api.TaskApi
import com.example.lr_15_16_jetcom.data.repository.TaskRepositoryImpl
import com.example.lr_15_16_jetcom.domain.usecase.AddTaskUseCase
import com.example.lr_15_16_jetcom.domain.usecase.GetTasksUseCase
import com.example.lr_15_16_jetcom.presentation.tasks.TasksScreen
import com.example.lr_15_16_jetcom.presentation.tasks.TasksViewModel
import com.example.lr_15_16_jetcom.presentation.tasks.TasksViewModelFactory
import com.example.lr_15_16_jetcom.ui.theme.LR_15_16_JetComTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Data Layer Setup
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            // Примечание: для реального теста лучше использовать свой API или мок,
            // так как JSONPlaceholder имеет другую структуру полей.
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val taskApi = retrofit.create(TaskApi::class.java)
        val taskRepository = TaskRepositoryImpl(taskApi)

        // 2. Domain Layer Setup
        val getTasksUseCase = GetTasksUseCase(taskRepository)
        val addTaskUseCase = AddTaskUseCase(taskRepository)

        // 3. Presentation Layer Setup (Manual DI)
        val factory = TasksViewModelFactory(getTasksUseCase, addTaskUseCase)
        val viewModel: TasksViewModel = ViewModelProvider(this, factory)[TasksViewModel::class.java]

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TasksScreen(viewModel = viewModel)
                }
            }
        }
    }
}