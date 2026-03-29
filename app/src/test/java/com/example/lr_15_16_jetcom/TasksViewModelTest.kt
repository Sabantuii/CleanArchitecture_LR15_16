package com.example.lr_15_16_jetcom

import com.example.lr_15_16_jetcom.domain.model.Task
import com.example.lr_15_16_jetcom.domain.repository.TaskRepository
import com.example.lr_15_16_jetcom.domain.usecase.AddTaskUseCase
import com.example.lr_15_16_jetcom.domain.usecase.GetTasksUseCase
import com.example.lr_15_16_jetcom.presentation.tasks.TasksViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TasksViewModelTest {

    private lateinit var viewModel: TasksViewModel
    private lateinit var fakeRepository: FakeTaskRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        fakeRepository = FakeTaskRepository()

        val getTasksUseCase = GetTasksUseCase(fakeRepository)
        val addTaskUseCase = AddTaskUseCase(fakeRepository)

        viewModel = TasksViewModel(
            getTasksUseCase = getTasksUseCase,
            addTaskUseCase = addTaskUseCase,
            ioDispatcher = testDispatcher
        )
    }

    @Test
    fun `verify initial state is empty`() = runTest {
        val state = viewModel.uiState.value
        assertTrue(state.tasks.isEmpty())
        assertFalse(state.isLoading)
        assertNull(state.error)
    }

    @Test
    fun `verify tasks are loaded successfully`() = runTest {
        fakeRepository.addFakeTask(Task(1L, "Test Task", false, System.currentTimeMillis()))

        viewModel.loadTasks()

        val state = viewModel.uiState.value
        assertEquals(1, state.tasks.size)  // 🔥 Используем .size
        assertEquals("Test Task", state.tasks[0].title)  // 🔥 Используем .title
        assertFalse(state.isLoading)
        assertNull(state.error)
    }

    @Test
    fun `verify task is added correctly`() = runTest {
        viewModel.addTask("New Task")

        val tasks = fakeRepository.getTasksList()  // 🔥 Отдельный метод для получения списка
        assertEquals(1, tasks.size)
        assertEquals("New Task", tasks[0].title)
    }

    @Test
    fun `verify empty task is not added`() = runTest {
        viewModel.addTask("")

        val tasks = fakeRepository.getTasksList()
        assertTrue(tasks.isEmpty())
    }
}

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