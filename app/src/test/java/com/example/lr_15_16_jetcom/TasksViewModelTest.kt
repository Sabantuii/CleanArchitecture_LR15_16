package com.example.lr_15_16_jetcom

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.lr_15_16_jetcom.domain.usecase.AddTaskUseCase
import com.example.lr_15_16_jetcom.domain.usecase.GetTasksUseCase
import com.example.lr_15_16_jetcom.presentation.tasks.TasksViewModel
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TasksViewModelTest {

    // Это правило нужно, если бы мы использовали LiveData, но для StateFlow оно тоже полезно
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    // Переменные объявляем здесь, чтобы они были доступны во всех методах
    private lateinit var viewModel: TasksViewModel
    private lateinit var fakeRepository: FakeTaskRepository

    @Before
    fun setup() {
        // 1. Мокаем Log ПЕРВЫМ делом, чтобы addTask не вылетал
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0

        // 2. Подменяем главный поток на тестовый
        Dispatchers.setMain(testDispatcher)

        // 3. Создаем фейковый репозиторий (как в ЛР 25)
        fakeRepository = FakeTaskRepository()

        // 4. Создаем UseCase, прокидывая в них фейк
        val getTasksUseCase = GetTasksUseCase(fakeRepository)
        val addTaskUseCase = AddTaskUseCase(fakeRepository)

        // 5. Создаем саму ViewModel
        viewModel = TasksViewModel(
            getTasksUseCase = getTasksUseCase,
            addTaskUseCase = addTaskUseCase,
            ioDispatcher = testDispatcher // Передаем тестовый диспетчер!
        )
    }

    @After
    fun tearDown() {
        // Обязательно очищаем за собой
        Dispatchers.resetMain()
        unmockkStatic(Log::class)
    }

    @Test
    fun `verify task is added correctly`() = runTest {
        // Given (Дано): название задачи
        val taskTitle = "Новая задача для теста"

        // When (Когда): вызываем метод добавления
        viewModel.addTask(taskTitle)

        // Then (Тогда): проверяем, что в состоянии (UiState) появилась эта задача
        val currentState = viewModel.uiState.value

        // Проверяем, что список не пуст
        assertEquals(1, currentState.tasks.size)
        // Проверяем, что имя совпадает
        assertEquals(taskTitle, currentState.tasks[0].title)
        // Проверяем, что загрузка выключилась
        assertEquals(false, currentState.isLoading)
    }
}