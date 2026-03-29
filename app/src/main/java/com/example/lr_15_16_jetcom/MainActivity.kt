package com.example.lr_15_16_jetcom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lr_15_16_jetcom.presentation.tasks.TasksScreen
import com.example.lr_15_16_jetcom.presentation.tasks.TasksViewModel
import com.example.lr_15_16_jetcom.ui.theme.LR_15_16_JetComTheme
import dagger.hilt.android.AndroidEntryPoint

// [ЛР 17, Задание 4] @AndroidEntryPoint - разрешает внедрение зависимостей в Activity
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TasksScreen(
                        // [ЛР 17] hiltViewModel() автоматически получает ViewModel из Hilt
                        // Hilt сам создаст TasksViewModel со всеми зависимостями
                        viewModel = hiltViewModel()
                    )
                }
            }
        }
    }
}